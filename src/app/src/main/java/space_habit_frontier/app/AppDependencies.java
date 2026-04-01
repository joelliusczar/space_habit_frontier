package space_habit_frontier.app;

import space_habit_frontier.engine.services.LookupsService;
import space_habit_frontier.engine.services.TodoService;
import space_habit_frontier.engine.services.dates.DefaultDatetimeProvider;
import space_habit_frontier.engine.services.dates.DefaultExpirationDatesProvider;
import space_habit_frontier.app.dtos.AppUserDetails;
import space_habit_frontier.app.security.AppCookieManager;
import space_habit_frontier.app.security.AppUserDetailsService;
import space_habit_frontier.engine.dtos.web.GlobalStore;
import space_habit_frontier.engine.dtos.web.IpAddressPair;
import space_habit_frontier.engine.dtos.web.TrackingInfo;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.dates.ExpirationDatesProvider;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;
import space_habit_frontier.engine.interfaces.events.EventLogger;
import space_habit_frontier.engine.interfaces.users.UserProvider;
import space_habit_frontier.engine.interfaces.web.TrackingInfoProvider;
import space_habit_frontier.engine.services.db.connection_providers.PsqlConnectionProvider;
import space_habit_frontier.engine.services.events.AggregateEventLogger;
import space_habit_frontier.engine.services.events.FSEventService;
import space_habit_frontier.engine.services.events.InMemEventService;
import space_habit_frontier.engine.services.events.VisitorService;
import space_habit_frontier.engine.services.secrets_providers.db.EnvApiUserSecretsProvider;
import space_habit_frontier.engine.services.users.BasicUserProvider;
import space_habit_frontier.engine.services.users.UserAccessService;
import space_habit_frontier.engine.services.users.UserManagementService;
import space_habit_frontier.engine.services.web.UserSessionService;
import space_habit_frontier.engine.services.web.VisitorTrackingService;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import jakarta.servlet.http.HttpServletRequest;

@Component
class AppDependencies {

	@Bean
	public GlobalStore getGlobalStore() {
		return GlobalStore.getInstance();
	}

	@Bean
	public LookupsService getLookupsService() 
	{
		return new LookupsService();
	}

	@Bean
	public DataContextProvider getApiDataContextProvider() 
	{
		var secretsProvider = new EnvApiUserSecretsProvider();
		var dataContextProvider = new PsqlConnectionProvider(secretsProvider);
		return dataContextProvider;
	}

	private IpAddressPair extractIpAddresses(HttpServletRequest request) 
	{
		var candidateIp = request.getHeader("X-Real-IP");
		if (candidateIp == null) {
			candidateIp = request.getRemoteAddr();
		}
		try {
			var ipAddress = InetAddress.getByName(candidateIp);
			return switch(ipAddress) {
				case Inet4Address _ -> new IpAddressPair(candidateIp, "");
				case Inet6Address _ -> new IpAddressPair("", candidateIp);
				default -> new IpAddressPair("", "");
			};
		}
		catch(UnknownHostException _) {
			return new IpAddressPair("", "");
		}
	}

	@Bean
	@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public TrackingInfo getTrackingInfo(HttpServletRequest request) {
		var ipAddresses = extractIpAddresses(request);
		return new TrackingInfo(
			request.getRequestURL().toString(),
			request.getHeader("User-Agent"),
			ipAddresses.ipv4Address(),
			ipAddresses.ipv6Address(),
			request.getMethod()
		);
	}

	@Bean
	public VisitorService getVisitorService(
			GlobalStore globalStore, 
			DataContextProvider dataContextProvider) throws SQLException {
		return new VisitorService(globalStore.getVisitorIdMap(), 
			dataContextProvider.getContext());
	}

	@Bean
	public TrackingInfoProvider getTrackingInfoProvider(
			TrackingInfo trackingInfo,
			VisitorService visitorService) {
		return new VisitorTrackingService(trackingInfo, visitorService);
	}

	@Bean
	public DatetimeProvider getDatetimeProvider() {
		return new DefaultDatetimeProvider();
	}

	@Bean
	public ExpirationDatesProvider expirationDatesProvider() {
		return new DefaultExpirationDatesProvider();
	}

	@Bean
	@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public UserProvider getUserProvider(
			SecurityContextHolderStrategy securityContextHolderStrategy) {
		var authentication = securityContextHolderStrategy
			.getContext()
			.getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			var user = (AppUserDetails)authentication.getPrincipal();
			return new BasicUserProvider(user.toInternalUserDto());
		}
		return new BasicUserProvider(null);
	}

	@Bean
	public InMemEventService getInMemEventService(GlobalStore globalStore,
			DatetimeProvider datetimeProvider,
			UserProvider userProvider,
			TrackingInfoProvider trackingInfoProvider) {
		return new InMemEventService(globalStore.getVisitorVisitMap(),
			globalStore.getUserIdEventMap(),
			datetimeProvider,
			userProvider,
			trackingInfoProvider);
	}

	@Bean
	public FSEventService getFSEventService(DatetimeProvider datetimeProvider,
			UserProvider userProvider,
			TrackingInfoProvider trackingInfoProvider) {
		return new FSEventService(datetimeProvider,
			userProvider,
			trackingInfoProvider);
	}

	@Bean
	@Primary
	public EventLogger getDefaultEventLogger(
			DatetimeProvider datetimeProvider,
			UserProvider userProvider,
			TrackingInfoProvider trackingInfoProvider,
			InMemEventService inMemEventService,
			FSEventService fsEventService
	) {
		return new AggregateEventLogger(datetimeProvider,
			userProvider,
			trackingInfoProvider,
			inMemEventService,
			fsEventService
		);
	}

	@Bean
	public UserManagementService getUserManagementService(
			DataContextProvider dataContextProvider,
			DatetimeProvider datetimeProvider) throws SQLException {
		return new UserManagementService(dataContextProvider.getContext(),
			datetimeProvider);
	}

	@Bean
	public UserAccessService getUserAccessService(
			DataContextProvider dataContextProvider, 
			DatetimeProvider datetimeProvider) throws SQLException {
		return new UserAccessService(dataContextProvider.getContext(), datetimeProvider);
	}

	@Bean
	public UserDetailsService userDetailsService(
			UserAccessService userAccessService) {
		return new AppUserDetailsService(userAccessService);
	}

	@Bean
	public UserSessionService getUserSessionService(
			DataContextProvider dataContextProvider,
			DatetimeProvider datetimeProvider,
			TrackingInfoProvider trackingInfoProvider,
			UserAccessService accessService,
			ExpirationDatesProvider expirationDatesProvider) throws SQLException {
		return new UserSessionService(
			dataContextProvider.getContext(),
			datetimeProvider,
			trackingInfoProvider,
			accessService,
			expirationDatesProvider);
	}

	@Bean
	public AppCookieManager cookieManager(DatetimeProvider datetimeProvider) {
		return new AppCookieManager(datetimeProvider);
	}

	@Bean
	public TodoService getTodoService(
			DataContextProvider dataContextProvider,
			UserProvider userProvider,
			DatetimeProvider datetimeProvider
	)  throws SQLException {
		return new TodoService(
			dataContextProvider,
			userProvider,
			datetimeProvider);
	}
}
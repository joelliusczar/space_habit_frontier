package space_habit_frontier.app;

import space_habit_frontier.engine.services.LookupsService;
import space_habit_frontier.engine.dtos.web.GlobalStore;
import space_habit_frontier.engine.dtos.web.IpAddressPair;
import space_habit_frontier.engine.dtos.web.TrackingInfo;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;
import space_habit_frontier.engine.interfaces.web.TrackingInfoProvider;
import space_habit_frontier.engine.services.db.connection_providers.PsqlConnectionProvider;
import space_habit_frontier.engine.services.events.VisitorService;
import space_habit_frontier.engine.services.secrets_providers.db.EnvOwnerSecretsProvider;
import space_habit_frontier.engine.services.web.VisitorTrackingService;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
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
		var secretsProvider = new EnvOwnerSecretsProvider();
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
	@RequestScope
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
	public VisitorService getVisitorService(GlobalStore globalStore, 
		DataContextProvider dataContextProvider) throws SQLException {

		return new VisitorService(globalStore.getVisitorIdMap(), 
			dataContextProvider.getContext());
	}

	@Bean
	@RequestScope
	public TrackingInfoProvider getTrackingInfoProvider(TrackingInfo trackingInfo,
		VisitorService visitorService) {

		return new VisitorTrackingService(trackingInfo, visitorService);
	}

}
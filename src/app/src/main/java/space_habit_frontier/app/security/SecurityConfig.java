package space_habit_frontier.app.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import space_habit_frontier.app.filters.JwtFilter;
import space_habit_frontier.engine.dtos.web.GlobalStore;
import space_habit_frontier.engine.services.web.UserSessionService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtFilter __jwtFilter;

	public SecurityConfig(JwtFilter jwtFilter) {
		this.__jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http, 
			AuthenticationManager authManager,
			GlobalStore globalStore,
			UserSessionService userSessionService,
			SecurityContextHolderStrategy securityContextHolderStrategy
		) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**").anonymous()
				.anyRequest().authenticated()
			)
			.securityContext((sec) ->
				sec.securityContextRepository(new AppSecurityContextRepository(
					globalStore.getUserSessionMap(), 
					userSessionService, securityContextHolderStrategy)))
			.securityContext((sec) -> 
				sec.requireExplicitSave(false));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityContextHolderStrategy securityContextHolderStrategy() {
		return SecurityContextHolder.getContextHolderStrategy();
	}

	// @Bean
	// public DaoAuthenticationProvider daoAuthenticationProvider(
	// 		UserDetailsService userDetailsService) {
	// 	var provider = new DaoAuthenticationProvider(userDetailsService);
	// 	provider.setPasswordEncoder(passwordEncoder());
	// 	return provider;
	// }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

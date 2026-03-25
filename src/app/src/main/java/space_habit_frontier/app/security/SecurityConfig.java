package space_habit_frontier.app.security;


import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import space_habit_frontier.app.filters.CookieFilter;
import space_habit_frontier.engine.dtos.web.GlobalStore;
import space_habit_frontier.engine.services.web.UserSessionService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CookieFilter __cookieFilter;

	public SecurityConfig(CookieFilter cookieFilter) {
		__cookieFilter = cookieFilter;
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
				.requestMatchers("/api/auth/**").permitAll()
				.anyRequest().authenticated()
			)
			.securityContext((sec) -> {
				sec.securityContextRepository(new AppSecurityContextRepository(
					globalStore.getUserSessionMap(), 
					userSessionService, securityContextHolderStrategy));
				sec.requireExplicitSave(false);
			})
			.sessionManagement(sess ->
				sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(
				__cookieFilter, 
				UsernamePasswordAuthenticationFilter.class);

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

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		var source = new UrlBasedCorsConfigurationSource();
		var config = new CorsConfiguration();
		config.setAllowedOrigins(List.of(
			"https://127.0.0.1",
			"https://localhost:3000",
			"http://127.0.0.1",
			"http://localhost:3000"
		));
		config.setAllowedHeaders(List.of(
			"Authorization",
			"Cookie",
			"Forwarded",
			"True-Client-Ip",
			"Via",
			"X-Client-IP",
			"X-Forwarded-For",
			"X-Real-IP"));
		config.setAllowCredentials(true);
		config.addExposedHeader("x-authexpired");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}

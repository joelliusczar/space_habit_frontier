package space_habit_frontier.app.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import space_habit_frontier.app.filters.JwtFilter;

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
			AuthenticationManager authManager) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**").anonymous()
				.anyRequest().authenticated()
			)
			.sessionManagement(sess -> 
				sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(__jwtFilter, 
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
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

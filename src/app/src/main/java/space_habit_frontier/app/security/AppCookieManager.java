package space_habit_frontier.app.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import space_habit_frontier.engine.dtos.web.UserSessionDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;

public class AppCookieManager {
	private final DatetimeProvider __datetimeProvider;

	public AppCookieManager(DatetimeProvider datetimeProvider) {
		this.__datetimeProvider = datetimeProvider;
	}
	
	public HttpHeaders getLoginCookies(UserSessionDto userSessionDto) {
		var tokenCookie = ResponseCookie.from(
			"session_token",
			userSessionDto.getSessionId().toString())
			.httpOnly(true)
			.path("/")
			.build();
		var usernameCookie = ResponseCookie.from(
			"username",
			userSessionDto.getUser().getUsername())
			.path("/")
			.build();
		var loginTimestampCookie = ResponseCookie.from(
			"login_timestamp",
			__datetimeProvider.now().toString())
			.path("/")
			.build();
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, tokenCookie.toString());
		headers.add(HttpHeaders.SET_COOKIE, usernameCookie.toString());
		headers.add(HttpHeaders.SET_COOKIE, loginTimestampCookie.toString());
		return headers;
	}

	private ResponseCookie expiredCookie(String name) {
		var cookie = ResponseCookie.from(
			name,
			"")
			.maxAge(0)
			.httpOnly(true)
			.path("/")
			.build();

		return cookie;
	}

	public HttpHeaders expireLoginCookies() {
		var headers = new HttpHeaders();
		headers.add(
			HttpHeaders.SET_COOKIE,
			expiredCookie("session_token").toString());
		headers.add(
			HttpHeaders.SET_COOKIE,
			expiredCookie("username").toString());
		headers.add(
			HttpHeaders.SET_COOKIE,
			expiredCookie("login_timestamp").toString());
		return headers;
	}
	
}
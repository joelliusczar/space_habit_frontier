package space_habit_frontier.app.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import space_habit_frontier.engine.services.environment.EnvironmentWrapper;


public class JwtService {

	private static long __expirationOffset = 60 * 15; // 30 days in seconds

	private static SecretKey __secretKey() {
		var keyBytes = EnvironmentWrapper.getAppSecretKey(). getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public static String generateToken(String username) {
		return Jwts.builder()
			.subject(username)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + __expirationOffset)) // 1 day
			.signWith(__secretKey())
			.compact();
	}

	private static <T> T __extractClaim(String token,
		Function<Claims, T> claimsResolver) {
		var claims = Jwts.parser()
			.verifyWith(__secretKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
		return claimsResolver.apply(claims);
	}

	public static String extractUsername(String token) {
		return __extractClaim(token, Claims::getSubject);
	}

	public static Date extractExpiration(String token) {
		return __extractClaim(token, Claims::getExpiration);
	}

	public static boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public static boolean validateToken(String token, String username) {
		var extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}
}

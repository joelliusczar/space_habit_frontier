package space_habit_frontier.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

	private final Logger __logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ProblemDetail handleAuthFailure(BadCredentialsException ex) {
		var problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
		problemDetail.setTitle("Incorrect username or password");
		return problemDetail;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ProblemDetail handleGeneralErrors(Exception ex) {
		__logger.error("Unhandled error {}", ex);
		var problemDetail = ProblemDetail.forStatus(
			HttpStatus.INTERNAL_SERVER_ERROR);
		problemDetail.setTitle("Onk! Caveman error! What do?");
		return problemDetail;
	}
	
}

package space_habit_frontier.app.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import space_habit_frontier.engine.interfaces.events.EventLogger;

@Component
public class EventLoggingFilter implements Filter {

	private final EventLogger __eventLogger;
	private final Logger __logger = LoggerFactory.getLogger(getClass());

	public EventLoggingFilter(EventLogger eventLogger) {
		this.__eventLogger =  eventLogger;
	}

	@Override
	public void doFilter(ServletRequest request, 
		ServletResponse response,
		FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
		try {
			__eventLogger.createVisitRecord("");
		}
		catch(Exception e) {
			__logger.warn("App errored while attempting to log vist: {}", e);
		}
	}
	
}

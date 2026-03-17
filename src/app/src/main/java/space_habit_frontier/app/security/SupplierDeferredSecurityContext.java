package space_habit_frontier.app.security;

import java.util.function.Supplier;

import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;

public class SupplierDeferredSecurityContext implements DeferredSecurityContext {

	private final Supplier<SecurityContext> __securityContextSupplier;
	private SecurityContext __cachedContext;

	public SupplierDeferredSecurityContext(
			Supplier<SecurityContext> securityContextSupplier) {
		this.__securityContextSupplier = securityContextSupplier;
	}

	@Override
	public SecurityContext get() {
		if (__cachedContext == null) {
			__cachedContext = __securityContextSupplier.get();
		}
		return __cachedContext;
	}

	@Override
	public boolean isGenerated() {
		return __cachedContext != null;
	}
	
}

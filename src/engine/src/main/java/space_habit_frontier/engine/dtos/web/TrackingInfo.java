
package space_habit_frontier.engine.dtos.web;

public class TrackingInfo {
	private final String url;
	private final String userAgent;
	private final String ipv4Address;
	private final String ipv6Address;
	private final String method;

	public TrackingInfo(String url,
		String userAgent,
		String ipv4Address,
		String ipv6Address,
		String method) {
		this.url = url;
		this.userAgent = userAgent;
		this.ipv4Address = ipv4Address;
		this.ipv6Address = ipv6Address;
		this.method = method;
	}

	public String url() {
		return url;
	}

	public String userAgent() {
		return userAgent;
	}

	public String ipv4Address() {
		return ipv4Address;
	}

	public String ipv6Address() {
		return ipv6Address;
	}

	public String method() {
		return method;
	}
}

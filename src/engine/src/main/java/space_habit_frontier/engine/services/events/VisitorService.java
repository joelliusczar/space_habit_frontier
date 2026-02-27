package space_habit_frontier.engine.services.events;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jooq.DSLContext;

import space_habit_frontier.engine.db_generated.tables.Visitors;
import space_habit_frontier.engine.dtos.events.event_maps.VisitorVisitMap;
import space_habit_frontier.engine.dtos.web.TrackingInfo;

public class VisitorService {
	private final ConcurrentMap<String, Map<String, Long>> visitorIdMap;
	private DSLContext dbContext;
	
	public VisitorService(
		ConcurrentMap<String, Map<String, Long>> visitorIdMap,
		DSLContext dbContext) 
	{
		this.visitorIdMap = visitorIdMap;
		this.dbContext = dbContext;
		// Initialize visitor tracking structures here
	}

	private long checkCache(TrackingInfo trackingInfo)
	{
		// Check if the visitor is already in the cache and return their ID
		if (this.visitorIdMap.containsKey(trackingInfo.ipv6Address())) {
			var ipMap = this.visitorIdMap.get(trackingInfo.ipv6Address());
			return ipMap.getOrDefault(trackingInfo.userAgent(), -1L);
		}
		if (this.visitorIdMap.containsKey(trackingInfo.ipv4Address())) {
			var ipMap = this.visitorIdMap.get(trackingInfo.ipv4Address());
			return ipMap.getOrDefault(trackingInfo.userAgent(), -1L);
		}
		return -1L; // Return -1 if not found
	}

	private void addToCache(TrackingInfo trackingInfo, long visitorId)
	{
		// Add the visitor to the cache
		this.visitorIdMap
			.computeIfAbsent(trackingInfo.ipv6Address(), k -> new ConcurrentHashMap<>())
			.put(trackingInfo.userAgent(), visitorId);
		this.visitorIdMap
			.computeIfAbsent(trackingInfo.ipv4Address(), k -> new ConcurrentHashMap<>())
			.put(trackingInfo.userAgent(), visitorId);
	}

	public long getOrAddVisitor(TrackingInfo trackingInfo) 
		throws NoSuchAlgorithmException
	{
		var visitorId = checkCache(trackingInfo);
		if (visitorId != -1L) {
			return visitorId; // Return cached visitor ID
		}
		var uaBytes = trackingInfo.userAgent().getBytes();
		var md = java.security.MessageDigest.getInstance("MD5");
		var uaHash = md.digest(uaBytes);
		return dbContext.transactionResult(configuration -> {
			DSLContext ctx = configuration.dsl();
			// Insert new visitor into the database and get the generated ID
			var _visitorId = ctx.selectFrom(Visitors.VISITORS)
				.where(Visitors.VISITORS.IPV4ADDRESS.eq(trackingInfo.ipv4Address())
					.and(Visitors.VISITORS.IPV6ADDRESS.eq(trackingInfo.ipv6Address()))
					.and(Visitors.VISITORS.UAHASH.eq(uaHash)))
					.and(Visitors.VISITORS.UALENGTH.eq(
						(short)trackingInfo.userAgent().length()))
					.fetchOptional(Visitors.VISITORS.ID);
				if (_visitorId.isPresent()) {
					return _visitorId.get();
				}
				var newVisitorId = ctx.insertInto(Visitors.VISITORS)
					.set(Visitors.VISITORS.IPV4ADDRESS, trackingInfo.ipv4Address())
					.set(Visitors.VISITORS.IPV6ADDRESS, trackingInfo.ipv6Address())
					.set(Visitors.VISITORS.UAHASH, uaHash)
					.set(Visitors.VISITORS.UALENGTH, (short)trackingInfo.userAgent().length())
					.returning(Visitors.VISITORS.ID)
					.fetchOne()
					.getId();
				addToCache(trackingInfo, newVisitorId);
				return newVisitorId;
			});

	}
}

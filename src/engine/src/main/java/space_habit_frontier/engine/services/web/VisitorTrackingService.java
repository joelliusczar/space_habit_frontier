package space_habit_frontier.engine.services.web;

import java.security.NoSuchAlgorithmException;

import space_habit_frontier.engine.dtos.web.TrackingInfo;
import space_habit_frontier.engine.interfaces.web.TrackingInfoProvider;
import space_habit_frontier.engine.services.events.VisitorService;

public class VisitorTrackingService implements TrackingInfoProvider {

	private TrackingInfo __trackingInfo;
	private VisitorService __visitorService;

	public VisitorTrackingService(TrackingInfo trackingInfo,
		VisitorService visitorService) {

		this.__trackingInfo = trackingInfo;
		this.__visitorService = visitorService;
	}

	@Override
	public TrackingInfo getTrackingInfo() {
		return this.__trackingInfo;
	}
	
	@Override
	public long getVisitorId() throws NoSuchAlgorithmException {
		return this.__visitorService.getOrAddVisitor(this.__trackingInfo);
	}
	
}

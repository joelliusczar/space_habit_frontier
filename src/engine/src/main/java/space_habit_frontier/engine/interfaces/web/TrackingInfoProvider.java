package space_habit_frontier.engine.interfaces.web;

import java.security.GeneralSecurityException;

import space_habit_frontier.engine.dtos.web.TrackingInfo;

public interface TrackingInfoProvider {
	TrackingInfo getTrackingInfo();
	long getVisitorId() throws GeneralSecurityException;
}

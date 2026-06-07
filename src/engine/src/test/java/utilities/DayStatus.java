package utilities;

import java.util.List;

public class DayStatus {

	private boolean __isDayActive;
	private int __daysTillActive;
	private List<Long> __missedDays;

	// Getters
	public boolean isDayActive() {
		return __isDayActive;
	}

	public int daysTillActive() {
		return __daysTillActive;
	}

	public List<Long> missedDays() {
		return __missedDays;
	}

	// Fluent Setters
	public DayStatus setIsDayActive(boolean isDayActive) {
		this.__isDayActive = isDayActive;
		return this;
	}

	public DayStatus setDaysTillActive(int daysTillActive) {
		this.__daysTillActive = daysTillActive;
		return this;
	}

	public DayStatus setMissedDays(List<Long> missedDays) {
		__missedDays = missedDays;
		return this;
	}

	@Override
	public String toString() {
		return String.format(
			"Active: %b daysTillActive: %d", 
			isDayActive(),
			daysTillActive());
	}

}
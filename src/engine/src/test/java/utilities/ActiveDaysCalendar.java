package utilities;

import java.util.ArrayList;
import java.util.Set;

public class ActiveDaysCalendar {
	private Set<Integer> __activeDays;
	private DayStatus[] __calendar;
	private int __intervalSize;
	private int __periodSize;
	private int __invalidCount;

	public ActiveDaysCalendar(
		Set<Integer> days,
		int periodSize,
		int intervalSize,
		int periodCount) {
			__calendar = new DayStatus[periodCount * periodSize]; 
			for (var i = 0; i < __calendar.length; i++) {
				__calendar[i] = new DayStatus();
			}
			__activeDays = days;
			__intervalSize = intervalSize;
			__periodSize = periodSize;
	}

	public void loadActiveDays(int prev) {
		for (var i = 0; i < prev && i < __calendar.length; i++) {
			__calendar[i].setIsDayActive(false);
		}

		var interval = 0;
		var cycleStarted = false;
		for (var i = prev; i < __calendar.length; i++) {
			if (interval % __intervalSize == 0) {
				__calendar[i].setIsDayActive(__activeDays.contains(i % __periodSize));
				if (__calendar[i].isDayActive()) {
					cycleStarted = true;
				}
			}
			else {
				__calendar[i].setIsDayActive(false);
			}
			if (i % __periodSize == (__periodSize - 1) && cycleStarted) {
				interval++;
			}
		}
	}

	public void loadDaysTill() {
		var count = 0;
		var hasFoundValid = false;
		for (var i = __calendar.length - 1; i >= 0; i--) {
			if (__calendar[i].isDayActive()) {
				count = 0;
				if (!hasFoundValid) {
					hasFoundValid = true;
					__invalidCount = __calendar.length - i - 1;
				}
			}
			else {
				count++;
			}
			__calendar[i].setDaysTillActive(count);
		}
	}
	
	public void loadMissedDays(int prev) {
		for (var i = 0; i < prev && i < __calendar.length; i++) {
			__calendar[i].setMissedDays(0);
		}

		for (var i = prev; i < __calendar.length; i++) {
			var count = 0l;
			for (var j = prev + 1; j < i; j++) {
				if (__calendar[j].isDayActive()) {
					count++;
				}
			}
			__calendar[i].setMissedDays(count);
		}
	}

	public int size() {
		return __calendar.length;
	}

	public DayStatus get(int idx) {
		return __calendar[idx];
	}

	public int invalidCount() {
		return __invalidCount;
	}

	public int validCount() {
		return size() - invalidCount();
	}
}

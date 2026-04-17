package utilities;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WeeklyActiveDaysCalendar {
	private List<Set<DayOfWeek>> __activeDays;
	private int __weekCount;
	private int __intervalSize;

	public WeeklyActiveDaysCalendar(
		Set<DayOfWeek> days,
		int intervalSize,
		int weekCount) {
			__activeDays = new ArrayList<Set<DayOfWeek>>(Collections.nCopies(
				weekCount,
				null));
			__intervalSize = intervalSize;
			__weekCount = weekCount;
	}

	public void loadActiveDays(Set<DayOfWeek> days) {
		for	(var i = 0; i < __weekCount; i++) {
			if (i % __intervalSize == 0) {
				__activeDays.set(i, days);
			}
		}
	}
}

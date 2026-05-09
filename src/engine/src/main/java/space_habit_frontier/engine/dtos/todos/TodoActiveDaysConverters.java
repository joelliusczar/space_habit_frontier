package space_habit_frontier.engine.dtos.todos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import space_habit_frontier.engine.dtos.MonthDay;
import space_habit_frontier.engine.utilities.SHFEnumUtils;

public class TodoActiveDaysConverters {
	
	public static BitSet getWeekactivedaysByteString(
			Collection<String> weekactivedays) {
		var result = new BitSet(7);
		SHFEnumUtils.loopEnum(DayOfWeek.class, day -> {
			result.set(
				day.getValue() % 7, 
				weekactivedays.contains(day.name().toLowerCase()));
		});
		return result;
	}

	public static String getWeekActiveDaysString(
				Collection<String> weekactivedays) {
			return "%d%d%d%d%d%d%d".formatted(
				weekactivedays.contains(
					DayOfWeek.SUNDAY.name().toLowerCase()) ? 1 : 0,
				weekactivedays.contains(
					DayOfWeek.MONDAY.name().toLowerCase()) ? 1 : 0,
				weekactivedays.contains(
					DayOfWeek.TUESDAY.name().toLowerCase()) ? 1 : 0,
				weekactivedays.contains(
					DayOfWeek.WEDNESDAY.name().toLowerCase()) ? 1 : 0,
				weekactivedays.contains(
					DayOfWeek.THURSDAY.name().toLowerCase()) ? 1 : 0,
				weekactivedays.contains(
					DayOfWeek.FRIDAY.name().toLowerCase()) ? 1 : 0,
				weekactivedays.contains(
					DayOfWeek.SATURDAY.name().toLowerCase()) ? 1 : 0
				);
		}

	public static Set<Integer> weekActiveDaysSet(String bits) {
		var result = new HashSet<Integer>();
		for (int i = 0; i < bits.length(); i++) {
			if (bits.charAt(i) == '1') {
				result.add(i);
			}
		}
		return result;
	}

	public static Set<Integer> weekActiveDaysSet(BitSet bits) {
		var result = new HashSet<Integer>();
		SHFEnumUtils.loopEnum(DayOfWeek.class, day -> {
			if (bits.get(day.getValue() % 7)) {
				result.add(day.getValue() % 7);
			}
		});
		return result;
	}

	public static Integer[] getYearActivedaysIntegerArray(List<MonthDay> yearactivedays) {
		var monthMap = Map.ofEntries(
			Map.entry(Month.JANUARY.name().toLowerCase(), Month.JANUARY),
			Map.entry(Month.FEBRUARY.name().toLowerCase(), Month.FEBRUARY),
			Map.entry(Month.MARCH.name().toLowerCase(), Month.MARCH),
			Map.entry(Month.APRIL.name().toLowerCase(), Month.APRIL),
			Map.entry(Month.MAY.name().toLowerCase(), Month.MAY),
			Map.entry(Month.JUNE.name().toLowerCase(), Month.JUNE),
			Map.entry(Month.JULY.name().toLowerCase(), Month.JULY),
			Map.entry(Month.AUGUST.name().toLowerCase(), Month.AUGUST),
			Map.entry(Month.SEPTEMBER.name().toLowerCase(), Month.SEPTEMBER),
			Map.entry(Month.OCTOBER.name().toLowerCase(), Month.OCTOBER),
			Map.entry(Month.NOVEMBER.name().toLowerCase(), Month.NOVEMBER),
			Map.entry(Month.DECEMBER.name().toLowerCase(), Month.DECEMBER)
		);
		var result = yearactivedays.stream().map(d -> {
			//use leap year so that all dates are captured
			var date = LocalDate.of(
				2000,
				monthMap.get(d.month().toLowerCase()),
				d.day());
			return Integer.valueOf(date.getDayOfYear());
		}).toArray(Integer[]::new);

		return result;
	}
	
	
}

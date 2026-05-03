package dtos.todos.WeeklyDueDate.MissedDays;

import org.junit.jupiter.api.Test;

import space_habit_frontier.engine.dtos.todos.WeeklyDueDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

class MissedDays1ActiveDaysInterval3Tests {

    @Test
    void testMissedDays1ActiveDaysInterval3() {

			var baselineDate = OffsetDateTime.of(
				LocalDate.of(2018, 1, 10),
				LocalTime.MIN,
				ZoneOffset.UTC
			).toLocalDateTime();

			var dueDate = new WeeklyDueDate(Set.of(
					WeeklyDueDate.idx(DayOfWeek.WEDNESDAY)),
				baselineDate)
			.setIntervalSize(3);
			
			var testDate = OffsetDateTime.of(
				LocalDate.of(2018, 1, 31),
				LocalTime.MIN,
				ZoneOffset.ofTotalSeconds(0))
			.toLocalDateTime();

			var result = 0L;

//testDate = \(struct SHDatetime\)\{\.year = (\d+), \.month = (\d+), \.day = (\d+), \.timezoneOffset = (-?\d+)\}

//testDate = OffsetDateTime.of(\n\t\t\t\t\tLocalDate.of($1, $2, $3),\n\t\t\t\t\tLocalTime.MIN,\n\t\t\t\t\tZoneOffset.ofTotalSeconds($4))\n\t\t\t\t.toLocalDateTime()

			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 29),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 30),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 31),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 1),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 3),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 5),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 6),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 7),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 8),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 9),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);


			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 1),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 3),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 5),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 6),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 7),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 8),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 9),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 29),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 30),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 31),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 1),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 3),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 5),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 6),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 7),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 8),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 9),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Tuesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Wednesday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);

			
			//Thursday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(5, result);

			
			//Friday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(5, result);

			
			//Saturday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(5, result);

			
			//Sunday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 29),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(5, result);

			
			//Monday
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 30),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(5, result);
		}
}

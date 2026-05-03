package dtos.todos.WeeklyDueDate;

import org.junit.jupiter.api.Test;

import space_habit_frontier.engine.dtos.todos.WeeklyDueDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

class MissedDaysMixedCasesTests {

		@Test
		void testMissedDaysMixedCases() {
			/*
				#calendar 2018
					SU	MO	TU	WE	TH	FR	SA
															01	02
					03	04	05	06	07	08	09		2	3
					10	11	12	13	14	15	16
					17	18	19	20	21	22	23		2
					24	25	26	27	28	29	30			3
					31														2
				JAN
							01	02	03	04	05	06
					07	08	09	10	11	12	13	*
					14	15	16	17	18	19	20	1
					21	22	23	24	25	26	27	1	2
					28	29	30	31									3
				FEB
													01	02	03	1		3
					04	05	06	07	08	09	10	1	2
					11	12	13	14	15	16	17	1
					18	19	20	21	22	23	24	1	2	3
					25	26	27	28							1
				MAR
													01	02	03		2
					04	05	06	07	08	09	10
					11	12	13	14	15	16	17		2	3
					18	19	20	21	22	23	24
					25	26	27	28	29	30	31		2
			*/

				var baselineDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 10),
					LocalTime.MIN,
					ZoneOffset.UTC
				).toLocalDateTime();

				var dueDate = new WeeklyDueDate(Set.of(
						WeeklyDueDate.idx(DayOfWeek.MONDAY),
						WeeklyDueDate.idx(DayOfWeek.WEDNESDAY)),
					baselineDate)
				.setIntervalSize(1);
				
				var testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 31),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(0))
				.toLocalDateTime();

				var result = 0L;

//testDate = \(struct SHDatetime\)\{\.year = (\d+), \.month = (\d+), \.day = (\d+), \.timezoneOffset = (-?\d+)\}

//testDate = OffsetDateTime.of(\n\t\t\t\t\tLocalDate.of($1, $2, $3),\n\t\t\t\t\tLocalTime.MIN,\n\t\t\t\t\tZoneOffset.ofTotalSeconds($4))\n\t\t\t\t.toLocalDateTime()

			result = dueDate.missedDays(testDate);
			assertEquals(5, result);
			
			dueDate.setIntervalSize(2);
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 8),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(6, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 7),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(7, result);
			
			dueDate.setIntervalSize(3);
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 30),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);

			dueDate.setActiveDays(Set.of(
				WeeklyDueDate.idx(DayOfWeek.SUNDAY),
				WeeklyDueDate.idx(DayOfWeek.MONDAY),
				WeeklyDueDate.idx(DayOfWeek.TUESDAY),
				WeeklyDueDate.idx(DayOfWeek.WEDNESDAY),
				WeeklyDueDate.idx(DayOfWeek.THURSDAY),
				WeeklyDueDate.idx(DayOfWeek.FRIDAY),
				WeeklyDueDate.idx(DayOfWeek.SATURDAY)))
			.setIntervalSize(1);
				

			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(22, result);

			dueDate.setIntervalSize(2);

			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(0, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(1, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(2, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(3, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(4, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(5, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(6, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(7, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(8, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(9, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(10, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 29),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(10, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 30),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(10, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 31),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(10, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 1),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(10, result);
			
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(10, result);
			
			//sun *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(10, result);
			
			//mon *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 5),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(11, result);
			
			//tues *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 6),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(12, result);
			
			//wed *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 7),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(13, result);
			
			//thr *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 8),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(14, result);
			
			//fri *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 9),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(15, result);
			
			
			//sat *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(16, result);
			
			//sun
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(17, result);
			
			//mon
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(17, result);
			
			//tue
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(17, result);
			
			//wed
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(17, result);
			
			//thr
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(17, result);
			
			//fri
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(17, result);
			
			//sat
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(17, result);
			
			//sun *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(17, result);
			
			//mon *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(18, result);
			
			//tue *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(19, result);
			
			//wed *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(20, result);
			
			//thr *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(21, result);
			
			//fri *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(22, result);
			
			//sat *
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(23, result);
			
			//sun
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(24, result);
			
			//mon
			testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
			result = dueDate.missedDays(testDate);
			assertEquals(24, result);

		}
}

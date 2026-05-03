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

class MissedDaysInterval7Tests {

    @Test
    void testFullWeekInterval7() {
				/*
					#calendar 2018
						SU	MO	TU	WE	TH	FR	SA
																01	02
						03	04	05	06	07	08	09
						10	11	12	13	14	15	16
						17	18	19	20	21	22	23
						24	25	26	27	28	29	30
			jan		31	01	02	03	04	05	06
						07	08	09	10	11	12	13	*
						14	15	16	17	18	19	20	1
						21	22	23	24	25	26	27	1	2
			feb		28	29	30	31	01	02	03	1		3
						04	05	06	07	08	09	10	1	2		4
						11	12	13	14	15	16	17	1				5
						18	19	20	21	22	23	24	1	2	3			6
			mar		25	26	27	28	01	02	03	1						7
						04	05	06	07	08	09	10	1	2		4				8
						11	12	13	14	15	16	17	1		3
						18	19	20	21	22	23	24	1	2			5
						25	26	27	28	29	30	31	1
			apr		01	02	03	04	05	06	07	1	2	3	4		6
						08	09	10	11	12	13	14	1
						15	16	17	18	19	20	21	1	2					7
						22	23	24	25	26	27	28	1		3		5
			may		29	30	01	02	03	04	05	1	2		4				8
						06	07	08	09	10	11	12	1
						13	14	15	16	17	18	19	1	2	3			6
						20	21	22	23	24	25	26	1
			jun		27	28	29	30	31  01	02	1	2		4	5
						03	04	05	06	07	08	09	1		3				7
						10	11	12	13	14	15	16	1	2
						17	18	19	20	21	22	23	1
						24	25	26	27	28	29	30	1	2	3	4		6		8
			jul		01	02	03	04	05	06	07	1				5
						08	09	10	11	12	13	14	1	2
						15	16	17	18	19	20	21	1		3
						22	23	24	25	26	27	28	1	2		4			7
			aug		29	30	31	01	02	03	04	1
						05	06	07	08	09	10	11	1	2	3		5	6
						12	13	14	15	16	17	18	1
						19	20	21	22	23	24	25	1	2		4				8
			sep		26	27	28	29	30	31	01	1		3
						02	03	04	05	06	07	08	1	2
						09	10	11	12	13	14	15	1				5		7
						16	17	18	19	20	21	22	1	2	3	4		6
						23	24	25	26	27	28	29	1
			oct		30	01	02	03	04	05	06	1	2
						07	08	09	10	11	12	13	1		3
						14	15	16	17	18	19	20	1	2		4	5			8
						21	22	23	24	25	26	27	1
			nov		28	29	30	31	01	02	03	1	2	3			6	7
						04	05	06	07	08	09	10	1
						11	12	13	14	15	16	17	1	2		4
						18	19	20	21	22	23	24	1		3		5
			dec		25	26	27	28	29	30	01	1	2
						02	03	04	05	06	07	08	1
						09	10	11	12	13	14	15	1	2	3	4		6		8
						16	17	18	19	20	21	22	1						7
						23	24	25	26	27	28	29	1	2			5
						30	31
				*/

				var baselineDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 10),
					LocalTime.MIN,
					ZoneOffset.UTC
				).toLocalDateTime();

				var dueDate = new WeeklyDueDate(Set.of(
						WeeklyDueDate.idx(DayOfWeek.SUNDAY),
						WeeklyDueDate.idx(DayOfWeek.MONDAY),
						WeeklyDueDate.idx(DayOfWeek.TUESDAY),
						WeeklyDueDate.idx(DayOfWeek.WEDNESDAY),
						WeeklyDueDate.idx(DayOfWeek.THURSDAY),
						WeeklyDueDate.idx(DayOfWeek.FRIDAY),
						WeeklyDueDate.idx(DayOfWeek.SATURDAY)),
					baselineDate)
				.setIntervalSize(7);
				
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
				assertEquals(0, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(1, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(2, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 29),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 30),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 31),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 1),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 3),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 5),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 6),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 7),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 8),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 9),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(3, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(4, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(5, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 2, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(6, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 1),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(7, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(8, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 3),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(9, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 5),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 6),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 7),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 8),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 9),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 29),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 30),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 3, 31),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 1),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 3),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 5),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 6),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 7),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 8),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 9),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(10, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(11, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(12, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(13, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(14, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(15, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(16, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 29),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 4, 30),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 1),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 3),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 5),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 6),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 7),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 8),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 9),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 10),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 11),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 12),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 13),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 14),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 15),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 16),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 17),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 18),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 19),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 20),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 21),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Tuesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 29),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Wednesday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 30),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Thursday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 5, 31),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Friday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 6, 1),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Saturday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 6, 2),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Sunday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 6, 3),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(17, result);;

				
				//Monday
				testDate = OffsetDateTime.of(
					LocalDate.of(2018, 6, 4),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
				result = dueDate.missedDays(testDate);
				assertEquals(18, result);;

		}
}

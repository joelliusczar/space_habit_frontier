package dtos.todos.weekly_due_date.missed_days;

import org.junit.jupiter.api.Test;

import space_habit_frontier.engine.dtos.todos.DueDateCalculator;
import space_habit_frontier.engine.dtos.todos.active_days.WeeklyActiveDaysCollection;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

class SavedDateLaterInWeekTests {

	@Test
	void testMissedDays1ActiveDaysInterval3() {

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
			LocalDate.of(2018, 1, 13),
			LocalTime.MIN,
			ZoneOffset.UTC
		).toLocalDateTime();

		var dueDate = new DueDateCalculator(new WeeklyActiveDaysCollection(Set.of(
				WeeklyActiveDaysCollection.idx(DayOfWeek.SUNDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.TUESDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.THURSDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.FRIDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.SATURDAY))),
			baselineDate)
		.setIntervalSize(2);
		
		var testDate = OffsetDateTime.of(
			LocalDate.of(2018, 1, 21),
			LocalTime.MIN,
			ZoneOffset.ofTotalSeconds(0))
		.toLocalDateTime();

		var result = 0L;

//testDate = \(struct SHDatetime\)\{\.year = (\d+), \.month = (\d+), \.day = (\d+), \.timezoneOffset = (-?\d+)\}

//testDate = OffsetDateTime.of(\n\t\t\t\t\tLocalDate.of($1, $2, $3),\n\t\t\t\t\tLocalTime.MIN,\n\t\t\t\t\tZoneOffset.ofTotalSeconds($4))\n\t\t\t\t.toLocalDateTime()

		result = dueDate.missedDays(testDate);
		assertEquals(0, result);
		
		dueDate.setPreviousCheckinDate(LocalDateTime.of(
			LocalDate.of(2018,1,12),
			LocalTime.MIN));
		result = dueDate.missedDays(testDate);
		assertEquals(1, result);
		
		dueDate.setPreviousCheckinDate(LocalDateTime.of(
			LocalDate.of(2018,1,11),
			LocalTime.MIN));
		result = dueDate.missedDays(testDate);
		assertEquals(2, result);
		
		dueDate.setPreviousCheckinDate(LocalDateTime.of(
			LocalDate.of(2018,1,10),
			LocalTime.MIN));
		result = dueDate.missedDays(testDate);
		assertEquals(3, result);
		
		dueDate.setPreviousCheckinDate(LocalDateTime.of(
			LocalDate.of(2018,1,9),
			LocalTime.MIN));
		result = dueDate.missedDays(testDate);
		assertEquals(4, result);
		
		dueDate.setPreviousCheckinDate(LocalDateTime.of(
			LocalDate.of(2018,1,8),
			LocalTime.MIN));
		result = dueDate.missedDays(testDate);
		assertEquals(5, result);
		
		dueDate.setPreviousCheckinDate(LocalDateTime.of(
			LocalDate.of(2018,1,7),
			LocalTime.MIN));
		result = dueDate.missedDays(testDate);
		assertEquals(6, result);
		
		dueDate.setPreviousCheckinDate(LocalDateTime.of(
			LocalDate.of(2018,1,6),
			LocalTime.MIN));
		result = dueDate.missedDays(testDate);
		assertEquals(7, result);
		
		dueDate.setPreviousCheckinDate(LocalDateTime.of(
			LocalDate.of(2018,1,12),
			LocalTime.MIN));
		testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 22),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
		result = dueDate.missedDays(testDate);
		assertEquals(2, result);
		
		testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 23),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
		result = dueDate.missedDays(testDate);
		assertEquals(3, result);
		
		testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 24),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
		result = dueDate.missedDays(testDate);
		assertEquals(4, result);
		
		testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 25),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
		result = dueDate.missedDays(testDate);
		assertEquals(5, result);
		
		testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 26),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
		result = dueDate.missedDays(testDate);
		assertEquals(6, result);
		
		testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 27),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
		result = dueDate.missedDays(testDate);
		assertEquals(7, result);
		
		testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 28),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
		result = dueDate.missedDays(testDate);
		assertEquals(8, result);
		
		testDate = OffsetDateTime.of(
					LocalDate.of(2018, 1, 29),
					LocalTime.MIN,
					ZoneOffset.ofTotalSeconds(-18000))
				.toLocalDateTime();
		result = dueDate.missedDays(testDate);
		assertEquals(8, result);
	}
}

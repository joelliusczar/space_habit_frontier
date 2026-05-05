package dtos.todos.WeeklyDueDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import space_habit_frontier.engine.dtos.todos.WeeklyDueDate;

public class WeeklyDueDateTest {


	
	@Test
	void testIsTodayADueDate() {
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
		var baselineDate = LocalDateTime.of(
			2018, 
			1, 
			10, 
			0, 
			0, 
			0, 
			0);

		var weeklyDueDate = new WeeklyDueDate(
				Set.of(WeeklyDueDate.idx(DayOfWeek.WEDNESDAY)),
				baselineDate)
			.setIntervalSize(3);
			
			
		var testDate = OffsetDateTime.of(
			2018, 
			1,
			31,
			0,
			0,
			0,
			0,
			ZoneOffset.ofTotalSeconds(-18000)
		).toLocalDateTime();
		
		var result = weeklyDueDate.isDateADueDate(testDate);
		assertTrue(result);

//testDate = \(struct SHDatetime\)\{\.year = (\d+), \.month = (\d+), \.day = (\d+), \.timezoneOffset = (-?\d+)\}

//testDate = OffsetDateTime.of(\n\t\t\tLocalDate.of($1, $2, $3),\n\t\t\tLocalTime.MIN,\n\t\t\tZoneOffset.ofTotalSeconds($4))\n\t\t.toLocalDateTime()

		testDate = OffsetDateTime.of(
			LocalDate.of(2018, 1, 31),
			LocalTime.MIN,
			ZoneOffset.ofTotalSeconds(18000))
		.toLocalDateTime();
		result = weeklyDueDate.isDateADueDate(testDate);
		assertTrue(result);

		weeklyDueDate.setDayStartHour(LocalTime.of(6, 0));

		testDate = OffsetDateTime.of(
			LocalDate.of(2018, 1, 31),
			LocalTime.MIN,
			ZoneOffset.ofTotalSeconds(18000))
		.toLocalDateTime();
		result = weeklyDueDate.isDateADueDate(testDate);
		assertTrue(result);
		
		weeklyDueDate.setPreviousCheckinDate(
			LocalDateTime.of(
			LocalDate.of(2018,1,11),
			LocalTime.MIN));
		var _testDate0 = testDate;
		assertThrows(IllegalStateException.class, () -> {
			weeklyDueDate.isDateADueDate(_testDate0);
		});
		
		
		assertThrows(IllegalStateException.class, () -> {
			var _testDate = OffsetDateTime.of(
				LocalDate.of(2018, 2, 1),
				LocalTime.MIN,
				ZoneOffset.ofTotalSeconds(18000))
			.toLocalDateTime();
			weeklyDueDate.isDateADueDate(_testDate);
		});

	}
}

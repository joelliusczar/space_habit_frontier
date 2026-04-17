package dtos.todos.WeeklyDueDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import space_habit_frontier.engine.dtos.todos.WeeklyDueDate;

public class PreviousDueDateTests {
	
	@Test
	void testIsTodayADueDate() {

		/*
		#calendar 2018
		DEC
			SU	MO	TU	WE	TH	FR	SA
													01	02
			03	04	05	06	07	08	09
			10	11	12	13	14	15	16
			17	18	19	20	21	22	23
			24	25	26	27	28	29	30
			31
		JAN
					01	02	03	04	05	06
			07	08	09	10	11	12	13*
			14	15	16	17	18	19	20
			21	22	23	24	25	26	27
			28	29	30	31
		*/

		var baselineDate = ZonedDateTime.of(
			2018, 
			1, 
			7, 
			0, 
			0, 
			0, 
			0,
			ZoneId.of("UTC"));

		var weeklyDueDate = new WeeklyDueDate(
				Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
				baselineDate)
			.setIntervalSize(3);

		var testDate = baselineDate;

		var result = weeklyDueDate.calculatePreviousDueDate(testDate);

		assertEquals(true, result);

	}
}

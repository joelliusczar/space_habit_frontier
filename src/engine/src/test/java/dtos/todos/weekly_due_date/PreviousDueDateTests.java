package dtos.todos.weekly_due_date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

import org.junit.jupiter.api.Test;

import space_habit_frontier.engine.dtos.todos.DueDateCalculator;
import space_habit_frontier.engine.dtos.todos.active_days.ActiveDaysCollection;
import space_habit_frontier.engine.dtos.todos.active_days.WeeklyActiveDaysCollection;

public class PreviousDueDateTests {
	
	@Test
	void testPreviousDate() {

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

		var baselineDateTime = LocalDateTime.of(
			2018, 
			1, 
			7, 
			0, 
			0, 
			0, 
			0);
		var baselineDate = baselineDateTime.toLocalDate();
		var expectedDate = baselineDate;
		var previousCheckinDate = baselineDateTime;

		var weeklyDueDate = new DueDateCalculator(new WeeklyActiveDaysCollection(Set.of(
				WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY)
			)),
			baselineDateTime)
			.setIntervalSize(3);

		var testDate = baselineDateTime;
		expectedDate = baselineDate.plusDays(1);

		var actualPreviousDueDate = weeklyDueDate
			.calculatePreviousDueDate(baselineDateTime);	
		assertEquals(expectedDate, actualPreviousDueDate);
		
		expectedDate = baselineDate;

		previousCheckinDate = previousCheckinDate.plusDays(1);
		weeklyDueDate.setPreviousCheckinDate(previousCheckinDate);
		testDate = testDate.plusDays(81);
		expectedDate = expectedDate.plusDays(66);

		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(65);
		expectedDate = baselineDate.plusDays(64);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(66);
		expectedDate = baselineDate.plusDays(64);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(64);
		expectedDate = baselineDate.plusDays(45);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(72);
		expectedDate = baselineDate.plusDays(66);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(5);
		expectedDate = baselineDate.plusDays(3);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(2);
		expectedDate = baselineDate.plusDays(1);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(24);
		expectedDate = baselineDate.plusDays(22);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(22);
		expectedDate = baselineDate.plusDays(3);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(50);
		expectedDate = baselineDate.plusDays(45);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDateTime.plusDays(1);
		weeklyDueDate.setPreviousCheckinDate(previousCheckinDate);
		weeklyDueDate.setIntervalSize(1);

		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(80);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(65);
		expectedDate = baselineDate.plusDays(64);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(66);
		expectedDate = baselineDate.plusDays(64);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(64);
		expectedDate = baselineDate.plusDays(59);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(72);
		expectedDate = baselineDate.plusDays(71);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(5);
		expectedDate = baselineDate.plusDays(3);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(2);
		expectedDate = baselineDate.plusDays(1);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(24);
		expectedDate = baselineDate.plusDays(22);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(22);
		expectedDate = baselineDate.plusDays(17);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDateTime.plusDays(5);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDays(new WeeklyActiveDaysCollection(
				Set.of(WeeklyActiveDaysCollection.idx(DayOfWeek.FRIDAY))))
			.setIntervalSize(3);

		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(68);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(6);
		expectedDate = baselineDate.plusDays(5);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		weeklyDueDate.setIntervalSize(1);

		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(75);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(6);
		expectedDate = baselineDate.plusDays(5);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDateTime;
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDays(new WeeklyActiveDaysCollection(
				Set.of(WeeklyActiveDaysCollection.idx(DayOfWeek.SUNDAY))))
			.setIntervalSize(3);

		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(63);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(62);
		expectedDate = baselineDate.plusDays(42);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(1);
		expectedDate = baselineDate.plusDays(0);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		weeklyDueDate.setIntervalSize(1);

		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(77);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(62);
		expectedDate = baselineDate.plusDays(56);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(1);
		expectedDate = baselineDate.plusDays(0);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(7);
		expectedDate = baselineDate.plusDays(0);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDateTime.plusDays(6);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDays(new WeeklyActiveDaysCollection(
				Set.of(WeeklyActiveDaysCollection.idx(DayOfWeek.SATURDAY))))
			.setIntervalSize(3);

		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(69);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(13);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(20);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(26);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(34);
		expectedDate = baselineDate.plusDays(27);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(68);
		expectedDate = baselineDate.plusDays(48);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(7);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDateTime.plusDays(6);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setIntervalSize(1);
		
		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(76);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(13);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(20);
		expectedDate = baselineDate.plusDays(13);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(26);
		expectedDate = baselineDate.plusDays(20);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(34);
		expectedDate = baselineDate.plusDays(27);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(7);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDateTime.plusDays(6);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDays(WeeklyActiveDaysCollection.fullWeek())
			.setIntervalSize(3);



		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(69);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(13);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(20);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(7);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(34);
		expectedDate = baselineDate.plusDays(27);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(68);
		expectedDate = baselineDate.plusDays(67);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDateTime.plusDays(3);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setIntervalSize(2);

		testDate = baselineDateTime.plusDays(4);
		expectedDate = baselineDate.plusDays(3);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		previousCheckinDate = baselineDateTime.plusDays(6);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setIntervalSize(1);


		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(80);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(13);
		expectedDate = baselineDate.plusDays(12);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);
		
		testDate = baselineDateTime.plusDays(20);
		expectedDate = baselineDate.plusDays(19);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(7);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(34);
		expectedDate = baselineDate.plusDays(33);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(68);
		expectedDate = baselineDate.plusDays(67);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		previousCheckinDate = baselineDateTime.plusDays(1);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDays(new WeeklyActiveDaysCollection(Set.of(
				WeeklyActiveDaysCollection.idx(DayOfWeek.SUNDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY))))
			.setIntervalSize(3);

		testDate = baselineDateTime.plusDays(7);
		expectedDate = baselineDate.plusDays(1);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDateTime.plusDays(7);
		expectedDate = baselineDate.plusDays(1);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);
		
	}	

	@Test
	void test__findPrevDayOfWeek() 
		throws NoSuchMethodException,
			IllegalAccessException,
			InvocationTargetException {
		var weeklyActiveDays = 
				new WeeklyActiveDaysCollection(Set.of(
					WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY),
					WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY)));
		var method = ActiveDaysCollection.class
			.getDeclaredMethod(
				"findPrevDayOfPeriod",
				int.class,
				boolean.class);
		method.setAccessible(true);
		var result = method.invoke(
			weeklyActiveDays, 
			WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY), 
			true);
		assertEquals(WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY), result);

		result = method.invoke(
			weeklyActiveDays, 
			WeeklyActiveDaysCollection.idx(DayOfWeek.TUESDAY), 
			true);
		assertEquals(WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY), result);

		result = method.invoke(
			weeklyActiveDays, 
			WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY), 
			true);
		assertEquals(WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY), result);

		result = method.invoke(
			weeklyActiveDays, 
			WeeklyActiveDaysCollection.idx(DayOfWeek.THURSDAY), 
			true);
		assertEquals(WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY), result);

		result = method.invoke(
			weeklyActiveDays, 
			WeeklyActiveDaysCollection.idx(DayOfWeek.SUNDAY), 
			true);
		assertEquals(WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY), result);

		result = method.invoke(
			weeklyActiveDays, 
			WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY), 
			false);
		assertEquals(WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY), result);

		result = method.invoke(
			weeklyActiveDays, 
			WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY), 
			false);
		assertEquals(WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY), result);

		result = method.invoke(
			weeklyActiveDays, 
			WeeklyActiveDaysCollection.idx(DayOfWeek.SATURDAY), 
			false);
		assertEquals(WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY), result);

		result = method.invoke(
			weeklyActiveDays, 
			WeeklyActiveDaysCollection.idx(DayOfWeek.SUNDAY), 
			false);
		assertEquals(WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY), result);
	}

	@Test
	void testPreviousDueDateDiffTimezone() {
			/*
			#calendar 2018
			DEC
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
			
			var previousCheckinDate = OffsetDateTime.of(
				LocalDate.of(2018, 1, 8),
				LocalTime.MIN,
				ZoneOffset.ofTotalSeconds(-18000)
			).toLocalDateTime();

			var weeklyDueDate = new DueDateCalculator(
					new WeeklyActiveDaysCollection(Set.of(
						WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY),
						WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY))),
					previousCheckinDate)
				.setIntervalSize(3);

			var testDate = OffsetDateTime.of(
				LocalDate.of(2018, 1, 17),
				LocalTime.MIN,
				ZoneOffset.ofTotalSeconds(-36000)
			).toLocalDateTime();

			var expectedDate = OffsetDateTime.of(
				LocalDate.of(2018, 1, 10),
				LocalTime.MIN,
				ZoneOffset.ofTotalSeconds(-36000)
			).toLocalDate();

			var actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
			assertEquals(actualPreviousDueDate, expectedDate);

	}
}

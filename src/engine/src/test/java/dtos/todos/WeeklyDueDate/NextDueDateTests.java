package dtos.todos.WeeklyDueDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import space_habit_frontier.engine.dtos.todos.DueDateCalculator;
import space_habit_frontier.engine.dtos.todos.active_days.WeeklyActiveDaysCollection;

public class NextDueDateTests {
	
	@Test
	void testDueDate() {
		var baselineDateTime = LocalDateTime.of(
			2018, 
			1, 
			7, 
			0, 
			0, 
			0, 
			0);
		var baselineDate =  baselineDateTime.toLocalDate();
		

		var weeklyDueDate = new DueDateCalculator(new WeeklyActiveDaysCollection(Set.of(
				WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY)
			)),
			baselineDateTime)
			.setIntervalSize(3);

		var testDate = baselineDateTime;

		var expectedDate = baselineDate;

		weeklyDueDate.setPreviousCheckinDate(baselineDateTime.plusDays(1));
		testDate = baselineDateTime.plusDays(81);
		expectedDate = baselineDate.plusDays(85);

		var actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(65);
		expectedDate = baselineDate.plusDays(66);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(63);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(62);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(50);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(46);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(66);
		expectedDate = baselineDate.plusDays(66);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(64);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);


		weeklyDueDate.setIntervalSize(1);
		testDate = baselineDateTime.plusDays(62);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(63);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(64);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(65);
		expectedDate = baselineDate.plusDays(66);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(66);
		expectedDate = baselineDate.plusDays(66);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(67);
		expectedDate = baselineDate.plusDays(71);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(68);
		expectedDate = baselineDate.plusDays(71);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(70);
		expectedDate = baselineDate.plusDays(71);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(71);
		expectedDate = baselineDate.plusDays(71);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(72);
		expectedDate = baselineDate.plusDays(73);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(73);
		expectedDate = baselineDate.plusDays(73);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

		testDate = baselineDateTime.plusDays(74);
		expectedDate = baselineDate.plusDays(78);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(actualDate, expectedDate);

	}
}

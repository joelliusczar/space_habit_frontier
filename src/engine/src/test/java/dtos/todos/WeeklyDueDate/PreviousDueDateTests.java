package dtos.todos.WeeklyDueDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import space_habit_frontier.engine.dtos.todos.WeeklyDueDate;

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

		var baselineDate = LocalDateTime.of(
			2018, 
			1, 
			7, 
			0, 
			0, 
			0, 
			0);
		var expectedDate = baselineDate;
		var previousCheckinDate = baselineDate;

		var weeklyDueDate = new WeeklyDueDate(
				Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
				baselineDate)
			.setIntervalSize(3);

		var testDate = baselineDate;

		assertThrows(IllegalArgumentException.class, () -> {
			var testDate1 = baselineDate;
			weeklyDueDate.calculatePreviousDueDate(testDate1);	
		});

		previousCheckinDate = previousCheckinDate.plusDays(1);
		weeklyDueDate.setPreviousCheckinDate(previousCheckinDate);
		testDate = testDate.plusDays(81);
		expectedDate = expectedDate.plusDays(66);

		var actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(65);
		expectedDate = baselineDate.plusDays(64);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(66);
		expectedDate = baselineDate.plusDays(64);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(64);
		expectedDate = baselineDate.plusDays(45);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(72);
		expectedDate = baselineDate.plusDays(66);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(5);
		expectedDate = baselineDate.plusDays(3);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(2);
		expectedDate = baselineDate.plusDays(1);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(24);
		expectedDate = baselineDate.plusDays(22);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(22);
		expectedDate = baselineDate.plusDays(3);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(50);
		expectedDate = baselineDate.plusDays(45);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDate.plusDays(1);
		weeklyDueDate.setPreviousCheckinDate(previousCheckinDate);
		weeklyDueDate.setIntervalSize(1);

		testDate = baselineDate.plusDays(81);
		expectedDate = baselineDate.plusDays(80);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(65);
		expectedDate = baselineDate.plusDays(64);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(66);
		expectedDate = baselineDate.plusDays(64);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(64);
		expectedDate = baselineDate.plusDays(59);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(72);
		expectedDate = baselineDate.plusDays(71);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(5);
		expectedDate = baselineDate.plusDays(3);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(2);
		expectedDate = baselineDate.plusDays(1);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(24);
		expectedDate = baselineDate.plusDays(22);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(22);
		expectedDate = baselineDate.plusDays(17);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDate.plusDays(5);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDaysMap(Set.of(DayOfWeek.FRIDAY))
			.setIntervalSize(3);

		testDate = baselineDate.plusDays(81);
		expectedDate = baselineDate.plusDays(68);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(6);
		expectedDate = baselineDate.plusDays(5);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		weeklyDueDate.setIntervalSize(1);

		testDate = baselineDate.plusDays(81);
		expectedDate = baselineDate.plusDays(75);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(6);
		expectedDate = baselineDate.plusDays(5);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDate;
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDaysMap(Set.of(DayOfWeek.SUNDAY))
			.setIntervalSize(3);

		testDate = baselineDate.plusDays(81);
		expectedDate = baselineDate.plusDays(63);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(62);
		expectedDate = baselineDate.plusDays(42);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(1);
		expectedDate = baselineDate.plusDays(0);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		weeklyDueDate.setIntervalSize(1);

		testDate = baselineDate.plusDays(81);
		expectedDate = baselineDate.plusDays(77);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(62);
		expectedDate = baselineDate.plusDays(56);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(1);
		expectedDate = baselineDate.plusDays(0);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(7);
		expectedDate = baselineDate.plusDays(0);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDate.plusDays(6);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDaysMap(Set.of(DayOfWeek.SATURDAY))
			.setIntervalSize(3);

		testDate = baselineDate.plusDays(81);
		expectedDate = baselineDate.plusDays(69);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(13);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(20);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(26);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(34);
		expectedDate = baselineDate.plusDays(27);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(68);
		expectedDate = baselineDate.plusDays(48);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(7);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDate.plusDays(6);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setIntervalSize(1);
		
		testDate = baselineDate.plusDays(81);
		expectedDate = baselineDate.plusDays(76);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(13);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(20);
		expectedDate = baselineDate.plusDays(13);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(26);
		expectedDate = baselineDate.plusDays(20);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(34);
		expectedDate = baselineDate.plusDays(27);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(7);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDate.plusDays(6);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDaysMap(Set.of(DayOfWeek.values()))
			.setIntervalSize(3);

		testDate = baselineDate.plusDays(81);
		expectedDate = baselineDate.plusDays(69);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(13);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(20);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(7);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(34);
		expectedDate = baselineDate.plusDays(27);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(68);
		expectedDate = baselineDate.plusDays(67);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);


		previousCheckinDate = baselineDate.plusDays(3);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setIntervalSize(2);

		testDate = baselineDate.plusDays(4);
		expectedDate = baselineDate.plusDays(3);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		previousCheckinDate = baselineDate.plusDays(6);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setIntervalSize(1);


		testDate = baselineDate.plusDays(81);
		expectedDate = baselineDate.plusDays(80);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(13);
		expectedDate = baselineDate.plusDays(12);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);
		
		testDate = baselineDate.plusDays(20);
		expectedDate = baselineDate.plusDays(19);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(7);
		expectedDate = baselineDate.plusDays(6);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(34);
		expectedDate = baselineDate.plusDays(33);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(68);
		expectedDate = baselineDate.plusDays(67);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		previousCheckinDate = baselineDate.plusDays(1);
		weeklyDueDate
			.setPreviousCheckinDate(previousCheckinDate)
			.setActiveDaysMap(Set.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY))
			.setIntervalSize(3);

		testDate = baselineDate.plusDays(7);
		expectedDate = baselineDate.plusDays(1);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);

		testDate = baselineDate.plusDays(7);
		expectedDate = baselineDate.plusDays(1);
		actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
		assertEquals(actualPreviousDueDate, expectedDate);
		
	}	

	@Test
	void test__findPrevDayOfWeek() 
		throws NoSuchMethodException,
			IllegalAccessException,
			InvocationTargetException {
		var weeklyDueDate = new WeeklyDueDate(
				Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
				LocalDateTime.now());
		var method = WeeklyDueDate.class
			.getDeclaredMethod(
				"__findPrevDayOfWeek",
				DayOfWeek.class,
				boolean.class);
		method.setAccessible(true);
		var result = method.invoke(weeklyDueDate, DayOfWeek.MONDAY, true);
		assertEquals(DayOfWeek.WEDNESDAY, result);

		result = method.invoke(weeklyDueDate, DayOfWeek.TUESDAY, true);
		assertEquals(DayOfWeek.MONDAY, result);

		result = method.invoke(weeklyDueDate, DayOfWeek.WEDNESDAY, true);
		assertEquals(DayOfWeek.MONDAY, result);

		result = method.invoke(weeklyDueDate, DayOfWeek.THURSDAY, true);
		assertEquals(DayOfWeek.WEDNESDAY, result);

		result = method.invoke(weeklyDueDate, DayOfWeek.SUNDAY, true);
		assertEquals(DayOfWeek.WEDNESDAY, result);

		result = method.invoke(weeklyDueDate, DayOfWeek.MONDAY, false);
		assertEquals(DayOfWeek.WEDNESDAY, result);

		result = method.invoke(weeklyDueDate, DayOfWeek.WEDNESDAY, false);
		assertEquals(DayOfWeek.WEDNESDAY, result);

		result = method.invoke(weeklyDueDate, DayOfWeek.SATURDAY, false);
		assertEquals(DayOfWeek.WEDNESDAY, result);

		result = method.invoke(weeklyDueDate, DayOfWeek.SUNDAY, false);
		assertEquals(DayOfWeek.WEDNESDAY, result);
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

			var weeklyDueDate = new WeeklyDueDate(
					Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
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
			).toLocalDateTime();

			var actualPreviousDueDate = weeklyDueDate.calculatePreviousDueDate(testDate);
			assertEquals(actualPreviousDueDate, expectedDate);

	}
}

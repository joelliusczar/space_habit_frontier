package dtos.todos.weekly_due_date;

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
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(65);
		expectedDate = baselineDate.plusDays(66);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(63);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(62);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(50);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(46);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(66);
		expectedDate = baselineDate.plusDays(66);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(64);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);


		weeklyDueDate.setIntervalSize(1);
		testDate = baselineDateTime.plusDays(62);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(63);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(64);
		expectedDate = baselineDate.plusDays(64);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(65);
		expectedDate = baselineDate.plusDays(66);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(66);
		expectedDate = baselineDate.plusDays(66);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(67);
		expectedDate = baselineDate.plusDays(71);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(68);
		expectedDate = baselineDate.plusDays(71);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(70);
		expectedDate = baselineDate.plusDays(71);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(71);
		expectedDate = baselineDate.plusDays(71);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(72);
		expectedDate = baselineDate.plusDays(73);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(73);
		expectedDate = baselineDate.plusDays(73);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(74);
		expectedDate = baselineDate.plusDays(78);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);
	}

	@Test
	void testNextDueDate_sun() {
		//effective date is 
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
jun		27	28	29	30	31	01	02	1	2		4	5
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
				WeeklyActiveDaysCollection.idx(DayOfWeek.SUNDAY)
			)),
			baselineDateTime)
			.setIntervalSize(3);

		var testDate = baselineDateTime;

		var expectedDate = baselineDate;

		testDate = baselineDateTime.plusDays(2);
		expectedDate = baselineDate.plusDays(21);

		var actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);


		weeklyDueDate.setIntervalSize(2);

		expectedDate = baselineDate.plusDays(14);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		// testDate = baselineDateTime.plusDays(0)
	}

	@Test
	void testDueDate_previousCheckinInactive_mon_wed() {
		//effective date is 
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
jun		27	28	29	30	31	01	02	1	2		4	5
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
		var baselineDateTime = LocalDateTime.of(
			2018, 
			1, 
			11, 
			0, 
			0, 
			0, 
			0);
		var baselineDate = baselineDateTime.toLocalDate();

		var weeklyDueDate = new DueDateCalculator(new WeeklyActiveDaysCollection(
			Set.of(
				WeeklyActiveDaysCollection.idx(DayOfWeek.MONDAY),
				WeeklyActiveDaysCollection.idx(DayOfWeek.WEDNESDAY)
			)),
			baselineDateTime)
			.setIntervalSize(1);

		var testDate = baselineDateTime.plusDays(1);
		var expectedDate = baselineDate.plusDays(4);

		var actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(2);
		expectedDate = baselineDate.plusDays(4);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(3);
		expectedDate = baselineDate.plusDays(4);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(4);
		expectedDate = baselineDate.plusDays(4);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(5);
		expectedDate = baselineDate.plusDays(6);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(6);
		expectedDate = baselineDate.plusDays(6);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(7);
		expectedDate = baselineDate.plusDays(11);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);


		weeklyDueDate.setIntervalSize(2);

		testDate = baselineDateTime.plusDays(1);
		expectedDate = baselineDate.plusDays(4);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(2);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(4);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(4);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(5);
		expectedDate = baselineDate.plusDays(6);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(6);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(7);
		expectedDate = baselineDate.plusDays(18);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(8);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(9);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(10);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(11);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(19);
		expectedDate = baselineDate.plusDays(20);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(21);
		expectedDate = baselineDate.plusDays(32);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(22);
		expectedDate = baselineDate.plusDays(32);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);


		weeklyDueDate.setIntervalSize(3);

		testDate = baselineDateTime.plusDays(1);
		expectedDate = baselineDate.plusDays(18);

	}

	@Test
	void testDueDate_previousCheckinInactive_sun() {
		//effective date is 
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
jun		27	28	29	30	31	01	02	1	2		4	5
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
		var baselineDateTime = LocalDateTime.of(
			2018, 
			1, 
			11, 
			0, 
			0, 
			0, 
			0);
		var baselineDate = baselineDateTime.toLocalDate();

		var weeklyDueDate = new DueDateCalculator(new WeeklyActiveDaysCollection(
			Set.of(
				WeeklyActiveDaysCollection.idx(DayOfWeek.SUNDAY)
			)),
			baselineDateTime)
			.setIntervalSize(1);

		var testDate = baselineDateTime.plusDays(1);
		var expectedDate = baselineDate.plusDays(3);

		var actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(2);
		expectedDate = baselineDate.plusDays(3);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(3);
		expectedDate = baselineDate.plusDays(3);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

		testDate = baselineDateTime.plusDays(4);
		expectedDate = baselineDate.plusDays(10);

		actualDate = weeklyDueDate.calculateNextDueDate(testDate);
		assertEquals(expectedDate, actualDate);

	}
	
}

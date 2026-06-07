package dtos.todos.weekly_due_date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import space_habit_frontier.engine.dtos.todos.DueDateCalculator;
import space_habit_frontier.engine.dtos.todos.TodoActiveDaysConverters;
import space_habit_frontier.engine.dtos.todos.active_days.WeeklyActiveDaysCollection;
import utilities.ActiveDaysCalendar;

public class ExhaustiveDueDateTests {

	// @Disabled
	@Test
	void testDueDateExhaustive() {

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

		var baselineDate = LocalDateTime.of(
			2018,
			1,
			7,
			0,
			0,
			0);
		var activeDaysCollection = new WeeklyActiveDaysCollection(new HashSet<>());
		var dueDateCalculator = new DueDateCalculator(
			null,
			null);
		for (var interval = 1; interval < 8; interval++) {
			for (byte week = 1; week < 127; week++) {
				var activeDays = TodoActiveDaysConverters.weekActiveDaysSet(week);
				var arbitraryWeekCount = 55;
				var calendar = new ActiveDaysCalendar(
					activeDays,
					7,
					interval,
					arbitraryWeekCount);
				for (var prev = 0; prev < calendar.validCount(); prev++) {
					calendar.loadActiveDays(prev);
					calendar.loadDaysTill();
					dueDateCalculator
						.setActiveDays(activeDaysCollection.setStore(activeDays))
						.setPreviousCheckinDate(baselineDate.plusDays(prev))
						.setIntervalSize(interval);
					for (
							var checkin = prev + 1;
							checkin < calendar.validCount();
							checkin++) {
						var actual = dueDateCalculator
							.calculateNextDueDate(baselineDate.plusDays(checkin));
						var expected = baselineDate
							.plusDays(calendar.get(checkin).daysTillActive() + checkin)
							.toLocalDate();
						try {
							assertEquals(actual, expected);
						}
						catch(AssertionFailedError e) {
							throw e;
						}
					}
				}	
			}
		}
	}
}

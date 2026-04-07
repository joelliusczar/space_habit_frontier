package space_habit_frontier.engine.dtos.todos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import space_habit_frontier.engine.dtos.MonthDay;
import space_habit_frontier.engine.dtos.TitledId;
import space_habit_frontier.engine.utilities.SHFEnumUtils;

public class TodoFormDto extends TitledId {

	private String note;
	private short repeattype;
	private short repeatcount;
	private ZonedDateTime duedatetimestamp;
	private List<MonthDay> yearactivedays;
	private Integer[] monthactivedays;
	private boolean rateinversionflag;
	private List<String> weekactivedays;
	private short repeatrate;
	private boolean poisonous;
	private short risk;
	private ZonedDateTime effectivedatetimestamp;
	private ZonedDateTime expirationdatetimestamp;

	public TodoFormDto() {
		super(new UUID(0, 0), "");
	}

	public TodoFormDto(
		UUID id,
		String title,
		String note,
		short repeattype,
		short repeatcount,
		ZonedDateTime duedatetimestamp,
		List<MonthDay> yearactivedays,
		Integer[] monthactivedays,
		boolean rateinversionflag,
		List<String> weekactivedays,
		short repeatrate,
		boolean poisonous,
		short risk,
		ZonedDateTime effectivedatetimestamp,
		ZonedDateTime expirationdatetimestamp) {
		super(id, title);
		this.note = note;
		this.repeattype = repeattype;
		this.duedatetimestamp = duedatetimestamp;
		this.yearactivedays = yearactivedays;
		this.monthactivedays = monthactivedays;
		this.rateinversionflag = rateinversionflag;
		this.weekactivedays = weekactivedays;
		this.repeatrate = repeatrate;
		this.repeatcount = repeatcount;
		this.poisonous = poisonous;
		this.risk = risk;
		this.effectivedatetimestamp = effectivedatetimestamp;
		this.expirationdatetimestamp = expirationdatetimestamp;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// Getter and Setter for repeattype
	public short getRepeattype() {
		return repeattype;
	}

	public void setRepeattype(short repeattype) {
			this.repeattype = repeattype;
	}

	// Getter and Setter for dueDate
	public ZonedDateTime getDuedatetimestamp() {
			return duedatetimestamp;
	}

	public void setDuedatetimestamp(ZonedDateTime dueDate) {
			this.duedatetimestamp = dueDate;
	}

	// Getter and Setter for yearlyDueDays
	public List<MonthDay> getYearactivedays() {
			return yearactivedays;
	}

	public void setYearactivedays(List<MonthDay> yearlyDueDays) {
			this.yearactivedays = yearlyDueDays;
	}

	// Getter and Setter for monthlyDueDays
	public Integer[] getMonthactivedays() {
			return monthactivedays;
	}

	public void setMonthlyDueDays(Integer[] monthactivedays) {
			this.monthactivedays = monthactivedays;
	}

	// Getter and Setter for monthlySkipMod
	public boolean isRateinversionflag() {
			return rateinversionflag;
	}

	public void setRateinversionflag(boolean monthlySkipMod) {
			this.rateinversionflag = monthlySkipMod;
	}

	// Getter and Setter for weekactivedays
	public List<String> getWeekactivedays() {
			return weekactivedays;
	}

	public void setWeekactivedays(List<String> dueDaysOfWeek) {
			this.weekactivedays = dueDaysOfWeek;
	}

	// Getter and Setter for dailyRate
	public short getRepeatrate() {
			return repeatrate;
	}

	public void setRepeatrate(short repeatrate) {
			this.repeatrate = repeatrate;
	}

	// Getter and Setter for dailyRate
	public short getRepeatcount() {
			return repeatcount;
	}

	public void setRepeatcount(short repeatcount) {
			this.repeatcount = repeatcount;
	}

	// Getter and Setter for poisonous
	public boolean isPoisonous() {
			return poisonous;
	}

	public void setPoisonous(boolean poisonous) {
			this.poisonous = poisonous;
	}

	// Getter and Setter for danger
	public short getRisk() {
			return risk;
	}

	public void setRisk(short risk) {
			this.risk = risk;
	}

	// Getter and Setter for activeFromDate
	public ZonedDateTime getEffectivedatetimestamp() {
			return effectivedatetimestamp;
	}

	public void setEffectivedatetimestamp(ZonedDateTime activeFromDate) {
			this.effectivedatetimestamp = activeFromDate;
	}

	// Getter and Setter for activeToDate
	public ZonedDateTime getExpirationdatetimestamp() {
			return expirationdatetimestamp;
	}

	public void setActiveToDate(ZonedDateTime activeToDate) {
			this.expirationdatetimestamp = activeToDate;
	}

	public BitSet getWeekactivedaysByteString() {
		var result = new BitSet(7);
		SHFEnumUtils.loopEnum(DayOfWeek.class, day -> {
			result.set(
				day.getValue() % 7, 
				weekactivedays.contains(day.name().toLowerCase()));
		});
		return result;
	}

	public Integer[] getYearactivedaysIntegerArray() {
		var monthMap = Map.ofEntries(
			Map.entry(Month.JANUARY.name().toLowerCase(), Month.JANUARY),
			Map.entry(Month.FEBRUARY.name().toLowerCase(), Month.FEBRUARY),
			Map.entry(Month.MARCH.name().toLowerCase(), Month.MARCH),
			Map.entry(Month.APRIL.name().toLowerCase(), Month.APRIL),
			Map.entry(Month.MAY.name().toLowerCase(), Month.MAY),
			Map.entry(Month.JUNE.name().toLowerCase(), Month.JUNE),
			Map.entry(Month.JULY.name().toLowerCase(), Month.JULY),
			Map.entry(Month.AUGUST.name().toLowerCase(), Month.AUGUST),
			Map.entry(Month.SEPTEMBER.name().toLowerCase(), Month.SEPTEMBER),
			Map.entry(Month.OCTOBER.name().toLowerCase(), Month.OCTOBER),
			Map.entry(Month.NOVEMBER.name().toLowerCase(), Month.NOVEMBER),
			Map.entry(Month.DECEMBER.name().toLowerCase(), Month.DECEMBER)
		);
		var result = yearactivedays.stream().map(d -> {
			//use leap year so that all dates are captured
			var date = LocalDate.of(
				2000,
				monthMap.get(d.month().toLowerCase()),
				d.day());
			return Integer.valueOf(date.getDayOfYear());
		}).toArray(Integer[]::new);

		return result;
	}


}

/*
 * 
 * public int CycleRateTypeId { get; set; } = (int)CycleRateTypes.Daily;

	public short DailyRate { get; set; }

	public short WeeklyRate { get; set; }

	public short MonthlyRate { get; set; }

	//public WeekDaySelection WeekDaySelection { get; set; }
	//	= new WeekDaySelection();

	//public ICollection<WeekDaySelection> MonthlyDaySelection { get; set; }
	//	= new List<WeekDaySelection>
	//	{
	//		new WeekDaySelection(),
	//		new WeekDaySelection(),
	//		new WeekDaySelection(),
	//		new WeekDaySelection()
	//	};

	//public ICollection<MonthDay> YearlyDaySelection { get; set; }
	//	= new List<MonthDay>();

	//public WeekDaySelection WeekDaySkipSelection { get; set; }
	//	= new WeekDaySelection();

	//public ICollection<WeekDaySelection> MonthlyDaySkipSelection { get; set; }
	//	= new List<WeekDaySelection>
	//	{
	//		new WeekDaySelection(),
	//		new WeekDaySelection(),
	//		new WeekDaySelection(),
	//		new WeekDaySelection()
	//	};

	//public ICollection<MonthDay> YearlyDaySkipSelection { get; set; }
	//	= new List<MonthDay>();

	//public byte Difficulty { get; set; } = 5;

	//public byte Importance { get; set; } = 5;

	//public DateTime ActiveFrom { get; set; } = DateTime.UtcNow;

	//public DateTime? ActiveTo { get; set; } = null;
*/
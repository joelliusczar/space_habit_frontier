package space_habit_frontier.engine.dtos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TodoFormDto extends TitledId {

	private String note;
	private int repeattype;
	private ZonedDateTime duedatetimestamp;
	private List<MonthDay> yearactivedays;
	private boolean yearlySkipMod;
	private List<Integer> monthlyDueDays;
	private boolean monthlySkipMod;
	private List<String> weekactivedays;
	private int repeatrate;
	private boolean poisonous;
	private short risk;
	private ZonedDateTime effectivedatetimestamp;
	private String activeToDate;

	public TodoFormDto() {
		super(new UUID(0, 0), "");
	}

	public TodoFormDto(
		UUID id,
		String title,
		String note,
		int repeattype,
		ZonedDateTime duedatetimestamp,
		List<MonthDay> yearactivedays,
		boolean yearlySkipMod,
		List<Integer> monthlyDueDays,
		boolean monthlySkipMod,
		List<String> weekactivedays,
		int repeatrate,
		boolean poisonous,
		short risk,
		ZonedDateTime effectivedatetimestamp,
		String activeToDate) {
		super(id, title);
		this.note = note;
		this.repeattype = repeattype;
		this.duedatetimestamp = duedatetimestamp;
		this.yearactivedays = yearactivedays;
		this.yearlySkipMod = yearlySkipMod;
		this.monthlyDueDays = monthlyDueDays;
		this.monthlySkipMod = monthlySkipMod;
		this.weekactivedays = weekactivedays;
		this.repeatrate = repeatrate;
		this.poisonous = poisonous;
		this.risk = risk;
		this.effectivedatetimestamp = effectivedatetimestamp;
		this.activeToDate = activeToDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// Getter and Setter for repeattype
	public int getRepeattype() {
		return repeattype;
	}

	public void setRepeattype(int repeattype) {
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

	// Getter and Setter for yearlySkipMod
	public boolean isYearlySkipMod() {
			return yearlySkipMod;
	}

	public void setYearlySkipMod(boolean yearlySkipMod) {
			this.yearlySkipMod = yearlySkipMod;
	}

	// Getter and Setter for monthlyDueDays
	public List<Integer> getMonthlyDueDays() {
			return monthlyDueDays;
	}

	public void setMonthlyDueDays(List<Integer> monthlyDueDays) {
			this.monthlyDueDays = monthlyDueDays;
	}

	// Getter and Setter for monthlySkipMod
	public boolean isMonthlySkipMod() {
			return monthlySkipMod;
	}

	public void setMonthlySkipMod(boolean monthlySkipMod) {
			this.monthlySkipMod = monthlySkipMod;
	}

	// Getter and Setter for weekactivedays
	public List<String> getWeekactivedays() {
			return weekactivedays;
	}

	public void setWeekactivedays(List<String> dueDaysOfWeek) {
			this.weekactivedays = dueDaysOfWeek;
	}

	// Getter and Setter for dailyRate
	public int getRepeatrate() {
			return repeatrate;
	}

	public void setRepeatrate(int dailyRate) {
			this.repeatrate = dailyRate;
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
	public String getActiveToDate() {
			return activeToDate;
	}

	public void setActiveToDate(String activeToDate) {
			this.activeToDate = activeToDate;
	}

	public String getWeekactivedaysByteString() {
		var result = String.format("%d%d%d%d%d%d%d",
			(weekactivedays.contains(DayOfWeek.MONDAY.name().toLowerCase())) 
				? 1 : 0,
			(weekactivedays.contains(DayOfWeek.TUESDAY.name().toLowerCase())) 
				? 1 : 0,
			(weekactivedays.contains(DayOfWeek.WEDNESDAY.name().toLowerCase())) 
				? 1 : 0,
			(weekactivedays.contains(DayOfWeek.THURSDAY.name().toLowerCase())) 
				? 1 : 0,
			(weekactivedays.contains(DayOfWeek.FRIDAY.name().toLowerCase())) 
				? 1 : 0,
			(weekactivedays.contains(DayOfWeek.SATURDAY.name().toLowerCase())) 
				? 1 : 0,
			(weekactivedays.contains(DayOfWeek.SUNDAY.name().toLowerCase())) 
				? 1 : 0
		);
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
			var date = LocalDate.of(2000, monthMap.get(d.month()), d.day());
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
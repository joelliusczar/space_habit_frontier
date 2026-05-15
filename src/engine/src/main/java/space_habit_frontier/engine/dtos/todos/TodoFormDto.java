package space_habit_frontier.engine.dtos.todos;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import space_habit_frontier.engine.dtos.MonthDay;
import space_habit_frontier.engine.dtos.TitledId;

public class TodoFormDto extends TitledId {

	private String note;
	private short repeattype;
	private short repeatcount;
	private ZonedDateTime duedatetimestamp;
	private List<MonthDay> yearactivedays;
	private Integer[] monthactivedays;
	private boolean rateinversionflag;
	private Collection<String> weekactivedays;
	private short repeatrate;
	private boolean poisonous;
	private short risk;
	private ZonedDateTime effectivedatetimestamp;
	private ZonedDateTime expirationdatetimestamp;

	public TodoFormDto() {
		super(new UUID(0, 0), "");
	}

	public TodoFormDto(UUID id, String title) {
		super(id, title);
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

	public TodoFormDto setNote(String note) {
		this.note = note;
		return this;
	}

	// Getter and Setter for repeattype
	public short getRepeattype() {
		return repeattype;
	}

	public TodoFormDto setRepeattype(short repeattype) {
			this.repeattype = repeattype;
			return this;
	}

	// Getter and Setter for dueDate
	public ZonedDateTime getDuedatetimestamp() {
			return duedatetimestamp;
	}

	public TodoFormDto setDuedatetimestamp(ZonedDateTime dueDate) {
			this.duedatetimestamp = dueDate;
			return this;
	}

	// Getter and Setter for yearlyDueDays
	public List<MonthDay> getYearactivedays() {
			return yearactivedays;
	}

	public TodoFormDto setYearactivedays(List<MonthDay> yearlyDueDays) {
			this.yearactivedays = yearlyDueDays;
			return this;
	}

	// Getter and Setter for monthlyDueDays
	public Integer[] getMonthactivedays() {
			return monthactivedays;
	}

	public TodoFormDto setMonthactivedays(Integer[] monthactivedays) {
			this.monthactivedays = monthactivedays;
			return this;
	}

	// Getter and Setter for monthlySkipMod
	public boolean isRateinversionflag() {
			return rateinversionflag;
	}

	public TodoFormDto setRateinversionflag(boolean monthlySkipMod) {
			this.rateinversionflag = monthlySkipMod;
			return this;
	}

	// Getter and Setter for weekactivedays
	public Collection<String> getWeekactivedays() {
			return weekactivedays;
	}

	public TodoFormDto setWeekactivedays(Collection<String> dueDaysOfWeek) {
			this.weekactivedays = dueDaysOfWeek;
			return this;
	}

	// Getter and Setter for dailyRate
	public short getRepeatrate() {
			return repeatrate;
	}

	public TodoFormDto setRepeatrate(short repeatrate) {
			this.repeatrate = repeatrate;
			return this;
	}

	// Getter and Setter for dailyRate
	public short getRepeatcount() {
			return repeatcount;
	}

	public TodoFormDto setRepeatcount(short repeatcount) {
			this.repeatcount = repeatcount;
			return this;
	}

	// Getter and Setter for poisonous
	public boolean isPoisonous() {
			return poisonous;
	}

	public TodoFormDto setPoisonous(boolean poisonous) {
			this.poisonous = poisonous;
			return this;
	}

	// Getter and Setter for danger
	public short getRisk() {
			return risk;
	}

	public TodoFormDto setRisk(short risk) {
			this.risk = risk;
			return this;
	}

	// Getter and Setter for activeFromDate
	public ZonedDateTime getEffectivedatetimestamp() {
			return effectivedatetimestamp;
	}

	public TodoFormDto setEffectivedatetimestamp(ZonedDateTime activeFromDate) {
			this.effectivedatetimestamp = activeFromDate;
			return this;
	}

	// Getter and Setter for activeToDate
	public ZonedDateTime getExpirationdatetimestamp() {
			return expirationdatetimestamp;
	}

	public TodoFormDto setExpirationdatetimestamp(ZonedDateTime activeToDate) {
			this.expirationdatetimestamp = activeToDate;
			return this;
	}

	public String getWeekActivedaysByteString() {
		return TodoActiveDaysConverters
			.getWeekActiveDaysString(this.weekactivedays);
	}

	public Integer[] getYearActivedaysIntegerArray() {
		return TodoActiveDaysConverters
			.getYearActivedaysIntegerArray(yearactivedays);
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
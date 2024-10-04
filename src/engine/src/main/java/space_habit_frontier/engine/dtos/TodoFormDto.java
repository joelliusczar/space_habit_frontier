package space_habit_frontier.engine.dtos;

import java.util.List;

public class TodoFormDto extends NamedId {

	private String note;
	private int rateType;
	private String dueDate;
	private List<MonthDay> yearlyDueDays;
	private boolean yearlySkipMod;
	private List<Integer> monthlyDueDays;
	private boolean monthlySkipMod;
	private List<String> dueDaysOfWeek;
	private int dailyRate;
	private boolean poisonous;
	private int danger;
	private String activeFromDate;
	private String activeToDate;

	public TodoFormDto() {
		super(0, "");
	}

	public TodoFormDto(
		long id,
		String name,
		String note,
		int rateType,
		String dueDate,
		List<MonthDay> yearlyDueDays,
		boolean yearlySkipMod,
		List<Integer> monthlyDueDays,
		boolean monthlySkipMod,
		List<String> dueDaysOfWeek,
		int dailyRate,
		boolean poisonous,
		int danger,
		String activeFromDate,
		String activeToDate
	) {
		super(id, name);
		this.note = note;
		this.rateType = rateType;
		this.dueDate = dueDate;
		this.yearlyDueDays = yearlyDueDays;
		this.yearlySkipMod = yearlySkipMod;
		this.monthlyDueDays = monthlyDueDays;
		this.monthlySkipMod = monthlySkipMod;
		this.dueDaysOfWeek = dueDaysOfWeek;
		this.dailyRate = dailyRate;
		this.poisonous = poisonous;
		this.danger = danger;
		this.activeFromDate = activeFromDate;
		this.activeToDate = activeToDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// Getter and Setter for rateType
	public int getRateType() {
		return rateType;
	}

	public void setRateType(int rateType) {
			this.rateType = rateType;
	}

	// Getter and Setter for dueDate
	public String getDueDate() {
			return dueDate;
	}

	public void setDueDate(String dueDate) {
			this.dueDate = dueDate;
	}

	// Getter and Setter for yearlyDueDays
	public List<MonthDay> getYearlyDueDays() {
			return yearlyDueDays;
	}

	public void setYearlyDueDays(List<MonthDay> yearlyDueDays) {
			this.yearlyDueDays = yearlyDueDays;
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

	// Getter and Setter for dueDaysOfWeek
	public List<String> getDueDaysOfWeek() {
			return dueDaysOfWeek;
	}

	public void setDueDaysOfWeek(List<String> dueDaysOfWeek) {
			this.dueDaysOfWeek = dueDaysOfWeek;
	}

	// Getter and Setter for dailyRate
	public int getDailyRate() {
			return dailyRate;
	}

	public void setDailyRate(int dailyRate) {
			this.dailyRate = dailyRate;
	}

	// Getter and Setter for poisonous
	public boolean isPoisonous() {
			return poisonous;
	}

	public void setPoisonous(boolean poisonous) {
			this.poisonous = poisonous;
	}

	// Getter and Setter for danger
	public int getDanger() {
			return danger;
	}

	public void setDanger(int danger) {
			this.danger = danger;
	}

	// Getter and Setter for activeFromDate
	public String getActiveFromDate() {
			return activeFromDate;
	}

	public void setActiveFromDate(String activeFromDate) {
			this.activeFromDate = activeFromDate;
	}

	// Getter and Setter for activeToDate
	public String getActiveToDate() {
			return activeToDate;
	}

	public void setActiveToDate(String activeToDate) {
			this.activeToDate = activeToDate;
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
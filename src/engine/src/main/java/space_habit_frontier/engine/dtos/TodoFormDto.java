package space_habit_frontier.engine.dtos;

public class TodoFormDto extends NamedId {

	private String note;

	public TodoFormDto() {
		super(0, "");
	}

	public TodoFormDto(
		long id,
		String name,
		String note
	) {
		super(id, name);
		this.note = note;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String value) {
		this.note = value;
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
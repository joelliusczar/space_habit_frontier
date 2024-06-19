namespace SpaceHabitFrontier.Dtos;

public class LookupsDto {
	public IEnumerable<NamedIdDto> CycleRateTypes { get; set; } 
		= new List<NamedIdDto>();
}
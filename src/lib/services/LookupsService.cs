using SpaceHabitFrontier.Dtos;
using SpaceHabitFrontier.Extensions;
using static SpaceHabitFrontier.Top.Constants;
namespace SpaceHabitFrontier.Services;

public class LookupsService
{

	public IEnumerable<NamedIdDto> GetCycleRateTypes()
	{
		return EnumExtensions.ToEnumerable<CycleRateTypes>();
	}

	public LookupsDto GetLookups() 
	{
		var dto =  new LookupsDto();
		dto.CycleRateTypes = GetCycleRateTypes();

		return dto;
	}
}
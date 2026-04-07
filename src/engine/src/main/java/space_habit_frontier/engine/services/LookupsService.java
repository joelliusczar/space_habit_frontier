package space_habit_frontier.engine.services;

import space_habit_frontier.engine.dtos.NamedIntId;
import space_habit_frontier.engine.dtos.Lookups;
import space_habit_frontier.engine.constants.CycleRateType;
import space_habit_frontier.engine.utilities.SHFEnumUtils;

import java.util.List;

public class LookupsService {

	public List<NamedIntId> getCycleRateTypes() {
		return SHFEnumUtils.getNamedIds(CycleRateType.class);
	}

	public Lookups getLookups() {
		var lookups = new Lookups(
			getCycleRateTypes()
		);

		return lookups;
	}

}
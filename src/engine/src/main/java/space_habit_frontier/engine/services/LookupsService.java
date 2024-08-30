package space_habit_frontier.engine.services;

import space_habit_frontier.engine.dtos.NamedId;
import space_habit_frontier.engine.dtos.Lookups;
import space_habit_frontier.engine.constants.CycleRateType;
import space_habit_frontier.engine.utilities.EnumUtils;

import java.util.List;

public class LookupsService {

	public List<NamedId> getCycleRateTypes() {
		return EnumUtils.getNamedIds(CycleRateType.class);
	}

	public Lookups getLookups() {
		var lookups = new Lookups(
			this.getCycleRateTypes()
		);

		return lookups;
	}

}
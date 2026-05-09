package space_habit_frontier.engine.services;

import space_habit_frontier.engine.dtos.NamedIntId;
import space_habit_frontier.engine.dtos.Lookups;
import space_habit_frontier.engine.constants.RepeatType;
import space_habit_frontier.engine.utilities.SHFEnumUtils;

import java.util.List;

public class LookupsService {

	public List<NamedIntId> getRepeatTypes() {
		return SHFEnumUtils.getNamedIds(RepeatType.class);
	}

	public Lookups getLookups() {
		var lookups = new Lookups(
			getRepeatTypes()
		);

		return lookups;
	}

}
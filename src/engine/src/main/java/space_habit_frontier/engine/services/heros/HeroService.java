package space_habit_frontier.engine.services.heros;

import java.sql.SQLException;
import java.util.UUID;

import org.jooq.DSLContext;
import org.jooq.JSON;

import space_habit_frontier.data_model.db_generated.tables.Hero;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;
import space_habit_frontier.engine.interfaces.users.UserProvider;

public class HeroService {

	private final DSLContext __context;
	private final UserProvider __userProvider;
	private final DatetimeProvider __datetimeProvider;

	public HeroService(
			DataContextProvider dataContextProvider,
			UserProvider userProvider,
			DatetimeProvider datetimeProvider) throws SQLException {
		__context = dataContextProvider.getContext();
		__userProvider = userProvider;
		__datetimeProvider = datetimeProvider;
	}

	public void add(UUID userId) {
		__context.transaction(configuration -> {
			var ctx = configuration.dsl();
			ctx.insertInto(Hero.HERO)
				.set(Hero.HERO.ID, userId)
				.set(Hero.HERO.XP, 0L)
				.set(Hero.HERO.GOLD, 0L)
				.set(Hero.HERO.MONSTER, JSON.valueOf("{}"))
				.set(Hero.HERO.DUNGEON, JSON.valueOf("{}"))
				.set(Hero.HERO.CURRENTHP, 100L)
				.execute();
		});
	}
}

package space_habit_frontier.engine.services.todos;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.jooq.DSLContext;

import com.fasterxml.uuid.Generators;

import space_habit_frontier.data_model.db_generated.tables.Todos;
import space_habit_frontier.engine.dtos.todos.TodoActiveDaysConverters;
import space_habit_frontier.engine.dtos.todos.TodoFormDto;
import space_habit_frontier.engine.dtos.todos.TodoListDto;
import space_habit_frontier.engine.interfaces.dates.DatetimeProvider;
import space_habit_frontier.engine.interfaces.db.DataContextProvider;
import space_habit_frontier.engine.interfaces.users.UserProvider;

public class TodoService {
	
	private final DSLContext __context;
	private final UserProvider __userProvider;
	private final DatetimeProvider __datetimeProvider;

	public TodoService(
			DataContextProvider dataContextProvider,
			UserProvider userProvider,
			DatetimeProvider datetimeProvider) throws SQLException {
		__context = dataContextProvider.getContext();
		__userProvider = userProvider;
		__datetimeProvider = datetimeProvider;
	}

	public Optional<TodoFormDto> get(UUID id) {
		return __context.transactionResult(configuration -> {
			var ctx = configuration.dsl();
			return ctx.selectFrom(Todos.TODOS)
				.where(Todos.TODOS.ID.eq(id))
				.fetchOptional(r -> new TodoFormDto(id, r.getTitle())
					.setNote(r.getNote())
					.setRisk(r.getRisk())
					.setDuedatetimestamp(
						r.getDuedatetime() != null 
							? r.getDuedatetime().toZonedDateTime() 
							: null)
					.setEffectivedatetimestamp(
						r.getEffectivedatetime() != null 
							? r.getEffectivedatetime().toZonedDateTime() 
							: null)
					.setRepeatcount(r.getRepeatcount())
					.setRepeattype(r.getRepeattype())
					.setRepeatrate(r.getRepeatrate())
					.setWeekactivedays(
						TodoActiveDaysConverters.
						getWeekActiveDaysNames(r.getWeekactivedays()))
					.setYearactivedays(
						TodoActiveDaysConverters
						.getYearActivedaysMonthDayList(
							Arrays.asList(r.getYearactivedays())))
					.setMonthactivedays(r.getMonthactivedays())
					.setPoisonous(r.getPoisonous())
					.setExpirationdatetimestamp(
						r.getExpirationdatetimestamp() != null 
							? r.getExpirationdatetimestamp().toZonedDateTime() 
							: null)
					.setRateinversionflag(r.getRateinversionflag())
				);
		});
	}

	public TodoListDto add(TodoFormDto formDto) {
		var id = Generators.timeBasedEpochRandomGenerator().generate();
		var timestamp = __datetimeProvider.now().toOffsetDateTime();
		var userId = __userProvider.getSessionUserRequired().getId();
		__context.transaction(configuration -> {
			var ctx = configuration.dsl();
			ctx.insertInto(Todos.TODOS)
				.set(Todos.TODOS.ID, id)
				.set(Todos.TODOS.TITLE, formDto.title())
				.set(Todos.TODOS.STREAKSTARTDATETIME, timestamp)
				.set(Todos.TODOS.CREATIONDATETIME, timestamp)
				.set(Todos.TODOS.USERID, userId)
				.execute();
		});
		return new TodoListDto(id, formDto.title());
	}

	public TodoFormDto update(UUID id, TodoFormDto formDto) {
		__context.transaction(configuration -> {
			var ctx = configuration.dsl();
			ctx.update(Todos.TODOS)
				.set(Todos.TODOS.TITLE, formDto.title())
				.set(Todos.TODOS.NOTE, formDto.getNote())
				.set(Todos.TODOS.RISK, formDto.getRisk())
				.set(
					Todos.TODOS.DUEDATETIME, 
					formDto.getDuedatetimestamp() != null 
						? formDto.getDuedatetimestamp().toOffsetDateTime()
						: null)
				.set(
					Todos.TODOS.EFFECTIVEDATETIME,
					formDto.getEffectivedatetimestamp() != null
						? formDto.getEffectivedatetimestamp().toOffsetDateTime()
						: null)
				.set(Todos.TODOS.REPEATCOUNT, formDto.getRepeatcount())
				.set(Todos.TODOS.REPEATTYPE, formDto.getRepeattype())
				.set(Todos.TODOS.REPEATRATE, formDto.getRepeatrate())
				.set(Todos.TODOS.WEEKACTIVEDAYS, formDto.getWeekActivedaysByteString())
				.set(
					Todos.TODOS.YEARACTIVEDAYS,
					formDto.getYearActivedaysIntegerArray())
				.set(
						Todos.TODOS.MONTHACTIVEDAYS,
						formDto.getMonthactivedays())
				.set(
					Todos.TODOS.POISONOUS,
					formDto.isPoisonous())
				.set(Todos.TODOS.EXPIRATIONDATETIMESTAMP,
					formDto.getExpirationdatetimestamp() != null 
						? formDto.getExpirationdatetimestamp().toOffsetDateTime()
						: null)
				.set(
					Todos.TODOS.RATEINVERSIONFLAG,
					formDto.isRateinversionflag())
				.where(Todos.TODOS.ID.eq(id))
				.execute();
		});
		return formDto; 
	}
	
}

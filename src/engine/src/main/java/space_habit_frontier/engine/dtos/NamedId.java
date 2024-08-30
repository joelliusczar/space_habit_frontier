
package space_habit_frontier.engine.dtos;

public class NamedId {
	private long id;
	private String name = "";

	public NamedId(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long value) {
		this.id = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

}
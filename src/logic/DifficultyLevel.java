package logic;

/**
 *
 * @author c00kiemon5ter
 */
public enum DifficultyLevel {

	EASY("Easy", 2),
	NORMAL("Normal", 3),
	HARD("Hard", 4),
	HEROIC("Heroic", 6);
	private String description;
	private int level;

	private DifficultyLevel(String descr, int level) {
		this.description = descr;
		this.level = level;
	}

	public String description() {
		return description;
	}

	public int level() {
		return level;
	}
}

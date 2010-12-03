package logic;

/**
 * Four difficulty levels.
 * Difficulty specifies the depth that the {@code Searchers} will dive
 * to predict score results and thus select the next move
 * 
 * @author c00kiemon5ter
 */
public enum DifficultyLevel {

	EASY("Easy", 3),
	NORMAL("Normal", 4),
	HARD("Hard", 5),
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

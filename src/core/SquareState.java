package core;

/**
 *
 * @author c00kiemon5ter
 */
public enum SquareState {

	BLACK('x'),
	WHITE('o'),
	PSSBL('.'),
	EMPTY(' ');
	private final char symbol;

	SquareState(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public SquareState opposite() {
		return this == BLACK ? WHITE : BLACK;
	}

	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
}

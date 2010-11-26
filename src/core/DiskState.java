package core;

/**
 *
 * @author c00kiemon5ter
 */
public enum DiskState {

	BLACK('x'),
	WHITE('o'),
	PSSBL('.'),
	EMPTY(' ');
	private final char symbol;

	DiskState(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return this.symbol;
	}

	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
}

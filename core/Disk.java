/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package core;

import java.awt.Point;
import java.awt.Color;

public class Disk {

	/** Colors:
	 * true for white
	 * false for black
	 */
	private Color color;
	private Point position;

	public Disk(Point position, boolean color) {
		this.position = new Point(position);
		this.color = color ? Color.WHITE : Color.BLACK;
	}

	public Point getPosition() {
		return this.position;
	}

	public void setPosition(Point position) {
		this.position.setLocation(position);
	}

	public Color getColorValue() {
		return this.color;
	}

	public boolean getColor() {
		return this.color == Color.WHITE;
	}

	protected void setColorValue(Color color) {
		this.color = color;
	}

	public void setColor(boolean color) {
		this.color = color ? Color.WHITE : Color.BLACK;
	}

	public void flip() {
		this.changeColor();
	}

	protected void changeColor() {
		this.setColor(!this.getColor());
		// this.color = !getColor() ? Color.WHITE : Color.BLACK;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj != null && obj instanceof Disk) {
			Disk that = (Disk) obj;
			return this.getColor() == that.getColor()
				&& this.position.equals(that.getPosition());
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 41 * hash + (this.color != null ? this.color.hashCode() : 0);
		hash = 41 * hash + (this.position != null ? this.position.hashCode() : 0);
		return hash;
	}
}

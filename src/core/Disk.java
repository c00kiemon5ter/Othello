/*
 * Creative Commons Attribution Non-Commercial Share Alike
 */
package core;

import java.awt.Point;

public class Disk {

	private Point position;
	private DiskState state;

	public Disk(Point position, DiskState color) {
		this.position = new Point(position);
		this.state = color;
	}

	public Point getPosition() {
		return this.position;
	}

	public void setPosition(Point position) {
		this.position.setLocation(position);
	}

	public DiskState getState() {
		return state;
	}

	public void setState(DiskState state) {
		this.state = state;
	}

	public void flip() {
		this.changeColor();
	}

	protected void changeColor() {
		this.state = this.isBlack() ? DiskState.WHITE : DiskState.BLACK;
	}

	private boolean isBlack() {
		return this.state == DiskState.BLACK;
	}

	public boolean isCorner() {
		int edge = Board.BOARD_SIZE - 1;
		return (this.position.x == 0 && this.position.y == 0)
		       || (this.position.x == 0 && this.position.y == edge)
		       || (this.position.x == edge && this.position.y == 0)
		       || (this.position.x == edge && this.position.y == edge);
	}
}

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

	public DiskState getColor() {
		return state;
	}

	public void setColor(DiskState color) {
		this.state = color;
	}

	public DiskState getState() {
		return state;
	}

	public void setState(DiskState state) {
		this.state = state;
	}

	public void changeColor() {
		this.state = this.state == DiskState.BLACK ? DiskState.WHITE : DiskState.BLACK;
	}

	public boolean isCorner() {
		int rowEdge = Board.BOARD_LENGTH - 1;
		int colmnEdge = Board.BOARD_WIDTH - 1;
		return (this.position.x == 0 && this.position.y == 0)
		       || (this.position.x == 0 && this.position.y == colmnEdge)
		       || (this.position.x == rowEdge && this.position.y == 0)
		       || (this.position.x == rowEdge && this.position.y == colmnEdge);
	}
}

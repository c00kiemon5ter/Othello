package core;

import java.awt.Point;

public class Disk {

	private Point point;
	private DiskState state;

	public Disk(Point point, DiskState color) {
		this.point = new Point(point);
		this.state = color;
	}

	public Disk(Disk disk) {
		this.point = new Point(disk.getPoint());
		this.state = disk.getState();
	}

	public Point getPoint() {
		return this.point;
	}

	public void setPoint(Point position) {
		this.point.setLocation(position);
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

	public boolean isCorner() {
		int rowEdge = Board.BOARD_LENGTH - 1;
		int colmnEdge = Board.BOARD_WIDTH - 1;
		return (this.point.x == 0 && this.point.y == 0)
		       || (this.point.x == 0 && this.point.y == colmnEdge)
		       || (this.point.x == rowEdge && this.point.y == 0)
		       || (this.point.x == rowEdge && this.point.y == colmnEdge);
	}
}

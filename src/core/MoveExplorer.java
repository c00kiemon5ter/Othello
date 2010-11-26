package core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public enum MoveExplorer {

	BLACKEXPLORER(DiskState.BLACK),
	WHITEEXPLORER(DiskState.WHITE);
	private Board board = Board.getInstance();
	private DiskState color;

	MoveExplorer(DiskState color) {
		this.color = color;
	}

	public Set<Point> explore() {
		Set<Point> possibleMoves = new HashSet<Point>();
		Set<Point> diskpoints = board.getDiskPoints(color);
		for (Point point : diskpoints) {
			possibleMoves.addAll(searchMoves(point));
		}
		return possibleMoves;
	}

	private List<Point> searchMoves(Point seed) {
		List<Point> pointlist = new LinkedList<Point>();
		for (Direction direction : Direction.values()) {
			if (shouldSearch(seed, direction)) {
				switch (direction) {
					case NORTH:
						pointlist.addAll(searchNorth(seed));
						break;
					case SOUTH:
						pointlist.addAll(searchSouth(seed));
						break;
					case WEST:
						pointlist.addAll(searchWest(seed));
						break;
					case EAST:
						pointlist.addAll(searchEast(seed));
						break;
					case NORTHWEST:
						pointlist.addAll(searchNorthWest(seed));
						break;
					case SOUTHEAST:
						pointlist.addAll(searchSouthEast(seed));
						break;
					case SOUTHWEST:
						pointlist.addAll(searchSouthWest(seed));
						break;
					case NORTHEAST:
						pointlist.addAll(searchNorthEast(seed));
						break;
				}
			}
		}
		return pointlist;
	}

	private boolean shouldSearch(Point seed, Direction direction) {
		Disk nextPoint;
		switch (direction) {
			case NORTH:
				nextPoint = board.getDisk(new Point(seed.x - 1, seed.y));
				return nextPoint.getState() != DiskState.EMPTY
				       && nextPoint.getColor() != color;
			case SOUTH:
				nextPoint = board.getDisk(new Point(seed.x + 1, seed.y));
				return nextPoint.getState() != DiskState.EMPTY
				       && nextPoint.getColor() != color;
			case WEST:
				nextPoint = board.getDisk(new Point(seed.x, seed.y - 1));
				return nextPoint.getState() != DiskState.EMPTY
				       && nextPoint.getColor() != color;
			case EAST:
				nextPoint = board.getDisk(new Point(seed.x, seed.y + 1));
				return nextPoint.getState() != DiskState.EMPTY
				       && nextPoint.getColor() != color;
			case NORTHWEST:
				nextPoint = board.getDisk(new Point(seed.x - 1, seed.y - 1));
				return nextPoint.getState() != DiskState.EMPTY
				       && nextPoint.getColor() != color;
			case SOUTHEAST:
				nextPoint = board.getDisk(new Point(seed.x + 1, seed.y + 1));
				return nextPoint.getState() != DiskState.EMPTY
				       && nextPoint.getColor() != color;
			case SOUTHWEST:
				nextPoint = board.getDisk(new Point(seed.x + 1, seed.y - 1));
				return nextPoint.getState() != DiskState.EMPTY
				       && nextPoint.getColor() != color;
			case NORTHEAST:
				nextPoint = board.getDisk(new Point(seed.x - 1, seed.y + 1));
				return nextPoint.getState() != DiskState.EMPTY
				       && nextPoint.getColor() != color;
			default:
				return false;
		}
	}

	private List<Point> searchNorth(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x - 2, seed.y);
		while (nextPoint.x >= 0) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			}
			if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y);
		}
		return list;
	}

	private List<Point> searchSouth(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x + 2, seed.y);
		while (nextPoint.x < Board.BOARD_LENGTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			}
			if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y);
		}
		return list;
	}

	private List<Point> searchWest(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x, seed.y - 2);
		while (nextPoint.x >= 0) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			}
			if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y - 1);
		}
		return list;
	}

	private List<Point> searchEast(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x, seed.y + 2);
		while (nextPoint.x < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			}
			if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y + 1);
		}
		return list;
	}

	private List<Point> searchNorthWest(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x - 2, seed.y - 2);
		while (nextPoint.x < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			}
			if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y - 1);
		}
		return list;
	}

	private List<Point> searchSouthEast(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x + 2, seed.y + 2);
		while (nextPoint.x < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			}
			if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y + 1);
		}
		return list;
	}

	private List<Point> searchSouthWest(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x + 2, seed.y - 2);
		while (nextPoint.x < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			}
			if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y - 1);
		}
		return list;
	}

	private List<Point> searchNorthEast(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x - 2, seed.y + 2);
		while (nextPoint.x < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			}
			if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y + 1);
		}
		return list;
	}
}

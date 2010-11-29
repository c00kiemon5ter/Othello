package logic;

import core.Board;
import core.Disk;
import core.DiskState;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MoveExplorer {

	private Board board;

	MoveExplorer(final Board board) {
		this.board = board;
	}

	/**
	 *
	 * @param board - the board to search in
	 * @param seed - where we start searching from
	 * @param direction - which direction should we search
	 * @return if search in this direction is likely to have interesting findings
	 */
	private boolean shouldSearch(final Point seed, final Direction direction) {
		DiskState color = board.getDisk(seed).getColor();
		Point nextPoint;
		Disk nextDisk;
		switch (direction) {
			case NORTH:
				nextPoint = new Point(seed.x - 1, seed.y);
				if (nextPoint.x < 0) {
					return false;
				}
				nextDisk = board.getDisk(nextPoint);
				return nextDisk.getState() != DiskState.EMPTY
				       && nextDisk.getColor() != color;
			case SOUTH:
				nextPoint = new Point(seed.x + 1, seed.y);
				if (nextPoint.x >= Board.BOARD_LENGTH) {
					return false;
				}
				nextDisk = board.getDisk(nextPoint);
				return nextDisk.getState() != DiskState.EMPTY
				       && nextDisk.getColor() != color;
			case WEST:
				nextPoint = new Point(seed.x, seed.y - 1);
				if (nextPoint.y < 0) {
					return false;
				}
				nextDisk = board.getDisk(nextPoint);
				return nextDisk.getState() != DiskState.EMPTY
				       && nextDisk.getColor() != color;
			case EAST:
				nextPoint = new Point(seed.x, seed.y + 1);
				if (nextPoint.y >= Board.BOARD_WIDTH) {
					return false;
				}
				nextDisk = board.getDisk(nextPoint);
				return nextDisk.getState() != DiskState.EMPTY
				       && nextDisk.getColor() != color;
			case NORTHWEST:
				nextPoint = new Point(seed.x - 1, seed.y - 1);
				if (nextPoint.x < 0 || nextPoint.y < 0) {
					return false;
				}
				nextDisk = board.getDisk(nextPoint);
				return nextDisk.getState() != DiskState.EMPTY
				       && nextDisk.getColor() != color;
			case SOUTHEAST:
				nextPoint = new Point(seed.x + 1, seed.y + 1);
				if (nextPoint.x >= Board.BOARD_LENGTH || nextPoint.y >= Board.BOARD_WIDTH) {
					return false;
				}
				nextDisk = board.getDisk(nextPoint);
				return nextDisk.getState() != DiskState.EMPTY
				       && nextDisk.getColor() != color;
			case SOUTHWEST:
				nextPoint = new Point(seed.x + 1, seed.y - 1);
				if (nextPoint.x >= Board.BOARD_LENGTH || nextPoint.y < 0) {
					return false;
				}
				nextDisk = board.getDisk(nextPoint);
				return nextDisk.getState() != DiskState.EMPTY
				       && nextDisk.getColor() != color;
			case NORTHEAST:
				nextPoint = new Point(seed.x - 1, seed.y + 1);
				if (nextPoint.x < 0 || nextPoint.y >= Board.BOARD_WIDTH) {
					return false;
				}
				nextDisk = board.getDisk(nextPoint);
				return nextDisk.getState() != DiskState.EMPTY
				       && nextDisk.getColor() != color;
			default:
				return false;
		}
	}

	public Set<Point> explore(final DiskState color) {
		Set<Point> possibleMoves = new HashSet<Point>();
		Set<Point> diskpoints = board.getDiskPoints(color);
		for (Point seed : diskpoints) {
			for (Direction direction : Direction.values()) {
				if (shouldSearch(seed, direction)) {
					switch (direction) {
						case NORTH:
							possibleMoves.addAll(searchNorth(seed));
							break;
						case SOUTH:
							possibleMoves.addAll(searchSouth(seed));
							break;
						case WEST:
							possibleMoves.addAll(searchWest(seed));
							break;
						case EAST:
							possibleMoves.addAll(searchEast(seed));
							break;
						case NORTHWEST:
							possibleMoves.addAll(searchNorthWest(seed));
							break;
						case SOUTHEAST:
							possibleMoves.addAll(searchSouthEast(seed));
							break;
						case SOUTHWEST:
							possibleMoves.addAll(searchSouthWest(seed));
							break;
						case NORTHEAST:
							possibleMoves.addAll(searchNorthEast(seed));
							break;
					}
				}
			}
		}
		return possibleMoves;
	}

	private Collection<Point> searchNorth(final Point seed) {
		DiskState color = board.getDisk(seed).getColor();
		Collection<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x - 2, seed.y);
		while (nextPoint.x >= 0) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y);
		}
		return list;
	}

	private Collection<Point> searchSouth(final Point seed) {
		DiskState color = board.getDisk(seed).getColor();
		Collection<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x + 2, seed.y);
		while (nextPoint.x < Board.BOARD_LENGTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y);
		}
		return list;
	}

	private Collection<Point> searchWest(final Point seed) {
		DiskState color = board.getDisk(seed).getColor();
		Collection<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x, seed.y - 2);
		while (nextPoint.y >= 0) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y - 1);
		}
		return list;
	}

	private Collection<Point> searchEast(final Point seed) {
		DiskState color = board.getDisk(seed).getColor();
		Collection<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x, seed.y + 2);
		while (nextPoint.y < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y + 1);
		}
		return list;
	}

	private Collection<Point> searchNorthWest(final Point seed) {
		DiskState color = board.getDisk(seed).getColor();
		Collection<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x - 2, seed.y - 2);
		while (nextPoint.x >= 0 && nextPoint.y >= 0) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y - 1);
		}
		return list;
	}

	private Collection<Point> searchSouthEast(final Point seed) {
		DiskState color = board.getDisk(seed).getColor();
		Collection<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x + 2, seed.y + 2);
		while (nextPoint.x < Board.BOARD_LENGTH && nextPoint.y < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y + 1);
		}
		return list;
	}

	private Collection<Point> searchSouthWest(final Point seed) {
		DiskState color = board.getDisk(seed).getColor();
		Collection<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x + 2, seed.y - 2);
		while (nextPoint.x < Board.BOARD_LENGTH && nextPoint.y >= 0) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y - 1);
		}
		return list;
	}

	private Collection<Point> searchNorthEast(final Point seed) {
		DiskState color = board.getDisk(seed).getColor();
		Collection<Point> list = new ArrayList<Point>(1);
		Point nextPoint = new Point(seed.x - 2, seed.y + 2);
		while (nextPoint.x >= 0 && nextPoint.y < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color) {
				break;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				list.add(nextPoint);
				break;
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y + 1);
		}
		return list;
	}

	public Set<Point> pointsToFill(final Point seed) {
		Set<Point> filledlist = new HashSet<Point>();
		for (Direction direction : Direction.values()) {
			if (shouldSearch(seed, direction)) {
				switch (direction) {
					case NORTH:
						filledlist.addAll(fillNorth(seed));
						break;
					case SOUTH:
						filledlist.addAll(fillSouth(seed));
						break;
					case WEST:
						filledlist.addAll(fillWest(seed));
						break;
					case EAST:
						filledlist.addAll(fillEast(seed));
						break;
					case NORTHWEST:
						filledlist.addAll(fillNorthWest(seed));
						break;
					case SOUTHEAST:
						filledlist.addAll(fillSouthEast(seed));
						break;
					case SOUTHWEST:
						filledlist.addAll(fillSouthWest(seed));
						break;
					case NORTHEAST:
						filledlist.addAll(fillNorthEast(seed));
						break;
				}
			}
		}
		return filledlist;
	}

	private Collection<Point> fillNorth(final Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
		DiskState color = board.getDisk(seed).getColor();
		Point nextPoint = new Point(seed.x - 1, seed.y);
		while (nextPoint.x >= 0) {
			if (board.getDisk(nextPoint).getColor() == color.opposite()) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillSouth(final Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
		DiskState color = board.getDisk(seed).getColor();
		Point nextPoint = new Point(seed.x + 1, seed.y);
		while (nextPoint.x < Board.BOARD_LENGTH) {
			if (board.getDisk(nextPoint).getColor() == color.opposite()) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillWest(final Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
		DiskState color = board.getDisk(seed).getColor();
		Point nextPoint = new Point(seed.x, seed.y - 1);
		while (nextPoint.y >= 0) {
			if (board.getDisk(nextPoint).getColor() == color.opposite()) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y - 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillEast(final Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
		DiskState color = board.getDisk(seed).getColor();
		Point nextPoint = new Point(seed.x, seed.y + 1);
		while (nextPoint.y < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color.opposite()) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y + 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillNorthWest(final Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
		DiskState color = board.getDisk(seed).getColor();
		Point nextPoint = new Point(seed.x - 1, seed.y - 1);
		while (nextPoint.x >= 0 && nextPoint.y >= 0) {
			if (board.getDisk(nextPoint).getColor() == color.opposite()) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y - 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillSouthEast(final Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
		DiskState color = board.getDisk(seed).getColor();
		Point nextPoint = new Point(seed.x + 1, seed.y + 1);
		while (nextPoint.x < Board.BOARD_LENGTH && nextPoint.y < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color.opposite()) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y + 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillSouthWest(final Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
		DiskState color = board.getDisk(seed).getColor();
		Point nextPoint = new Point(seed.x + 1, seed.y - 1);
		while (nextPoint.x < Board.BOARD_LENGTH && nextPoint.y >= 0) {
			if (board.getDisk(nextPoint).getColor() == color.opposite()) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y - 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillNorthEast(final Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
		DiskState color = board.getDisk(seed).getColor();
		Point nextPoint = new Point(seed.x - 1, seed.y + 1);
		while (nextPoint.x >= 0 && nextPoint.y < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == color.opposite()) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y + 1);
		}
		return Collections.emptyList();
	}
}

package core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
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

	public Collection<Point> explore() {
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

	private List<Point> searchNorth(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
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

	private List<Point> searchSouth(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
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

	private List<Point> searchWest(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
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

	private List<Point> searchEast(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
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

	private List<Point> searchNorthWest(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
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

	private List<Point> searchSouthEast(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
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

	private List<Point> searchSouthWest(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
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

	private List<Point> searchNorthEast(Point seed) {
		List<Point> list = new ArrayList<Point>(1);
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

	public void fill(Point seed) {
		for (Direction direction : Direction.values()) {
			if (shouldSearch(seed, direction)) {
				switch (direction) {
					case NORTH:
						fillNorth(seed);
						break;
					case SOUTH:
						fillSouth(seed);
						break;
					case WEST:
						fillWest(seed);
						break;
					case EAST:
						fillEast(seed);
						break;
					case NORTHWEST:
						fillNorthWest(seed);
						break;
					case SOUTHEAST:
						fillSouthEast(seed);
						break;
					case SOUTHWEST:
						fillSouthWest(seed);
						break;
					case NORTHEAST:
						fillNorthEast(seed);
						break;
				}
			}
		}
	}

	private void fillNorth(Point seed) {
		List<Point> pointlist = new LinkedList<Point>();
		DiskState oppositeColor = (color == DiskState.BLACK)
					  ? DiskState.WHITE : DiskState.BLACK;
		Point nextPoint = new Point(seed.x - 1, seed.y);
		while (nextPoint.x >= 0) {
			if (board.getDisk(nextPoint).getColor() == oppositeColor) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				for (Point toChange : pointlist) {
					board.getDisk(toChange).changeColor();
				}
				return;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				break;
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y);
		}
	}

	private void fillSouth(Point seed) {
		List<Point> pointlist = new LinkedList<Point>();
		DiskState oppositeColor = (color == DiskState.BLACK)
					  ? DiskState.WHITE : DiskState.BLACK;
		Point nextPoint = new Point(seed.x + 1, seed.y);
		while (nextPoint.x < Board.BOARD_LENGTH) {
			if (board.getDisk(nextPoint).getColor() == oppositeColor) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				for (Point toChange : pointlist) {
					board.getDisk(toChange).changeColor();
				}
				return;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				break;
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y);
		}
	}

	private void fillWest(Point seed) {
		List<Point> pointlist = new LinkedList<Point>();
		DiskState oppositeColor = (color == DiskState.BLACK)
					  ? DiskState.WHITE : DiskState.BLACK;
		Point nextPoint = new Point(seed.x, seed.y - 1);
		while (nextPoint.y >= 0) {
			if (board.getDisk(nextPoint).getColor() == oppositeColor) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				for (Point toChange : pointlist) {
					board.getDisk(toChange).changeColor();
				}
				return;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				break;
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y - 1);
		}
	}

	private void fillEast(Point seed) {
		List<Point> pointlist = new LinkedList<Point>();
		DiskState oppositeColor = (color == DiskState.BLACK)
					  ? DiskState.WHITE : DiskState.BLACK;
		Point nextPoint = new Point(seed.x, seed.y + 1);
		while (nextPoint.y < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == oppositeColor) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				for (Point toChange : pointlist) {
					board.getDisk(toChange).changeColor();
				}
				return;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				break;
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y + 1);
		}
	}

	private void fillNorthWest(Point seed) {
		List<Point> pointlist = new LinkedList<Point>();
		DiskState oppositeColor = (color == DiskState.BLACK)
					  ? DiskState.WHITE : DiskState.BLACK;
		Point nextPoint = new Point(seed.x - 1, seed.y - 1);
		while (nextPoint.x >= 0 && nextPoint.y >= 0) {
			if (board.getDisk(nextPoint).getColor() == oppositeColor) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				for (Point toChange : pointlist) {
					board.getDisk(toChange).changeColor();
				}
				return;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				break;
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y - 1);
		}
	}

	private void fillSouthEast(Point seed) {
		List<Point> pointlist = new LinkedList<Point>();
		DiskState oppositeColor = (color == DiskState.BLACK)
					  ? DiskState.WHITE : DiskState.BLACK;
		Point nextPoint = new Point(seed.x + 1, seed.y + 1);
		while (nextPoint.x < Board.BOARD_LENGTH && nextPoint.y < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == oppositeColor) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				for (Point toChange : pointlist) {
					board.getDisk(toChange).changeColor();
				}
				return;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				break;
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y + 1);
		}
	}

	private void fillSouthWest(Point seed) {
		List<Point> pointlist = new LinkedList<Point>();
		DiskState oppositeColor = (color == DiskState.BLACK)
					  ? DiskState.WHITE : DiskState.BLACK;
		Point nextPoint = new Point(seed.x + 1, seed.y - 1);
		while (nextPoint.x < Board.BOARD_LENGTH && nextPoint.y >= 0) {
			if (board.getDisk(nextPoint).getColor() == oppositeColor) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				for (Point toChange : pointlist) {
					board.getDisk(toChange).changeColor();
				}
				return;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				break;
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y - 1);
		}
	}

	private void fillNorthEast(Point seed) {
		List<Point> pointlist = new LinkedList<Point>();
		DiskState oppositeColor = (color == DiskState.BLACK)
					  ? DiskState.WHITE : DiskState.BLACK;
		Point nextPoint = new Point(seed.x - 1, seed.y + 1);
		while (nextPoint.x >= 0 && nextPoint.y < Board.BOARD_WIDTH) {
			if (board.getDisk(nextPoint).getColor() == oppositeColor) {
				pointlist.add(nextPoint);
			} else if (board.getDisk(nextPoint).getColor() == color) {
				for (Point toChange : pointlist) {
					board.getDisk(toChange).changeColor();
				}
				return;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				break;
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y + 1);
		}
	}
}

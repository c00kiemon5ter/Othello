package logic;

import core.Board;
import core.Direction;
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

	private Board board;;
	private DiskState color;

	MoveExplorer(Board board, DiskState color) {
		this.board = board;
		this.color = color;
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

	public Set<Point> explore() {
		Set<Point> possibleMoves = new HashSet<Point>();
		Set<Point> diskpoints = board.getDiskPoints(color);
		for (Point point : diskpoints) {
			possibleMoves.addAll(searchMoves(point));
		}
		return possibleMoves;
	}

	private Collection<Point> searchMoves(Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
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

	private Collection<Point> searchNorth(Point seed) {
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

	private Collection<Point> searchSouth(Point seed) {
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

	private Collection<Point> searchWest(Point seed) {
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

	private Collection<Point> searchEast(Point seed) {
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

	private Collection<Point> searchNorthWest(Point seed) {
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

	private Collection<Point> searchSouthEast(Point seed) {
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

	private Collection<Point> searchSouthWest(Point seed) {
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

	private Collection<Point> searchNorthEast(Point seed) {
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

	public Set<Point> fill(Point seed) {
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

	private Collection<Point> fillNorth(Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
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
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillSouth(Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
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
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillWest(Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
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
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y - 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillEast(Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
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
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x, nextPoint.y + 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillNorthWest(Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
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
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y - 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillSouthEast(Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
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
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y + 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillSouthWest(Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
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
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x + 1, nextPoint.y - 1);
		}
		return Collections.emptyList();
	}

	private Collection<Point> fillNorthEast(Point seed) {
		Collection<Point> pointlist = new LinkedList<Point>();
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
				return pointlist;
			} else if (board.getDisk(nextPoint).getState() == DiskState.EMPTY) {
				return Collections.emptyList();
			}
			nextPoint = new Point(nextPoint.x - 1, nextPoint.y + 1);
		}
		return Collections.emptyList();
	}
}

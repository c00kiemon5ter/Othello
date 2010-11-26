package core;

import java.awt.Point;
import java.util.HashSet;
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
		Set<Point> availablemoves = new HashSet<Point>();
		Set<Point> diskpoints = board.getDiskPoints(color);
		for (Point point : diskpoints) {
			for (Direction direction : Direction.values()) {
				availablemoves.addAll(searchMoves(point, direction));
			}
		}
		return availablemoves;
	}

	private Set<Point> searchMoves(Point seed, Direction direction) {
		Set<Point> points = new HashSet<Point>();
		// TODO: search this direction for available moves
                switch(direction) {
                    case NORTH:if(hasNorth(seed)) points.add(searchNorth(seed));break;
                    case SOUTH:if(hasSouth(seed)) points.add(searchSouth(seed));break;
                    case WEST:if(hasWest(seed)) points.add(searchWest(seed));break;
                    case EAST:if(hasEast(seed)) points.add(searchEast(seed));break;
                    case NORTHWEST:if(hasNorthWest(seed)) points.add(searchNorthWest(seed));break;
                    case SOUTHEAST:if(hasSouthEast(seed)) points.add(searchSouthEast(seed));break;
                    case SOUTHWEST:if(hasSouthWest(seed)) points.add(searchSouthWest(seed));break;
                    case NORTHEAST:if(hasNorthEast(seed)) points.add(searchNorthEast(seed));break;
                }
		return points;
	}

        private boolean hasNorth(Point seed) {
            return (board.getDisk(new Point(seed.x,seed.y-1)).getState() == board.getDisk(seed).getState());
        }

        private Point searchNorth(Point seed) {
            Point point = new Point();
            int current = seed.y-1;
            while(current>=0) {
                point.setLocation(seed.x,current );
                if(board.getDisk(point).getState() == DiskState.EMPTY)
                    break;
                if(current==0)
                    return null;
                current--;
            }
            return point;
        }
        
        private boolean hasSouth(Point seed) {
            return (board.getDisk(new Point(seed.x,seed.y+1)).getState() == board.getDisk(seed).getState());
        }

        private Point searchSouth(Point seed) {
            Point point = new Point();
            int current = seed.y+1;
            while(current<Board.BOARD_SIZE) {
                point.setLocation(seed.x,current );
                if(board.getDisk(point).getState() == DiskState.EMPTY)
                    break;
                if(current==Board.BOARD_SIZE-1)
                    return null;
                current++;
            }
            return point;
        }

        private boolean hasWest(Point seed) {
            return (board.getDisk(new Point(seed.x-1,seed.y)).getState() == board.getDisk(seed).getState());
        }

        private Point searchWest(Point seed) {
            Point point = new Point();
            int current = seed.x-1;
            while(current>=0) {
                point.setLocation(current, seed.y);
                if(board.getDisk(point).getState() == DiskState.EMPTY)
                    break;
                if(current==0)
                    return null;
                current--;
            }
            return point;
        }
        
        private boolean hasEast(Point seed) {
            return (board.getDisk(new Point(seed.x+1,seed.y)).getState() == board.getDisk(seed).getState());
        }

        private Point searchEast(Point seed) {
            Point point = new Point();
            int current = seed.x+1;
            while(current<Board.BOARD_SIZE) {
                point.setLocation(current, seed.y);
                if(board.getDisk(point).getState() == DiskState.EMPTY)
                    break;
                if(current==Board.BOARD_SIZE-1)
                    return null;
                current++;
            }
            return point;
        }

        private boolean hasNorthWest(Point seed) {
            return (board.getDisk(new Point(seed.x-1,seed.y-1)).getState() == board.getDisk(seed).getState());
        }

        private Point searchNorthWest(Point seed) {
            Point point = new Point();
            int currentx = seed.x-1;
            int currenty = seed.y-1;
            while(currentx>=0 && currenty>=0) {
                point.setLocation(currentx,currenty );
                if(board.getDisk(point).getState() == DiskState.EMPTY)
                    break;
                if(currentx==0 || currenty==0)
                    return null;
                currentx--;
                currenty--;
            }
            return point;
        }

        private boolean hasSouthEast(Point seed) {
            return (board.getDisk(new Point(seed.x+1,seed.y+1)).getState() == board.getDisk(seed).getState());
        }

        private Point searchSouthEast(Point seed) {
            Point point = new Point();
            int currentx = seed.x+1;
            int currenty = seed.y+1;
            while(currentx<Board.BOARD_SIZE && currenty<Board.BOARD_SIZE) {
                point.setLocation(currentx,currenty );
                if(board.getDisk(point).getState() == DiskState.EMPTY)
                    break;
                if(currentx==Board.BOARD_SIZE-1 || currenty==Board.BOARD_SIZE-1)
                    return null;
                currentx++;
                currenty++;
            }
            return point;
        }

        private boolean hasSouthWest(Point seed) {
            return (board.getDisk(new Point(seed.x-1,seed.y+1)).getState() == board.getDisk(seed).getState());
        }

        private Point searchSouthWest(Point seed) {
            Point point = new Point();
            int currentx = seed.x-1;
            int currenty = seed.y+1;
            while(currentx>=0 && currenty<Board.BOARD_SIZE) {
                point.setLocation(currentx,currenty );
                if(board.getDisk(point).getState() == DiskState.EMPTY)
                    break;
                if(currentx==0 || currenty==Board.BOARD_SIZE-1)
                    return null;
                currentx--;
                currenty++;
            }
            return point;
        }

        private boolean hasNorthEast(Point seed) {
            return (board.getDisk(new Point(seed.x+1,seed.y-1)).getState() == board.getDisk(seed).getState());
        }

        private Point searchNorthEast(Point seed) {
            Point point = new Point();
            int currentx = seed.x+1;
            int currenty = seed.y-1;
            while(currentx<Board.BOARD_SIZE && currenty>=0) {
                point.setLocation(currentx,currenty );
                if(board.getDisk(point).getState() == DiskState.EMPTY)
                    break;
                if(currentx==Board.BOARD_SIZE-1 || currenty==0)
                    return null;
                currentx++;
                currenty--;
            }
            return point;
        }

}

package spw4.game2048;

import java.awt.Point;

public class Tile {

	private Board myBoard;
	private int posX;
	private int posY;
	private int value;
	
	private boolean hasMerged;
	
	public Tile(Board myBoard, int posX, int posY, int value) {
		if (myBoard == null) throw new IllegalArgumentException("Board cannot be null");
		if (posX < 0 || posY < 0) throw new IllegalArgumentException("Coordinates cannot be lower than zero");
		if (posX > 3 || posY > 3) throw new IllegalArgumentException("Coordinates cannot be greater than three");
		if (value < 2) throw new IllegalArgumentException("Tile value cannot be smaller than 2");
		if (value % 2 != 0) throw new IllegalArgumentException("Tile value must be even");
		
		this.myBoard = myBoard;
		this.posX = posX;
		this.posY = posY;
		this.value = value;
		this.hasMerged = false;
	}
	
	public boolean getHasMerged() {
		return hasMerged;
	}

	public void setHasMerged(boolean hasMerged) {
		this.hasMerged = hasMerged;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int move(Direction move) {
		if (move == Direction.up && posY == 0) return 0;
		if (move == Direction.down && posY == 3) return 0;
		if (move == Direction.left && posX == 0) return 0;
		if (move == Direction.right && posX == 3) return 0;
		
		Point targetPoint = getTargetCoordinates(move);
		Tile target = myBoard.getTile(targetPoint.x, targetPoint.y);
		
		if (target == null) { // can move to target
			myBoard.setTile(targetPoint.x, targetPoint.y, this);
			myBoard.setTile(posX, posY, null);
			this.posX = targetPoint.x;
			this.posY = targetPoint.y;
			return move(move);
		}
		else if(target.getValue() == this.getValue() && !target.getHasMerged() && !this.getHasMerged()) { // can merge with target
			myBoard.setTile(targetPoint.x, targetPoint.y, this);
			myBoard.setTile(posX, posY, null);
			this.posX = targetPoint.x;
			this.posY = targetPoint.y;
			this.value = this.value * 2;
			this.hasMerged = true;
			return this.value;
		}
		
		return 0;
	}
	
	private Point getTargetCoordinates(Direction direction) {
		switch (direction) {
		case up:
			return new Point(posX, posY-1);
		case down:
			return new Point(posX, posY+1);
		case left:
			return new Point(posX-1, posY);
		case right:
			return new Point(posX+1, posY);
		}
		throw new IllegalArgumentException("Encountered unknown direction");
	}
	
}

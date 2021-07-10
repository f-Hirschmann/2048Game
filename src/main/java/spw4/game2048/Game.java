package spw4.game2048;

import java.util.Random;

public class Game {
	
	private int score;
	private int moveCount;
	private boolean hasLost;
	private Board myBoard;
	
	public Board getBoard() {
		return myBoard;
	}
	
    public Game() {
    	if(Board.random == null) Board.random = new Random();
        myBoard = new Board();
        score = 0;
        moveCount = 0;
        hasLost = false;
    }

    public int getScore() {
        return score;
    }
    
    public int getMoves() {
    	return moveCount;
    }
    
    public int getValueAt(int x, int y) {
    	return myBoard.getTile(x, y) == null ? 0 : myBoard.getTile(x, y).getValue();
    }

    public boolean isOver() {
    	return hasLost || isWon();
    }

    public boolean isWon() {
    	boolean found2048 = false;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(myBoard.getTile(i, j) != null) 
					if(myBoard.getTile(i, j).getValue() == 2048)
						found2048 = true;
			}
		}
		return found2048;
    }

    @Override
    public String toString() {
    	String outPut = "Moves: " + moveCount + " Score: " + score + "\n";
    	outPut += myBoard.toString();
        return outPut;
    }

    public void initialize() {
    	if(Board.random == null) Board.random = new Random();
        myBoard = new Board();
        score = 0;
        moveCount = 0;
    }

    public void move(Direction direction) {
    	score += myBoard.move(direction);
    	moveCount++;
    	hasLost = !myBoard.generateRandomTile();
    }
}

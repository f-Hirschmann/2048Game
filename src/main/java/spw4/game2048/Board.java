package spw4.game2048;

import java.util.Random;

public class Board {
	
	private Tile[][] tiles;
	public static Random random;
	
	public Board() {
		tiles = new Tile[4][4];
		generateRandomTile();
		generateRandomTile();
	}

	public int move(Direction move) {
		int score = 0;
		
		switch(move) {
		case up:
			score = this.doUpMove();
			break;
		case down:
			score = this.doDownMove();
			break;
		case left:
			score = this.doLeftMove();
			break;
		case right:
			score = this.doRightMove();
			break;
		}
		
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 4; y++) {
				if(tiles[x][y] != null) tiles[x][y].setHasMerged(false);
			}
		}
		return score;
	}
	
	private int doRightMove() {
		int score = 0;
		for(int x = 3; x >= 0; x--) {
			for(int y = 0; y < 4; y++) {
				score += tiles[x][y] != null ? tiles[x][y].move(Direction.right) : 0;
			}
		}
		return score;
	}

	private int doLeftMove() {
		int score = 0;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 4; y++) {
				score += tiles[x][y] != null ? tiles[x][y].move(Direction.left) : 0;
			}
		}
		return score;
	}

	private int doDownMove() {
		int score = 0;
		for(int x = 0; x < 4; x++) {
			for(int y = 3; y >= 0; y--) {
				score += tiles[x][y] != null ? tiles[x][y].move(Direction.down) : 0;
			}
		}
		return score;
	}

	private int doUpMove() {
		int score = 0;
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				score += tiles[x][y] != null ? tiles[x][y].move(Direction.up) : 0;
			}
		}
		return score;
	}

	public void drawBoard() {
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				System.out.print(tiles[x][y] != null ? tiles[x][y].getValue() : ".");
				System.out.print("\t");
			}
			System.out.println();
		}
	}
	
	@Override
    public String toString() {
		String outPut = "";
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				outPut += (tiles[x][y] != null ? tiles[x][y].getValue() : ".");
				outPut += "\t";
			}
			outPut += "\n";
		}
		return outPut;
	}
	
	public Tile getTile(int posX, int posY) {
		return tiles[posX][posY];
	}
	
	public void setTile(int posX, int posY, Tile tile) {
		tiles[posX][posY] = tile;
	}
	
	public boolean generateRandomTile() {
		boolean emptyTileFound = false;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(tiles[i][j] == null) emptyTileFound = true;
			}
		}
		
		if(!emptyTileFound) return false;
		
		int posX = random.nextInt(4);
		int posY = random.nextInt(4);
		int value = random.nextInt(10) == 0 ? 4 : 2;
		
		while(tiles[posX][posY] != null) {
			posX = random.nextInt(4);
			posY = random.nextInt(4);
			value = random.nextInt(10) == 0 ? 4 : 2;
		}
		
		tiles[posX][posY] = new Tile(this, posX, posY, value);
		return true;
	}
}

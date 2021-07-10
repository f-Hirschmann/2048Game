package spw4.game2048;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTests {

	@DisplayName("Game.ctor returns new instance with 0 score")
	@Test
	final void testGame() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
		Game game = new Game();
		assertAll(
				() -> assertNotNull(game.getBoard()),
				() -> assertEquals(0, game.getScore())
				);
	}

	@DisplayName("Game.isOver returns true when the game is won")
	@Test
	final void testIsOverWhenWon() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
		Game game = new Game();
		game.getBoard().setTile(0, 0, new Tile(game.getBoard(), 0, 0, 2048));
		assertEquals(true, game.isOver());
	}
	
	@DisplayName("Game.isOver returns true when no new tiles could be created after the last turn")
	@Test
	final void testIsOverWhenCouldNotCreateNewTiles() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 2)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 2
		Game game = new Game();
		int value = 2;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 4; y++) {
				game.getBoard().setTile(x, y, new Tile(game.getBoard(), x, y, value));
			}
			value = value * 2;
		}
		game.move(Direction.right);
		assertEquals(true, game.isOver());
	}
	
	@DisplayName("Game.isOver returns false when game is not over")
	@Test
	final void testIsOverWhenIsNotOver() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 2)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 2
		Game game = new Game();
		assertEquals(false, game.isOver());
	}

	@DisplayName("Game.isWon returns true when game is won")
	@Test
	final void testIsWonWhenActuallyHasWon() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
		Game game = new Game();
		game.getBoard().setTile(0, 0, new Tile(game.getBoard(), 0, 0, 2048));
		assertEquals(true, game.isWon());
	}
	
	@DisplayName("Game.isWon returns false when game is not won")
	@Test
	final void testIsWonWhenActuallyHasNotWon() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
		Game game = new Game();
		game.getBoard().setTile(0, 0, new Tile(game.getBoard(), 0, 0, 1024));
		assertEquals(false, game.isWon());
	}

	@DisplayName("Game.initialize resets the board and the score")
	@Test
	final void testInitialize() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0, 3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
		Game game = new Game();
		game.initialize();
		assertAll(
				() -> assertNotNull(game.getBoard()),
				() -> assertEquals(0, game.getScore())
				);
	}

	@DisplayName("Game.move performs a move")
	@Test
	final void testMove() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0, 2, 2, 1)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
		Game game = new Game();
		game.getBoard().setTile(2, 1, new Tile(game.getBoard(), 2, 1, 2));
		game.move(Direction.right);
		assertAll(
				() -> assertEquals(4 ,game.getScore())
				);
	}

}

package spw4.game2048;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

class BoardTests {

	@DisplayName("Board.ctor returns new instance with two random tiles set")
	@Test
	final void boardCtorReturnsNewInstanceWithTwoRandomTiles() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
		Board testBoard = new Board();
		
		assertAll(
			() -> assertEquals(2, testBoard.getTile(3, 1).getValue()),
			() -> assertEquals(4, testBoard.getTile(0, 1).getValue())
		);
	}
	
	@DisplayName("Board.move when ...")
	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	public class MoveTests {

		@DisplayName("... moving outside boundaries, nothing happens")
		@Test
		void moveWhenMovingOutsideBoundariesNothingHappens() {
			Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
			Board testBoard = new Board();
			
			testBoard.move(Direction.right);
			
			assertAll(
					() -> assertEquals(2, testBoard.getTile(3, 1).getValue()),
					() -> assertEquals(4, testBoard.getTile(2, 1).getValue())
					);
		}
		
		@DisplayName("... moving into a tile with same value, they merge")
		@Test
		void moveWithMerge() {
			Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 2)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
			Board testBoard = new Board();
			
			int score = testBoard.move(Direction.right);
			
			//tiles should have merged and score should
			assertAll(
					() -> assertEquals(4, testBoard.getTile(3, 1).getValue()),
					() -> assertEquals(4, score),
					() -> assertEquals(false, testBoard.getTile(3, 1).getHasMerged()),
					() -> assertNull(testBoard.getTile(0, 1))
					);
		}
		
		@DisplayName("... moving, where many Merges happen")
		@Test
		void moveWithMultipleMerges() {
			Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 2)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
			Board testBoard = new Board();
			
			for(int x = 0; x < 4; x++) {
				for(int y = 0; y < 4; y++) {
					testBoard.setTile(x, y, new Tile(testBoard, x, y, 2));
				}
			}
			
			int score = testBoard.move(Direction.down);
			
			//tiles should have merged and score should
			assertAll(
					() -> assertEquals(4, testBoard.getTile(0, 2).getValue()),
					() -> assertEquals(4, testBoard.getTile(1, 2).getValue()),
					() -> assertEquals(4, testBoard.getTile(2, 2).getValue()),
					() -> assertEquals(4, testBoard.getTile(3, 2).getValue()),
					() -> assertEquals(4, testBoard.getTile(0, 3).getValue()),
					() -> assertEquals(4, testBoard.getTile(1, 3).getValue()),
					() -> assertEquals(4, testBoard.getTile(2, 3).getValue()),
					() -> assertEquals(4, testBoard.getTile(3, 3).getValue()),
					() -> assertNull(testBoard.getTile(0, 0)),
					() -> assertNull(testBoard.getTile(1, 0)),
					() -> assertNull(testBoard.getTile(2, 0)),
					() -> assertNull(testBoard.getTile(3, 0)),
					() -> assertNull(testBoard.getTile(0, 1)),
					() -> assertNull(testBoard.getTile(1, 1)),
					() -> assertNull(testBoard.getTile(2, 1)),
					() -> assertNull(testBoard.getTile(3, 1)),
					() -> assertEquals(32, score)
					);
		}
	}

	@DisplayName("Board.setTile sets Tile at correct position")
	@Test
	final void testSetTile() {
		Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
		Board testBoard = new Board();
		
		testBoard.setTile(2, 2, new Tile(testBoard, 2, 2, 2));
		assertAll(
				() -> assertEquals(2, testBoard.getTile(3, 1).getValue()),
				() -> assertEquals(4, testBoard.getTile(0, 1).getValue()),
				() -> assertEquals(2, testBoard.getTile(2, 2).getValue())
				);
	}

}

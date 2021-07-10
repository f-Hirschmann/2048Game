package spw4.game2048;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TileTests {
	
	@Mock
	Board board;
	
	@DisplayName("Tile.ctor when ...")
	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	public class ConstructorTests {
		
		@DisplayName("... board is null, throws IllegalArgumentException")
		@Test
		void ctorWhenBoardIsNullThrowsIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> new Tile(null, 1, 1, 2));
		}
		
		@DisplayName("... position is smaller than 0, throws IllegalArgumentException")
		@ParameterizedTest
		@MethodSource
		void ctorWhenPositionIsSmallerThanZeroThrowsIllegalArgumentException(int[] values) {
			assertThrows(IllegalArgumentException.class, () -> new Tile(board, values[0], values[1], 2));
		}
		
		Stream<Arguments> ctorWhenPositionIsSmallerThanZeroThrowsIllegalArgumentException() {
			return Stream.of(
				Arguments.of(new int[] {-1, -1}),
				Arguments.of(new int[] {-5,  3}),
				Arguments.of(new int[] { 3, Integer.MIN_VALUE}),
				Arguments.of(new int[] {10, -1})
			);
		}
		
		@DisplayName("... position is larger than 3, throws IllegalArgumentException")
		@ParameterizedTest
		@MethodSource
		void ctorWhenPositionIsLargerThanThreeThrowsIllegalArgumentException(int[] values) {
			assertThrows(IllegalArgumentException.class, () -> new Tile(board, values[0], values[1], 2));
		}
		
		Stream<Arguments> ctorWhenPositionIsLargerThanThreeThrowsIllegalArgumentException() {
			return Stream.of(
				Arguments.of(new int[] { 4,  4}),
				Arguments.of(new int[] { 5,  3}),
				Arguments.of(new int[] { 1, Integer.MAX_VALUE}),
				Arguments.of(new int[] {10,  3})
			);
		}
		
		@DisplayName("... value is smaller than 1, throws IllegalArgumentException")
		@ParameterizedTest
		@MethodSource
		void ctorWhenValueIsSmallerThanTwoThrowsIllegalArgumentException(int[] values) {
			assertThrows(IllegalArgumentException.class, () -> new Tile(board, values[0], values[1], values[2]));
		}
		
		Stream<Arguments> ctorWhenValueIsSmallerThanTwoThrowsIllegalArgumentException() {
			return Stream.of(
				Arguments.of(new int[] { 1, 1,  0}),
				Arguments.of(new int[] { 1, 1, -2}),
				Arguments.of(new int[] { 1, 1, -10}),
				Arguments.of(new int[] { 1, 1, Integer.MIN_VALUE})
			);
		}
		
		@DisplayName("... value is not even, throws IllegalArgumentException")
		@ParameterizedTest
		@MethodSource
		void ctorWhenValueIsNotEvenThrowsIllegalArgumentException(int[] values) {
			assertThrows(IllegalArgumentException.class, () -> new Tile(board, values[0], values[1], values[2]));
		}
		
		Stream<Arguments> ctorWhenValueIsNotEvenThrowsIllegalArgumentException() {
			return Stream.of(
				Arguments.of(new int[] { 1, 1,  3}),
				Arguments.of(new int[] { 1, 1,  5}),
				Arguments.of(new int[] { 1, 1, 2047}),
				Arguments.of(new int[] { 1, 1, 133})
			);
		}
		
		@DisplayName("... arguments are valid, returns new instance")
		@ParameterizedTest
		@MethodSource
		void ctorWhenArgumentsAreValidReturnNewInstance(int[] values) {
			Tile tile = new Tile(board, values[0], values[1], values[2]);
			
			assertAll(
				() -> assertNotNull(tile),
				() -> assertEquals(false, tile.getHasMerged()),
				() -> assertEquals(values[0], tile.getPosX()),
				() -> assertEquals(values[1], tile.getPosY()),
				() -> assertEquals(values[2], tile.getValue())
			);	
		}
		
		Stream<Arguments> ctorWhenArgumentsAreValidReturnNewInstance() {
			return Stream.of(
				Arguments.of(new int[] {2,  1, 2}),
				Arguments.of(new int[] {3,  0, 4}),
				Arguments.of(new int[] {1,  2, 8}),
				Arguments.of(new int[] {0,  3, 16})
			);
		}
	}

	@DisplayName("Tile.move when ...")
	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	public class MoveTests {
		
		@DisplayName("... moving outside boundaries, nothing happens")
		@ParameterizedTest
		@MethodSource
		void moveWhenMovingOutsideBoundariesNothingHappens(Direction move) {
			int y = move == Direction.up ? 0 : 3;
			int x = move == Direction.left ? 0 : 3;
			Tile tile = new Tile(board, x, y, 2);
			tile.move(move);
			
			assertAll(
				() -> assertEquals(x, tile.getPosX()),
				() -> assertEquals(y, tile.getPosY()),
				() -> assertEquals(false, tile.getHasMerged())
			);
		}
		
		Stream<Direction> moveWhenMovingOutsideBoundariesNothingHappens() {
			return Stream.of(
				Direction.up,
				Direction.down,
				Direction.right,
				Direction.left
			);
		}
		
		@DisplayName("... moving to empty tile, moves to that tile or further")
		@ParameterizedTest
		@MethodSource
		void moveWhenMovingToEmptyTileMovesToThatTileOrFurther(Direction move) {
			when(board.getTile(anyInt(), anyInt())).thenReturn(null);
			
			Tile tile = new Tile(board, 1, 1, 2);
			tile.move(move);
			
			int x;
			int y;
			
			switch (move) {
			case up:
				x = 1;
				y = 0;
				break;
			case down:
				x = 1;
				y = 3;
				break;
			case left:
				x = 0;
				y = 1;
				break;
			case right:
				x = 3;
				y = 1;
				break;
			default:
				x = 0;
				y = 0;
				break;
			}
			
			assertAll(
				() -> assertEquals(x, tile.getPosX()),
				() -> assertEquals(y, tile.getPosY()),
				() -> assertEquals(false, tile.getHasMerged())
			);
		}
		
		Stream<Direction> moveWhenMovingToEmptyTileMovesToThatTileOrFurther() {
			return Stream.of(
				Direction.up,
				Direction.down,
				Direction.right,
				Direction.left
			);
		}
		
		
		@DisplayName("... moving to not empty tile, moves to that tile and no further")
		@Test
		void moveWhenBoardHasFewTileInTheWay() {
			Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
			Board testBoard = new Board();
			
			testBoard.setTile(1, 1, new Tile(testBoard, 1, 1, 2));
			
			testBoard.getTile(0, 1).move(Direction.right);
			//Tile should not move because extra Tile is in way with a different value, so no merge
			assertAll(
					() -> assertEquals(2, testBoard.getTile(3, 1).getValue()),
					() -> assertEquals(4, testBoard.getTile(0, 1).getValue()),
					() -> assertEquals(2, testBoard.getTile(1, 1).getValue())
					);
		}
		
		@DisplayName("... moving to tile with equal value, moves to that tile and merges")
		@Test
		void moveWhenBoardHasATileInTheWayButCanMerge() {
			Board.random = new IntRandomStub(List.of(3, 1, 2, 0, 1, 0)); //pos 3,1 has tile with value 2 and pos 0, 1 has tile with value 4
			Board testBoard = new Board();
			
			testBoard.setTile(1, 1, new Tile(testBoard, 1, 1, 4));
			
			int score = testBoard.getTile(0, 1).move(Direction.right);
			//Tile should move and merge, old Tile should be gone
			assertAll(
					() -> assertEquals(8, score),
					() -> assertEquals(2, testBoard.getTile(3, 1).getValue()),
					() -> assertEquals(8, testBoard.getTile(1, 1).getValue()),
					() -> assertEquals(true, testBoard.getTile(1, 1).getHasMerged()),
					() -> assertNull(testBoard.getTile(0, 1))
					);
		}
	}

}

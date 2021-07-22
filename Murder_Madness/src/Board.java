import java.util.*;

/**
 * 
 * @author Runtime Terror
 */
public class Board {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	private final int SIZE = 24;

	// Board Associations
	private List<Square> gameSquares;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Board() {
		gameSquares = new ArrayList<Square>();
		for (int i = 0; i < Math.pow(SIZE, 2); i++) {
			gameSquares.add(new Square(true, "__|"));
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	/* Code from template association_GetMany */
	public Square getGameSquare(int index) {
		Square aGameSquare = gameSquares.get(index);
		return aGameSquare;
	}

	public Square getGameSquare(int x, int y) {
		Square aGameSquare = gameSquares.get(y * SIZE + x);
		return aGameSquare;
	}

	public List<Square> getGameSquares() {
		List<Square> newGameSquares = Collections.unmodifiableList(gameSquares);
		return newGameSquares;
	}

	public int numberOfGameSquares() {
		int number = gameSquares.size();
		return number;
	}

	public boolean hasGameSquares() {
		boolean has = gameSquares.size() > 0;
		return has;
	}

	public int indexOfGameSquare(Square aGameSquare) {
		int index = gameSquares.indexOf(aGameSquare);
		return index;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfGameSquares() {
		return 0;
	}

	/* Code from template association_AddUnidirectionalMany */
	public boolean addGameSquare(Square aGameSquare) {
		boolean wasAdded = false;
		if (gameSquares.contains(aGameSquare)) {
			return false;
		}
		gameSquares.add(aGameSquare);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeGameSquare(Square aGameSquare) {
		boolean wasRemoved = false;
		if (gameSquares.contains(aGameSquare)) {
			gameSquares.remove(aGameSquare);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	public int boardSize() {
		return SIZE;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addGameSquareAt(Square aGameSquare, int index) {
		boolean wasAdded = false;
		if (addGameSquare(aGameSquare)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfGameSquares()) {
				index = numberOfGameSquares() - 1;
			}
			gameSquares.remove(aGameSquare);
			gameSquares.add(index, aGameSquare);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveGameSquareAt(Square aGameSquare, int index) {
		boolean wasAdded = false;
		if (gameSquares.contains(aGameSquare)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfGameSquares()) {
				index = numberOfGameSquares() - 1;
			}
			gameSquares.remove(aGameSquare);
			gameSquares.add(index, aGameSquare);
			wasAdded = true;
		} else {
			wasAdded = addGameSquareAt(aGameSquare, index);
		}
		return wasAdded;
	}

	public void delete() {
		gameSquares.clear();
	}

	/**
	 * this is for testing only and should be updated
	 */
	public void printBoard(){
		String print = "";
		for (int y = 0; y < SIZE; y++) {
			String temp = "";
			for (int x = 0; x < SIZE; x++) {
				temp += getGameSquare(x, y).getCharacter();
			}
			print += temp;
			print += "\n";
		}
		System.out.println(print);
	}
}
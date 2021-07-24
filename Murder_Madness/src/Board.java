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
			gameSquares.add(new Square(String.valueOf((char) (getX(i) + 'a')) + (getY(i))));
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	public int getX(int index) {
		return index % SIZE;
	}

	public int getY(int index) {
		return index / SIZE;
	}
	/* Code from template association_GetMany */
	public Square getGameSquare(int index) {
		Square aGameSquare = gameSquares.get(index);
		return aGameSquare;
	}

	public Square getGameSquare(int x, int y) {
		Square aGameSquare = gameSquares.get(y * SIZE + x);
		return aGameSquare;
	}
	public boolean isGameSquare(int x, int y) {
		if(x < 0 || y < 0 || x >= SIZE || y >= SIZE || !getGameSquare(x, y).isAccessible())
			return false;
		return true;
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
	
	public Square gotFromCode(String position) {
		try {
		return getGameSquare(position.charAt(0) + 'a' - 2, Integer.parseInt(position.substring(1)) - 8);
		}
		catch(Exception e) {
			return null;
		}
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
	public void printBoard(List<Character>characters){
		String print = "";
		for (int y = 0; y < SIZE; y++) {
			String temp = y < 10 ? " " + y : "" + y;
			for (int x = 0; x < SIZE; x++) {
				temp += getGameSquare(x, y).getCharacter();
			}
			print += temp;
			print += "\n";
		}
		print += " ";
		for(int i = 0; i < SIZE; i++) {
			print +=  " " + String.valueOf((char) (i + 'a')) + " ";
		}
		for(Character c : characters) {
			int charPos = gameSquares.indexOf(c.getLocation());
			charPos = (charPos + getY(charPos)) * 3 + 2;
			print = print.substring(0, charPos) + c.getName().substring(0, 2) + print.substring(charPos + 2);
		}
		System.out.println(print);
	}
}
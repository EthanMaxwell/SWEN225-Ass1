import java.util.*;

/**
 * The game board that manages all the game squares
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
	/**
	 * Get the x value of a square in the given index
	 * 
	 * @param index
	 * @return The x value computed
	 */
	public int getX(int index) {
		return index % SIZE;
	}

	/**
	 * Get the y value of a square in the given index
	 * 
	 * @param index
	 * @return The y value computed
	 */
	public int getY(int index) {
		return index / SIZE;
	}

	/**
	 * Get the square at a given index
	 * 
	 * @param index
	 * @return The square at the index
	 */
	public Square getGameSquare(int index) {
		Square aGameSquare = gameSquares.get(index);
		return aGameSquare;
	}

	/**
	 * Get the square at a given x and y coordinate
	 * 
	 * @param x
	 * @param y
	 * @return The square located
	 */
	public Square getGameSquare(int x, int y) {
		Square aGameSquare = gameSquares.get(y * SIZE + x);
		return aGameSquare;
	}

	/**
	 * Check if given coordinates can be moved onto by a player
	 * 
	 * @param x
	 * @param y
	 * @return If location is able to be moved onto
	 */
	public boolean isGameSquare(int x, int y) {
		if (x < 0 || y < 0 || x >= SIZE || y >= SIZE || !getGameSquare(x, y).isAccessible())
			return false;
		return true;
	}

	/**
	 * @return All the game square
	 */
	public List<Square> getGameSquares() {
		List<Square> newGameSquares = Collections.unmodifiableList(gameSquares);
		return newGameSquares;
	}

	/**
	 * @return The number of game square
	 */
	public int numberOfGameSquares() {
		int number = gameSquares.size();
		return number;
	}

	/**
	 * Find the index in the list of a given game square
	 * 
	 * @param aGameSquare Square to find the index of
	 * @return The index of the square
	 */
	public int indexOfGameSquare(Square aGameSquare) {
		int index = gameSquares.indexOf(aGameSquare);
		return index;
	}

	/**
	 * Attempts to find a square that corresponds to the given code (like b4) and
	 * returns one if found, otherwise returns null
	 * 
	 * @param position The code of the position as a string
	 * @return The located square or null
	 */
	public Square gotFromCode(String position) {
		try {
			// Try to extract square at game code
			return getGameSquare(position.charAt(0) + 'a' - 2, Integer.parseInt(position.substring(1)) - 8);
		}
		// Something went wrong so return null.
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * @return The amount of squares along a side of the board
	 */
	public int boardSize() {
		return SIZE;
	}

	/**
	 * Prints out the board, but requires player to be passed to it if are are to be
	 * displayed
	 * 
	 * @param characters List of characters to be printed on the board
	 */
	public void printBoard(List<Character> characters) {
		String print = "";
		for (int y = 0; y < SIZE; y++) {
			// Print the row number at start of each row
			String temp = y < 10 ? " " + y : "" + y;
			// Print out this squares text representation
			for (int x = 0; x < SIZE; x++) {
				temp += getGameSquare(x, y).getCharacter();
			}
			print += temp;
			print += "\n";
		}
		print += " ";
		
		// Print the column letter below each column
		for (int i = 0; i < SIZE; i++) {
			print += " " + String.valueOf((char) (i + 'a')) + " ";
		}
		
		// Print characters onto the correct squares
		for (Character c : characters) {
			int charPos = gameSquares.indexOf(c.getLocation());
			charPos = (charPos + getY(charPos)) * 3 + 2;
			print = print.substring(0, charPos) + c.getName().substring(0, 2) + print.substring(charPos + 2);
		}
		
		System.out.println(print);
	}
}
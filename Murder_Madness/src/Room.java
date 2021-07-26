import java.util.*;
/**
 * Represents a room that is a group of squares with a name
 * 
 * @author Runtime Terror
 */
public class Room {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Room Attributes
	private String name;
	private List<Square> squares =  new ArrayList<>();


	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
	 * Create a new room with the given name
	 * 
	 * @param aName
	 */
	public Room(String aName) {
		name = aName;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	/**
	 * @return The name of the room
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Add the given square to the room
	 * 
	 * @param square
	 */
	public void addSquare(Square square) {
		squares.add(square);
	}
	
	/**
	 * @return A unmodifiable list of the rooms squares
	 */
	public List<Square> getSquares() {
		List<Square> newSquares = Collections.unmodifiableList(squares);
		return newSquares;
	}

	public String toString() {
		return getName();
	}
}
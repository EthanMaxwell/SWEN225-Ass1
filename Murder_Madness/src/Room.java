import java.util.*;
/**
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

	public Room(String aName) {
		name = aName;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setName(String aName) {
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}

	public String getName() {
		return name;
	}
	
	public void addSquare(Square square) {
		squares.add(square);
	}
	
	public List<Square> getSquares() {
		List<Square> newSquares = Collections.unmodifiableList(squares);
		return newSquares;
	}

	public String toString() {
		return getName();
	}
}
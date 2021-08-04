package GameCode;
/**
 * A single square that helps to form the game board
 * 
 * @author Runtime Terror
 */
public class Square {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Square Attributes
	private boolean accessible = true;

	// Square Associations
	private Room partOf = null;

	// Character representing each square for printing
	private String position;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
	 * Construct a new square it's position/name will be the given string
	 * 
	 * @param position String the corresponds to the squares name/position
	 */
	public Square(String position) {
		this.position = position;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	/**
	 * @param aAccessible Set the accessibility of this square to this value
	 * @return if the change was successful
	 */
	public boolean setAccessible(boolean aAccessible) {
		boolean wasSet = false;
		accessible = aAccessible;
		wasSet = true;
		return wasSet;
	}

	/**
	 * @return If this square is accessible
	 */
	public boolean isAccessible() {
		return accessible;
	}

	
	/**
	 * @return The room this square is part of (may be null)
	 */
	public Room getPartOf() {
		return partOf;
	}

	/**
	 * @return If the square is part of a room
	 */
	public boolean hasPartOf() {
		boolean has = partOf != null;
		return has;
	}

	/**
	 * @param aNewPartOf Set the room this square is part of
	 * @return If the setting was successful
	 */
	public boolean setPartOf(Room aNewPartOf) {
		boolean wasSet = false;
		partOf = aNewPartOf;
		wasSet = true;
		return wasSet;
	}

	/**
	 * @return The text representation of this cell for displaying the board
	 */
	public String getCharacter() {
		if (hasPartOf()) {
			if (accessible)
				return "   "; // Room entry
			return " " + partOf.getName().charAt(0) + " "; //Room body
		}
		if (!accessible)
			return "WW|"; // Inaccessible square outside a room
		return "__|"; // Standard square
	}

	/**
	 * @return The text position of this square
	 */
	public String getPosition() {
		return position;
	}

	public String toString() {
		return position;
	}
}
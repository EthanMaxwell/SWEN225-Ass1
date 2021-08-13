package GameCode;
/**
 * A single square that helps to form the game board
 * 
 * @author Runtime Terror
 */
public class Square implements Location{

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Square Attributes
	private boolean accessible = true;

	// Square Attributes
	private boolean door = false;

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
	 * Set a square to be a door
	 * @param accessible
	 */
	public void setDoor(boolean accessible){
		door = accessible;
	}

	/**
	 * @return If the square is a door
	 */
	public boolean isDoor(){
		return door;
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
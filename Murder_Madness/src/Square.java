/**
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

	//Character representing each square for printing
	private String position;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Square(String position) {
		this.position = position;
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setAccessible(boolean aAccessible) {
		boolean wasSet = false;
		accessible = aAccessible;
		wasSet = true;
		return wasSet;
	}

	public boolean isAccessible() {
		return accessible;
	}

	/* Code from template association_GetOne */
	public Room getPartOf() {
		return partOf;
	}

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

	public String getCharacter(){
		if(hasPartOf()) {
			if(accessible)
				return "   ";
			return " " + partOf.getName().charAt(0) + " ";
		}
		if(!accessible)
			return "WW|";
		return "__|";
	}
	
	public String getPosition() {
		return position;
	}

	public String toString() {
		return position;
	}
}
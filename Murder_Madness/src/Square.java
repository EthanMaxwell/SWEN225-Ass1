/**
 * 
 * @author Runtime Terror
 */
public class Square {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Square Attributes
	private boolean accessible;

	// Square Associations
	private Room partOf;

	//Character representing each square for printing
	private String Character;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Square(boolean aAccessible) {
		accessible = aAccessible;
	}
	public Square(boolean aAccessible, String character) {
		accessible = aAccessible; Character = character;
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

	public boolean getAccessible() {
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

	/* Code from template association_SetUnidirectionalOptionalOne */
	public boolean setPartOf(Room aNewPartOf) {
		boolean wasSet = false;
		partOf = aNewPartOf;
		wasSet = true;
		return wasSet;
	}

	public void delete() {
		partOf = null;
	}
	public void setCharacter(String character){Character = character;}

	public String getCharacter(){return Character;}

	public String toString() {
		return super.toString() + "[" + "accessible" + ":" + getAccessible() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "partOf = "
				+ (getPartOf() != null ? Integer.toHexString(System.identityHashCode(getPartOf())) : "null");
	}
}
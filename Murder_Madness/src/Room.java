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

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "]";
	}
}
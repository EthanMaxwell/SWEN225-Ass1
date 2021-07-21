/**
 * 
 * @author Runtime Terror
 */
public class Weapon {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Weapon Attributes
	private String name;

	// Weapon Associations
	private Room location;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Weapon(String aName, Room aLocation) {
		name = aName;
		if (!setLocation(aLocation)) {
			throw new RuntimeException(
					"Unable to create Weapon due to aLocation. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
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

	/* Code from template association_GetOne */
	public Room getLocation() {
		return location;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setLocation(Room aNewLocation) {
		boolean wasSet = false;
		if (aNewLocation != null) {
			location = aNewLocation;
			wasSet = true;
		}
		return wasSet;
	}

	public void delete() {
		location = null;
	}

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "location = "
				+ (getLocation() != null ? Integer.toHexString(System.identityHashCode(getLocation())) : "null");
	}
}
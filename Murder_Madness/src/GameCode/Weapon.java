package GameCode;
/**
 * A weapon that in part of the game
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

	/**
	 * Create a new weapon with the given name and location (Room)
	 * 
	 * @param aName
	 * @param aLocation Room to create weapon in
	 */
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
	
	/**
	 * @return Name of this room
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Room this weapon is in
	 */
	public Room getLocation() {
		return location;
	}

	
	/**
	 * Set the location of the weapon to the given room
	 * 
	 * @param aNewLocation Location to move the weapon too
	 * @return If the move was successful
	 */
	public boolean setLocation(Room aNewLocation) {
		boolean wasSet = false;
		if (aNewLocation != null) {
			location = aNewLocation;
			wasSet = true;
		}
		return wasSet;
	}

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "location = "
				+ (getLocation() != null ? Integer.toHexString(System.identityHashCode(getLocation())) : "null");
	}
}
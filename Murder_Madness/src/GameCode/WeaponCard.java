package GameCode;
/**
 * A card that represents a weapon
 * 
 * @author Runtime Terror
 */
public class WeaponCard implements Card {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// WeaponCard Associations
	private Weapon weapon;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
	 * Create a new card for the given weapon
	 * 
	 * @param aWeapon
	 */
	public WeaponCard(Weapon aWeapon) {
		super();
		if (!setWeapon(aWeapon)) {
			throw new RuntimeException(
					"Unable to create WeaponCard due to aWeapon. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	/**
	 * @return Weapon this card represents
	 */
	public Weapon getWeapon() {
		return weapon;
	}
	
	public String getName() {
		return weapon.getName();
	}

	/**
	 * Set CardTriplet weapon that this card represents
	 * 
	 * @param aNewWeapon Weapon to change too
	 * @return If change was successful
	 */
	public boolean setWeapon(Weapon aNewWeapon) {
		boolean wasSet = false;
		if (aNewWeapon != null) {
			weapon = aNewWeapon;
			wasSet = true;
		}
		return wasSet;
	}

	public String toString() {
		return weapon.getName() + " card";
	}

}
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
	/* Code from template association_GetOne */
	public Weapon getWeapon() {
		return weapon;
	}

	/* Code from template association_SetUnidirectionalOne */
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
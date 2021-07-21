/**
 * 
 * @author Runtime Terror
 */
public class Set {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Set Associations
	private RoomCard roomCard;
	private WeaponCard weaponCard;
	private CharacterCard characterCard;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Set(RoomCard aRoomCard, WeaponCard aWeaponCard, CharacterCard aCharacterCard) {
		if (!setRoomCard(aRoomCard)) {
			throw new RuntimeException(
					"Unable to create Set due to aRoomCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		if (!setWeaponCard(aWeaponCard)) {
			throw new RuntimeException(
					"Unable to create Set due to aWeaponCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		if (!setCharacterCard(aCharacterCard)) {
			throw new RuntimeException(
					"Unable to create Set due to aCharacterCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	/* Code from template association_GetOne */
	public RoomCard getRoomCard() {
		return roomCard;
	}

	/* Code from template association_GetOne */
	public WeaponCard getWeaponCard() {
		return weaponCard;
	}

	/* Code from template association_GetOne */
	public CharacterCard getCharacterCard() {
		return characterCard;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setRoomCard(RoomCard aNewRoomCard) {
		boolean wasSet = false;
		if (aNewRoomCard != null) {
			roomCard = aNewRoomCard;
			wasSet = true;
		}
		return wasSet;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setWeaponCard(WeaponCard aNewWeaponCard) {
		boolean wasSet = false;
		if (aNewWeaponCard != null) {
			weaponCard = aNewWeaponCard;
			wasSet = true;
		}
		return wasSet;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setCharacterCard(CharacterCard aNewCharacterCard) {
		boolean wasSet = false;
		if (aNewCharacterCard != null) {
			characterCard = aNewCharacterCard;
			wasSet = true;
		}
		return wasSet;
	}

	public void delete() {
		roomCard = null;
		weaponCard = null;
		characterCard = null;
	}

}
/**
 * 
 * @author Runtime Terror
 */
public class Accusation extends Set {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Accusation(RoomCard aRoomCard, WeaponCard aWeaponCard, CharacterCard aCharacterCard) {
		super(aRoomCard, aWeaponCard, aCharacterCard);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public void delete() {
		super.delete();
	}

}
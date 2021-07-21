/**
 * 
 * @author Runtime Terror
 */
public class Suggestion extends Set {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Suggestion(RoomCard aRoomCard, WeaponCard aWeaponCard, CharacterCard aCharacterCard) {
		super(aRoomCard, aWeaponCard, aCharacterCard);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public void delete() {
		super.delete();
	}

}
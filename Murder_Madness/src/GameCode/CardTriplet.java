package GameCode;
import java.util.*;
/**
 * Is a set of three objects: one room, weapon and character.
 * Used to store the game solution and player guesses and solves
 * 
 * @author Runtime Terror
 */
public class CardTriplet {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Triplet Associations
	private Room room;
	private Weapon weapon;
	private Character character;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
	 * Create the card triplet for the given room, weapon and character object
	 * 
	 * @param aRoom
	 * @param aWeapon
	 * @param aCharacter
	 */
	public CardTriplet(Room aRoom, Weapon aWeapon, Character aCharacter) {
		if (!setRoom(aRoom)) {
			throw new RuntimeException(
					"Unable to create Triplet due to aRoom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		if (!setWeapon(aWeapon)) {
			throw new RuntimeException(
					"Unable to create Triplet due to aWeapon. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		if (!setCharacter(aCharacter)) {
			throw new RuntimeException(
					"Unable to create Triplet due to aCharacter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}
	
	/**
	 * Create the card triplet for the given CARDS
	 * 
	 * @param aRoomCard
	 * @param aWeaponCard
	 * @param aCharacterCard
	 */
	public CardTriplet(RoomCard aRoomCard, WeaponCard aWeaponCard, CharacterCard aCharacterCard) {
		if (!setRoom(aRoomCard.getroom())) {
			throw new RuntimeException(
					"Unable to create Triplet due to aRoom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		if (!setWeapon(aWeaponCard.getWeapon())) {
			throw new RuntimeException(
					"Unable to create Triplet due to aWeapon. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		if (!setCharacter(aCharacterCard.getCharacter())) {
			throw new RuntimeException(
					"Unable to create Triplet due to aCharacter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	/**
	 * @return Room in this triplet
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @return Weapon in this triplet
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * @return Character in this triplet
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 * Set the room in this triplet
	 * 
	 * @param aNewRoom Room to set it to
	 * @return If the setting was successful
	 */
	public boolean setRoom(Room aNewRoom) {
		boolean wasSet = false;
		if (aNewRoom != null) {
			room = aNewRoom;
			wasSet = true;
		}
		return wasSet;
	}

	/**
	 * Set the weapon in this triplet
	 * 
	 * @param aNewWeapon Weapon to set it to
	 * @return If the setting was successful
	 */
	public boolean setWeapon(Weapon aNewWeapon) {
		boolean wasSet = false;
		if (aNewWeapon != null) {
			weapon = aNewWeapon;
			wasSet = true;
		}
		return wasSet;
	}

	/**
	 * Set the character in this triplet
	 * 
	 * @param aNewCharacter Character to set it to
	 * @return If the setting was successful
	 */
	public boolean setCharacter(Character aNewCharacter) {
		boolean wasSet = false;
		if (aNewCharacter != null) {
			character = aNewCharacter;
			wasSet = true;
		}
		return wasSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((character == null) ? 0 : character.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((weapon == null) ? 0 : weapon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardTriplet other = (CardTriplet) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}
	
	public List<Card> contains(List<Card> toSearch){
		List<Card> output = new ArrayList<>();
		for(Card c : toSearch) {
			if(character.getName().equals(c.getName()) || room.getName().equals(c.getName()) || weapon.getName().equals(c.getName()))
				output.add(c);
		}
		return output;
	}

	/**
	 * Print out this card triplet
	 */
	public void print() {
		System.out.println(character.getName() + " in the " + room.getName() + " with the " + weapon.getName());
	}

}
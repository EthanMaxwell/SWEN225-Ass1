/**
 * A card that represents a character
 * 
 * @author Runtime Terror
 */
public class CharacterCard implements Card {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// CharacterCard Associations
	private Character character;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public CharacterCard(Character character) {
		super();
		if (!setCharacter(character)) {
			throw new RuntimeException(
					"Unable to create CharacterCard due to character. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	
	/**
	 * @return Character this card represents
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 * Set character that this card represents
	 * 
	 * @param aNewCharacter Character to change to
	 * @return If change was successful
	 */
	private boolean setCharacter(Character aNewCharacter) {
		boolean wasSet = false;
		if (aNewCharacter != null) {
			character = aNewCharacter;
			wasSet = true;
		}
		return wasSet;
	}
	
	public String toString() {
		return character.getName() + " card";
	}
}
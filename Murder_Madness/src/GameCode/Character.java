package GameCode;
import java.util.*;

/**
 * A character in on the game board, may be player controlled or not
 * 
 * @author Runtime Terror
 */
public class Character {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Character Attributes
	private String name;
	private boolean isOut = false; // Record if this player is actively taking turns (out)

	// Character Associations
	private Square location;
	private List<Card> hand;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
	 * Create a new character with given name on given square
	 * 
	 * @param aName
	 * @param aLocation
	 */
	public Character(String aName, Square aLocation) {
		name = aName;
		if (!setLocation(aLocation)) {
			throw new RuntimeException(
					"Unable to create Character due to aLocation. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		hand = new ArrayList<Card>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	/**
	 * @return This characters name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The square this character is located on
	 */
	public Square getLocation() {
		return location;
	}

	/**
	 * @return A unmodifiable list of the players hand
	 */
	public List<Card> getHand() {
		List<Card> newHand = Collections.unmodifiableList(hand);
		return newHand;
	}

	/**
	 * @return Number of card in the players hand
	 */
	public int numberOfCards() {
		int number = hand.size();
		return number;
	}

	/**
	 * @return If the player has a hand (No hand probably means they arn't a player)
	 */
	public boolean hasHand() {
		boolean has = hand.size() > 0;
		return has;
	}

	/**
	 * @return Room this character is in (may be null)
	 */
	public Room getInRoom() {
		return location.getPartOf();
	}

	/**
	 * @return If this character is in a room
	 */
	public boolean isInRoom() {
		boolean has = location.getPartOf() != null;
		return has;
	}

	/**
	 * @return if the character is currently not taking turns (is out)
	 */
	public boolean isOut() {
		return isOut;
	}

	/**
	 * CardTriplet this player as out so they can not take turns anymore
	 */
	public void setOut() {
		this.isOut = true;
	}

	/**
	 * Move the character to the given location
	 * 
	 * @param aNewLocation Location to move to
	 * @return If move was successful
	 */
	public boolean setLocation(Square aNewLocation) {
		boolean wasList = false;
		if (aNewLocation != null) {
			// Change start and finish square accessibility
			if (location != null && !location.hasPartOf())
				location.setAccessible(true);
			aNewLocation.setAccessible(false);
			
			location = aNewLocation;
			wasList = true;
		}
		return wasList;
	}

	/**
	 * Add given card to this character hand
	 * 
	 * @param aCard Card to add
	 * @return If card addition was successful
	 */
	public boolean addCard(Card aCard) {
		boolean wasAdded = false;
		if (hand.contains(aCard)) {
			return false;
		}
		hand.add(aCard);
		wasAdded = true;
		return wasAdded;
	}

	/**
	 * Remove given card to this character hand
	 * 
	 * @param aCard Card to remove
	 * @return If card removal was successful
	 */
	public boolean removeCard(Card aCard) {
		boolean wasRemoved = false;
		if (hand.contains(aCard)) {
			hand.remove(aCard);
			wasRemoved = true;
		}
		return wasRemoved;
	}
	
	/**
	 * Print out the characters hand in full
	 */
	public void printHand() {
		// Get the name of each card and joins them with commas
		System.out.println("Your hand is: "
				+ (hand.stream().map(c -> c.getName()).reduce((a, b) -> (a + ", " + b))).get());
	}

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "location = "
				+ (getLocation() != null ? Integer.toHexString(System.identityHashCode(getLocation())) : "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "inRoom = "
				+ (getInRoom() != null ? Integer.toHexString(System.identityHashCode(getInRoom())) : "null");
	}
}
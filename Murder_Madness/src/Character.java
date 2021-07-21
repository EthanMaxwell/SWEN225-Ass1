import java.util.*;

/**
 * 
 * @author Runtime Terror
 */
public class Character {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Character Attributes
	private String name;

	// Character Associations
	private Square location;
	private List<Card> hand;
	private Room inRoom;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

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
	public Square getLocation() {
		return location;
	}

	/* Code from template association_GetMany */
	public Card getCard(int index) {
		Card aHand = hand.get(index);
		return aHand;
	}

	public List<Card> getHand() {
		List<Card> newHand = Collections.unmodifiableList(hand);
		return newHand;
	}

	public int numberOfCards() {
		int number = hand.size();
		return number;
	}

	public boolean hasHand() {
		boolean has = hand.size() > 0;
		return has;
	}

	public int indexOfHand(Card aHand) {
		int index = hand.indexOf(aHand);
		return index;
	}

	/* Code from template association_GetOne */
	public Room getInRoom() {
		return inRoom;
	}

	public boolean hasInRoom() {
		boolean has = inRoom != null;
		return has;
	}

	/**
	 * Move the character to the given location
	 * 
	 * @param aNewLocation Location to move to
	 * @return If move was successful
	 */
	public boolean setLocation(Square aNewLocation) {
		boolean wasSet = false;
		if (aNewLocation != null) {
			location = aNewLocation;
			wasSet = true;
		}
		return wasSet;
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
	 * Set if the character is currently in a room
	 * 
	 * @param aNewInRoom What to set characters in room status to
	 * @return If the room change was successful
	 */
	public boolean setInRoom(Room aNewInRoom) {
		boolean wasSet = false;
		inRoom = aNewInRoom;
		wasSet = true;
		return wasSet;
	}

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "location = "
				+ (getLocation() != null ? Integer.toHexString(System.identityHashCode(getLocation())) : "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "inRoom = "
				+ (getInRoom() != null ? Integer.toHexString(System.identityHashCode(getInRoom())) : "null");
	}
}
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
	private boolean isOut = false; // Record if this player is actively taking turns (out)

	// Character Associations
	private Square location;
	private List<Card> hand;

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
		return location.getPartOf();
	}

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
		boolean wasSet = false;
		if (aNewLocation != null) {
			if (location != null && !location.hasPartOf())
				location.setAccessible(true);
			aNewLocation.setAccessible(false);
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

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "location = "
				+ (getLocation() != null ? Integer.toHexString(System.identityHashCode(getLocation())) : "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "inRoom = "
				+ (getInRoom() != null ? Integer.toHexString(System.identityHashCode(getInRoom())) : "null");
	}
}
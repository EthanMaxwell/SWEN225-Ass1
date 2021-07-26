/**
 * A card that represents a room
 * 
 * @author Runtime Terror
 */
public class RoomCard implements Card {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// RoomCard Associations
	private Room room;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
	 * Create a new card for the given room
	 * 
	 * @param aroom
	 */
	public RoomCard(Room aroom) {
		super();
		if (!setroom(aroom)) {
			throw new RuntimeException(
					"Unable to create RoomCard due to aroom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	/**
	 * @return Room this card represents
	 */
	public Room getroom() {
		return room;
	}
	
	@Override
	public String getName() {
		return room.getName();
	}

	/**
	 * Set CardTriplet room that this card represents
	 * 
	 * @param aNewroom Room to change too
	 * @return If change was successful
	 */
	public boolean setroom(Room aNewroom) {
		boolean wasSet = false;
		if (aNewroom != null) {
			room = aNewroom;
			wasSet = true;
		}
		return wasSet;
	}

	public String toString() {
		return room.getName() + " card";
	}

}
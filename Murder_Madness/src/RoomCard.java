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
	/* Code from template association_GetOne */
	public Room getroom() {
		return room;
	}
	
	public String getName() {
		return room.getName();
	}

	/* Code from template association_SetUnidirectionalOne */
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
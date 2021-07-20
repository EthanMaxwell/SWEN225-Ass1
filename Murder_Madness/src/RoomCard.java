/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/



// line 51 "model.ump"
// line 169 "model.ump"
public class RoomCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomCard Associations
  private Room itsCard;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RoomCard(Room aItsCard)
  {
    super();
    if (!setItsCard(aItsCard))
    {
      throw new RuntimeException("Unable to create RoomCard due to aItsCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Room getItsCard()
  {
    return itsCard;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setItsCard(Room aNewItsCard)
  {
    boolean wasSet = false;
    if (aNewItsCard != null)
    {
      itsCard = aNewItsCard;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    itsCard = null;
    super.delete();
  }

}
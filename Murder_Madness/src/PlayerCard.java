/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/



// line 45 "model.ump"
// line 163 "model.ump"
public class PlayerCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayerCard Associations
  private Player itsCard;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayerCard(Player aItsCard)
  {
    super();
    if (!setItsCard(aItsCard))
    {
      throw new RuntimeException("Unable to create PlayerCard due to aItsCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Player getItsCard()
  {
    return itsCard;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setItsCard(Player aNewItsCard)
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
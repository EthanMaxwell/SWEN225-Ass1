/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/



// line 39 "model.ump"
// line 157 "model.ump"
public class WeaponCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeaponCard Associations
  private Weapon itsCard;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WeaponCard(Weapon aItsCard)
  {
    super();
    if (!setItsCard(aItsCard))
    {
      throw new RuntimeException("Unable to create WeaponCard due to aItsCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Weapon getItsCard()
  {
    return itsCard;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setItsCard(Weapon aNewItsCard)
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
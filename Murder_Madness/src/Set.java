/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/



// line 66 "model.ump"
// line 188 "model.ump"
public class Set
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Set Associations
  private RoomCard roomCard;
  private WeaponCard weaponCard;
  private PlayerCard playerCard;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Set(RoomCard aRoomCard, WeaponCard aWeaponCard, PlayerCard aPlayerCard)
  {
    if (!setRoomCard(aRoomCard))
    {
      throw new RuntimeException("Unable to create Set due to aRoomCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setWeaponCard(aWeaponCard))
    {
      throw new RuntimeException("Unable to create Set due to aWeaponCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setPlayerCard(aPlayerCard))
    {
      throw new RuntimeException("Unable to create Set due to aPlayerCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public RoomCard getRoomCard()
  {
    return roomCard;
  }
  /* Code from template association_GetOne */
  public WeaponCard getWeaponCard()
  {
    return weaponCard;
  }
  /* Code from template association_GetOne */
  public PlayerCard getPlayerCard()
  {
    return playerCard;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRoomCard(RoomCard aNewRoomCard)
  {
    boolean wasSet = false;
    if (aNewRoomCard != null)
    {
      roomCard = aNewRoomCard;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setWeaponCard(WeaponCard aNewWeaponCard)
  {
    boolean wasSet = false;
    if (aNewWeaponCard != null)
    {
      weaponCard = aNewWeaponCard;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPlayerCard(PlayerCard aNewPlayerCard)
  {
    boolean wasSet = false;
    if (aNewPlayerCard != null)
    {
      playerCard = aNewPlayerCard;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    roomCard = null;
    weaponCard = null;
    playerCard = null;
  }

}
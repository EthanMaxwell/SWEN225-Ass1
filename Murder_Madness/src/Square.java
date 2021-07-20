/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/



// line 7 "model.ump"
// line 104 "model.ump"
public class Square
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Square Attributes
  private boolean accessible;

  //Square Associations
  private Room partOf;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Square(boolean aAccessible)
  {
    accessible = aAccessible;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAccessible(boolean aAccessible)
  {
    boolean wasSet = false;
    accessible = aAccessible;
    wasSet = true;
    return wasSet;
  }

  public boolean getAccessible()
  {
    return accessible;
  }
  /* Code from template association_GetOne */
  public Room getPartOf()
  {
    return partOf;
  }

  public boolean hasPartOf()
  {
    boolean has = partOf != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setPartOf(Room aNewPartOf)
  {
    boolean wasSet = false;
    partOf = aNewPartOf;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    partOf = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "accessible" + ":" + getAccessible()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "partOf = "+(getPartOf()!=null?Integer.toHexString(System.identityHashCode(getPartOf())):"null");
  }
}
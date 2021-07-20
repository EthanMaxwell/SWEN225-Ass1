/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/


import java.util.*;

// line 2 "model.ump"
// line 92 "model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private List<Square> gameSquares;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board()
  {
    gameSquares = new ArrayList<Square>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Square getGameSquare(int index)
  {
    Square aGameSquare = gameSquares.get(index);
    return aGameSquare;
  }

  public List<Square> getGameSquares()
  {
    List<Square> newGameSquares = Collections.unmodifiableList(gameSquares);
    return newGameSquares;
  }

  public int numberOfGameSquares()
  {
    int number = gameSquares.size();
    return number;
  }

  public boolean hasGameSquares()
  {
    boolean has = gameSquares.size() > 0;
    return has;
  }

  public int indexOfGameSquare(Square aGameSquare)
  {
    int index = gameSquares.indexOf(aGameSquare);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameSquares()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addGameSquare(Square aGameSquare)
  {
    boolean wasAdded = false;
    if (gameSquares.contains(aGameSquare)) { return false; }
    gameSquares.add(aGameSquare);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameSquare(Square aGameSquare)
  {
    boolean wasRemoved = false;
    if (gameSquares.contains(aGameSquare))
    {
      gameSquares.remove(aGameSquare);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameSquareAt(Square aGameSquare, int index)
  {  
    boolean wasAdded = false;
    if(addGameSquare(aGameSquare))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameSquares()) { index = numberOfGameSquares() - 1; }
      gameSquares.remove(aGameSquare);
      gameSquares.add(index, aGameSquare);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameSquareAt(Square aGameSquare, int index)
  {
    boolean wasAdded = false;
    if(gameSquares.contains(aGameSquare))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameSquares()) { index = numberOfGameSquares() - 1; }
      gameSquares.remove(aGameSquare);
      gameSquares.add(index, aGameSquare);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameSquareAt(aGameSquare, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    gameSquares.clear();
  }

}
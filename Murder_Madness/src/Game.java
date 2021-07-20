/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/


import java.util.*;

// line 57 "model.ump"
// line 177 "model.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Associations
  private Board board;
  private List<Player> players;
  private Solution solution;
  private Accusation accusation;
  private Suggestion suggestion;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(Board aBoard, Solution aSolution, Accusation aAccusation, Suggestion aSuggestion)
  {
    if (!setBoard(aBoard))
    {
      throw new RuntimeException("Unable to create Game due to aBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    players = new ArrayList<Player>();
    if (!setSolution(aSolution))
    {
      throw new RuntimeException("Unable to create Game due to aSolution. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setAccusation(aAccusation))
    {
      throw new RuntimeException("Unable to create Game due to aAccusation. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSuggestion(aSuggestion))
    {
      throw new RuntimeException("Unable to create Game due to aSuggestion. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public Solution getSolution()
  {
    return solution;
  }
  /* Code from template association_GetOne */
  public Accusation getAccusation()
  {
    return accusation;
  }
  /* Code from template association_GetOne */
  public Suggestion getSuggestion()
  {
    return suggestion;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setBoard(Board aNewBoard)
  {
    boolean wasSet = false;
    if (aNewBoard != null)
    {
      board = aNewBoard;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    players.add(aPlayer);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    if (players.contains(aPlayer))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSolution(Solution aNewSolution)
  {
    boolean wasSet = false;
    if (aNewSolution != null)
    {
      solution = aNewSolution;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setAccusation(Accusation aNewAccusation)
  {
    boolean wasSet = false;
    if (aNewAccusation != null)
    {
      accusation = aNewAccusation;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSuggestion(Suggestion aNewSuggestion)
  {
    boolean wasSet = false;
    if (aNewSuggestion != null)
    {
      suggestion = aNewSuggestion;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    board = null;
    players.clear();
    solution = null;
    accusation = null;
    suggestion = null;
  }

}
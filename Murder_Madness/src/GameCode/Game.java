package GameCode;

import GUI.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Repents a entire game of Murder Mystery. Can be run with main directly to
 * create a new game.
 * 
 * @author Runtime Terror
 */
public class Game extends Observable {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Game Associations
	private Board board;
	private List<Character> characters;
	private List<Room> rooms;
	private List<Weapon> weapons;
	private CardTriplet solution = new CardTriplet();
	private CardTriplet currentGuess;
	private int playerNum; // Number of people playing the game
	private int playerTurn; // Number of the player currently taking their turn
	private int currentRefuter; // Number of the current player refuting
	private Map<Character, Card> refuteCards = new HashMap<>();
	private int diceOne;
	private int diceTwo;

	// Game State Machines
	public enum GameState {
		SelectPlayerNumber, RollDice, AskToStay, MovePlayer, AskGuessOrSolve, MakingGuess, MakingSolve, PlayerOut,
		GameOver, ShowRefute
	}

	public enum GameStateMakingGuess {
		Null, SelectingWeapon, SelectingPlayer, Refuting
	}

	public enum GameStateMakingSolve {
		Null, SelectingWeapon, SelectingPlayer
	}

	private GameState gameState = GameState.SelectPlayerNumber;
	private GameStateMakingGuess gameStateMakingGuess = null;
	private GameStateMakingSolve gameStateMakingSolve = null;

	/**
	 * Create a new game object, thus starting the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game(new GUI());
	}

	public Game(Observer gui) {
		addObserver(gui);
		update();
	}

	/**
	 * Called to show that the view should be updated
	 */
	private void update() {
		setChanged();
		notifyObservers();
	}

	/**
	 * For state machine. Setup the game board as player number was selected
	 *
	 * @param playerNum The number of players to setup the game for
	 * @return If the state change could be applied
	 */
	public boolean setupBoard(int playerNum) {
		if (gameState == GameState.SelectPlayerNumber || playerNum < 3 || playerNum > 4) {
			this.playerNum = playerNum;

			createRooms();

			createBoard();

			createCharacters();

			createWeapons();

			createCards();

			playerTurn = (int) (Math.random() * playerNum);
			setGameState(GameState.RollDice);
			System.out.println("Game Start");
			update();
			return true;
		}
		return false;
	}

	/**
	 * Create all the rooms
	 */
	private void createRooms() {
		rooms = new ArrayList<Room>();
		rooms.add(new Room("Haunted House"));
		rooms.add(new Room("Manic Manor"));
		rooms.add(new Room("Villa Celia"));
		rooms.add(new Room("Calamity Castle"));
		rooms.add(new Room("Peril Palace"));
	}

	/**
	 * Create the game board and set all information about each square like
	 * accessibility and what room it's part of
	 */
	private void createBoard() {
		board = new Board();
		// CardTriplet the squares that are parts of rooms as such
		for (int y = 0; y < board.boardSize(); y++) {
			for (int x = 0; x < board.boardSize(); x++) {
				// Haunted House
				if (x >= 2 && x <= 6 && y >= 2 && y <= 6) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(0));
					rooms.get(0).addSquare(square);
					square.setAccessible(false);
				}
				// Manic Manor
				else if (x >= 17 && x <= 21 && y >= 2 && y <= 6) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(1));
					rooms.get(1).addSquare(square);
					;
					square.setAccessible(false);
				}
				// Villa Celia
				else if (x >= 9 && x <= 14 && y >= 10 && y <= 13) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(2));
					rooms.get(2).addSquare(square);
					;
					square.setAccessible(false);
				}
				// Calamity Castle
				else if (x >= 2 && x <= 6 && y >= 17 && y <= 21) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(3));
					rooms.get(3).addSquare(square);
					;
					square.setAccessible(false);
				}
				// Peril Palace
				else if (x >= 17 && x <= 21 && y >= 17 && y <= 21) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(4));
					rooms.get(4).addSquare(square);
					;
					square.setAccessible(false);
				}
				// None move-on-to-able squares
				else if (x >= 5 && x <= 6 && y >= 11 && y <= 12) {
					Square square = board.getGameSquare(x, y);
					square.setAccessible(false);
				} else if (x >= 11 && x <= 12 && y >= 5 && y <= 6) {
					Square square = board.getGameSquare(x, y);
					square.setAccessible(false);
				} else if (x >= 17 && x <= 18 && y >= 11 && y <= 12) {
					Square square = board.getGameSquare(x, y);
					square.setAccessible(false);
				} else if (x >= 11 && x <= 12 && y >= 17 && y <= 18) {
					Square square = board.getGameSquare(x, y);
					square.setAccessible(false);
				}
			}
		}
		setupDoors();
	}

	/**
	 * Create the doorways to each of the rooms
	 */
	private void setupDoors() {
		// Setup doors
		// HH
		board.getGameSquare(6, 3).setAccessible(true);
		board.getGameSquare(5, 6).setAccessible(true);
		// MM
		board.getGameSquare(17, 5).setAccessible(true);
		board.getGameSquare(20, 6).setAccessible(true);
		// VC
		board.getGameSquare(12, 10).setAccessible(true);
		board.getGameSquare(14, 11).setAccessible(true);
		board.getGameSquare(9, 12).setAccessible(true);
		board.getGameSquare(11, 13).setAccessible(true);
		// CC
		board.getGameSquare(3, 17).setAccessible(true);
		board.getGameSquare(6, 18).setAccessible(true);
		// PP
		board.getGameSquare(18, 17).setAccessible(true);
		board.getGameSquare(17, 20).setAccessible(true);
	}

	/**
	 * Create all the game characters and set their starting positions
	 */
	private void createCharacters() {
		characters = new ArrayList<Character>();
		if (playerNum == 4 || playerNum == 3) {
			characters.add(new Character("Lucilla", board.getGameSquare(11, 1)));
			characters.add(new Character("Bert", board.getGameSquare(1, 9)));
			characters.add(new Character("Malina", board.getGameSquare(9, 22)));
			characters.add(new Character("Percy", board.getGameSquare(22, 14)));

		} else {
			throw new RuntimeException("Unable to create Board");
		}
	}

	/**
	 * Create all the game weapons and put them in random rooms
	 */
	private void createWeapons() {
		weapons = new ArrayList<Weapon>();
		// Make copy of the weapons list in a random order so they are placed randomly
		List<Room> randomOrder = new ArrayList<>(rooms);
		Collections.shuffle(randomOrder);
		weapons.add(new Weapon("Broom", randomOrder.get(0)));
		weapons.add(new Weapon("Scissors", randomOrder.get(1)));
		weapons.add(new Weapon("Knife", randomOrder.get(2)));
		weapons.add(new Weapon("Shovel", randomOrder.get(3)));
		weapons.add(new Weapon("iPad", randomOrder.get(4)));
	}

	/**
	 * Create all the cards, set the game solution then deal out the cards to
	 * players
	 */
	private void createCards() {
		List<RoomCard> roomCards = new ArrayList<RoomCard>();
		// Form the various cards required
		for (Room room : rooms) {
			roomCards.add(new RoomCard(room));
		}
		List<CharacterCard> characterCards = new ArrayList<CharacterCard>();
		for (Character character : characters) {
			characterCards.add(new CharacterCard(character));
		}
		List<WeaponCard> weaponCards = new ArrayList<WeaponCard>();
		for (Weapon weapon : weapons) {
			weaponCards.add(new WeaponCard(weapon));
		}
		// Random order
		Collections.shuffle(roomCards);
		Collections.shuffle(characterCards);
		Collections.shuffle(weaponCards);
		// Make solution for bottom card of each list
		solution = new CardTriplet(roomCards.get(0), weaponCards.get(0), characterCards.get(0));
		// Remove the solution for the cards
		roomCards.remove(0);
		characterCards.remove(0);
		weaponCards.remove(0);

		// Shuffle all remaining card into one deck
		List<Card> allCards = new ArrayList<Card>();
		allCards.addAll(roomCards);
		allCards.addAll(characterCards);
		allCards.addAll(weaponCards);
		Collections.shuffle(allCards);
		// Deal cards to the players
		for (int i = 0; i < allCards.size(); i++) {
			// If less player then characters, some character get no cards
			characters.get(i % playerNum).addCard(allCards.get(i));
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	/**
	 * @return If all player are out and unable to take turns
	 */
	private boolean allPlayersOut() {
		for (int i = 0; i < playerNum; i++) {
			if (!characters.get(i).isOut()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get the locations that the player taking their turn can move to with the
	 * current dice roll
	 * 
	 * @return A set of the locations (Rooms or squares)
	 */
	public Set<Location> canMoveTo() {
		Set<Location> dests = new HashSet<>();

		// Setup fringe
		Queue<List<Square>> fringe = new LinkedList<>();
		fringe.add(Arrays.asList(takingTurn().getLocation())); // Starting location

		while (!fringe.isEmpty()) {
			List<Square> curPath = fringe.poll();

			// Extract the current end of the path
			Square cur = curPath.get(curPath.size() - 1);

			// Search all neighbours for more paths
			for (Square dest : getNeighbours(cur)) {
				// Can't visit a already visited square
				if (!curPath.contains(dest)) {
					// Create the new path
					List<Square> newPath = new ArrayList<>(curPath);
					newPath.add(dest);

					// If the square is part of a room the player to access that room
					if (dest.hasPartOf())
						dests.add(dest.getPartOf());

					// If the player has more moves add path to fringe
					if (newPath.size() < diceRoll() + 1)
						fringe.add(newPath);

					// If out of moves and not in a room record destination
					if (!dest.hasPartOf() && newPath.size() >= diceRoll() + 1)
						dests.add(dest);
				}
			}
		}

		dests.remove(takingTurn().getInRoom());

		return dests;
	}

	/**
	 * Finds all the accessible neighbours of the given square
	 * 
	 * @param square The square to find the neighbours for
	 * @return The set of neighbours located
	 */
	private Set<Square> getNeighbours(Square square) {
		Set<Square> neighbours = new HashSet<>();
		// If the square is outside a room check bordering squares
		if (!square.hasPartOf()) {
			int index = board.indexOfGameSquare(square);
			// Left
			if (board.isGameSquare(board.getX(index) - 1, board.getY(index)))
				neighbours.add(board.getGameSquare(index - 1));
			// Right
			if (board.isGameSquare(board.getX(index) + 1, board.getY(index)))
				neighbours.add(board.getGameSquare(index + 1));
			// Above
			if (board.isGameSquare(board.getX(index), board.getY(index) - 1))
				neighbours.add(board.getGameSquare(index - board.boardSize()));
			// Below
			if (board.isGameSquare(board.getX(index), board.getY(index) + 1))
				neighbours.add(board.getGameSquare(index + board.boardSize()));

		} else {
			for (Square s : square.getPartOf().getSquares()) {
				if (s.isAccessible()) {
					Room placeHold = s.getPartOf();
					s.setPartOf(null);
					neighbours.addAll(getNeighbours(s));
					s.setPartOf(placeHold);
				}
			}
		}
		return neighbours;
	}

	/**
	 * Move a given player to a given room
	 * 
	 * @param character    Character to move
	 * @param roomToMoveTo Room to move them too
	 */
	public void moveToRoom(Character character, Room roomToMoveTo) {
		character.setLocation(roomToMoveTo.getSquares().get(getRoomPos(character, roomToMoveTo)));
	}

	/**
	 * Get the position in a room to move a certain character to. Don't use me, use
	 * moveToRoom(Character, Room) instead to move characters.
	 * 
	 * @param c Character to find location for
	 * @param r Room to find location for
	 * @return The position in the room identified
	 */
	private int getRoomPos(Character c, Room r) {
		int charIndex = characters.indexOf(c);
		if (r.getName().equals("Villa Celia")) {
			return 7 + charIndex;
		}
		if (charIndex == 0) {
			return 7;
		}
		return 10 + charIndex;

	}

	/**
	 * Make a list of strings of numbers from 1 to the given number
	 * 
	 * @param end The number to cut up to (included)
	 * @return The list of string for the numbers
	 */
	private List<String> makeSequence(int end) {
		List<String> ret = new ArrayList<>(end);
		for (int i = 1; i <= end; i++) {
			ret.add(i + "");
		}
		return ret;
	}

	/**
	 * Set the game state to the given state. Never directly change the game state
	 * instead call this as it runs all ENTERY code of all states!
	 * 
	 * @param aGameState Game state to change to
	 */
	private void setGameState(GameState aGameState) {
		gameState = aGameState;

		// entry actions and do activities
		switch (gameState) {
		case RollDice:
			playerTurn++;
			playerTurn = playerTurn % playerNum;
			break;
		case MakingGuess:
			gameStateMakingGuess = GameStateMakingGuess.SelectingWeapon;
			// Reset the refuting data
			currentRefuter = (playerTurn + 1) % playerNum;
			refuteCards.clear();
			break;
		case MakingSolve:
			gameStateMakingSolve = GameStateMakingSolve.SelectingWeapon;
			break;
		}
	}

	/**
	 * @return The current game state
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * @return The current state of making a guess
	 */
	public GameStateMakingGuess getGameStateMakingGuess() {
		return gameStateMakingGuess;
	}

	/**
	 * For that state machine. Roll the dice if in correct game state
	 * 
	 * @return If the state change could be applied
	 */
	public boolean rollDice() {
		if (gameState == GameState.RollDice) {
			diceOne = (int) (Math.random() * 6) + 1;
			diceTwo = (int) (Math.random() * 6) + 1;
			if (takingTurn().isInRoom()) {
				setGameState(GameState.AskToStay);
			} else {
				setGameState(GameState.MovePlayer);
			}
			update();
			return true;
		}
		return false;
	}

	/**
	 * For that state machine. Called after the the user is asked if they want to
	 * stay in their current room or move
	 * 
	 * @param staying If the player is staying
	 * @return If the state change could be applied
	 */
	public boolean askToStay(boolean staying) {
		if (gameState == GameState.AskToStay) {
			if (staying) {
				setGameState(GameState.AskGuessOrSolve);
			} else {
				setGameState(GameState.MovePlayer);
			}
			update();
			return true;
		}
		return false;
	}

	/**
	 * For that state machine. Called after the the user is asked if they want to
	 * make a guess or solve attempt
	 * 
	 * @param guess If they want to guess (or solve)
	 * @return If the state change could be applied
	 */
	public boolean askGuessOrSolve(boolean guess) {
		if (gameState == GameState.AskGuessOrSolve) {
			if (guess) {
				setGameState(GameState.MakingGuess);
			} else {
				setGameState(GameState.MakingSolve);
			}
			update();
			return true;
		}
		return false;
	}

	/**
	 * For that state machine. Move the player currently taking their turn to the
	 * given location
	 * 
	 * @param newLoc Location (Room or Square) to move them to
	 * @return If the state change could be applied
	 */
	public boolean MovePlayer(Location newLoc) {
		if (gameState == GameState.MovePlayer) {
			if (newLoc instanceof Square) {
				takingTurn().setLocation((Square) newLoc);
				setGameState(GameState.RollDice);
			}
			if (newLoc instanceof Room) {
				moveToRoom(takingTurn(), (Room) newLoc);
				setGameState(GameState.AskGuessOrSolve);
			}
			update();
			return true;
		}
		return false;
	}

	/**
	 * For that state machine. Called when the player has selected their weapon when
	 * solving OR guessing
	 * 
	 * @param w The weapon they chose
	 * @return If the state change could be applied
	 */
	public boolean weaponSelected(Weapon w) {
		if (gameState == GameState.MakingGuess || gameState == GameState.MakingSolve) {
			currentGuess.setWeapon(w);
			if (gameState == GameState.MakingGuess) {
				gameStateMakingGuess = GameStateMakingGuess.SelectingPlayer;
			} else {
				gameStateMakingSolve = GameStateMakingSolve.SelectingPlayer;
			}
			update();
			return true;
		}
		return false;
	}

	/**
	 * For that state machine. Called when the player has selected their character
	 * when solving OR guessing
	 * 
	 * @param c The character they chose
	 * @return If the state change could be applied
	 */
	public boolean playerSelected(Character c) {
		if (gameState == GameState.MakingGuess || gameState == GameState.MakingSolve) {
			currentGuess.setCharacter(c);
			if (gameState == GameState.MakingGuess) {
				gameStateMakingGuess = GameStateMakingGuess.Refuting;
			} else {
				currentGuess.setRoom(takingTurn().getInRoom());
				if (currentGuess.equals(solution)) {
					setGameState(GameState.GameOver);
				} else {
					setGameState(GameState.PlayerOut);
				}
			}
			update();
			return true;
		}
		return false;
	}

	/**
	 * For that state machine. Called when the player as selected "ok" if the "you
	 * are out" view
	 * 
	 * @return If the state change could be applied
	 */
	public boolean acceptedOut() {
		if (gameState == GameState.PlayerOut) {
			takingTurn().setOut();
			setGameState(GameState.RollDice);
			update();
			return true;
		}
		return false;
	}

	/**
	 * For that state machine. Set the card to be show by the currently refuting
	 * player to this
	 * 
	 * @param cardTheyRefuted
	 * @return If the state change could be applied
	 */
	public boolean refuting(Card cardTheyRefuted) {
		if (gameState == GameState.MakingGuess) {
			// Check if the card is in their hand
			if(!characters.get(currentRefuter).getHand().contains(cardTheyRefuted)){
				return false;
			}
			
			refuteCards.put(characters.get(currentRefuter), cardTheyRefuted);
			// Move to next refuter
			currentRefuter = (currentRefuter + 1) % playerNum;
			// All refutes done, exit state
			if (currentRefuter == playerTurn) {
				setGameState(GameState.ShowRefute);
			}
			update();
			return true;
		}
		return false;
	}

	/**
	 * For that state machine Called when the player as selected "ok" if the "you
	 * were show the cards" view
	 * 
	 * @return If the state change could be applied
	 */
	public boolean acceptedRefute() {
		if (gameState == GameState.ShowRefute) {
			setGameState(GameState.RollDice);
			update();
			return true;
		}
		return false;
	}

	/**
	 * @return The player currently taking their turn
	 */
	public Character takingTurn() {
		return characters.get(playerTurn);
	}

	/**
	 * @return The current diceRoll
	 */
	public int diceRoll() {
		return diceOne + diceTwo;
	}
	
	/**
	 * @return The value roll on dice one
	 */
	public int getDiceOne() {
		return diceOne;
	}
	
	/**
	 * @return The value roll on dice two
	 */
	public int getDiceTwo() {
		return diceTwo;
	}
	
	/**
	 * @return All characters in the game
	 */
	public List<Character> getCharacters(){
		return Collections.unmodifiableList(characters);
	}

	/**
	 * @return The current state of making a solve
	 */
	public GameStateMakingSolve getGameStateMakingSolve() {
		return gameStateMakingSolve;
	}
}
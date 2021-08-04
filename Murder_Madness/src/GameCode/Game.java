package GameCode;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
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
	private CardTriplet solution;
	private int playerNum; // Number of people playing the game

	// Game State Machines
	public enum GameState {
		SelectPlayerNumber, RollDice, AskToStay, MovePlayer, AskGuessOrSolve, MakingGuess, MakingSolve, PlayerOut,
		GameWon
	}

	public enum GameStateMakingGuess {
		Null, SelectingWeapon, SelectingPlayer, Refuting
	}

	public enum GameStateMakingSolve {
		Null, SolveSelectingWeapon, SolveSelectingPlayer
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
		Game game = new Game();
	}

	public Game() {
		addObserver(new GUI());
		setChanged();
		notifyObservers();
	}

	/**
	 * Setup the game board
	 */
	public void setupBoard(int playerNum) {
		this.playerNum = playerNum;
		
		createRooms();

		createBoard();

		createCharacters();

		createWeapons();

		createCards();
		
		gameState = GameState.RollDice;
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
	 * Make the game loop run
	 */
	private void playGame() {
		boolean gameOver = false;
		// Run game starting from a random player until it's gameOver
		for (int turnNum = (int) (Math.random() * playerNum); !gameOver; turnNum++) {
			// Check that at least one player can still take turns
			if (allPlayersOut()) {
				gameOver = true;
				System.out.println("Everybody failed their solve attempts so everybody loses!!!\nThe solution was:");
				solution.print();
			}

			Character takingTurn = characters.get(turnNum % playerNum);

			// If the player is out skip their turn
			if (takingTurn.isOut())
				continue;

			// System.out.println("\nIt's now player " + (turnNum % playerNum + 1) + " (" +
			// takingTurn.getName() + ") turn");
			askForPlayer(turnNum % playerNum);

			board.printBoard(characters);
			takingTurn.printHand();

			// Roll the two dice
			int diceRoll = (int) (Math.random() * 6) + (int) (Math.random() * 6) + 2;
			System.out.println("You rolled a " + diceRoll + "!");

			// If in a room ask if they want to move or stay
			if (!takingTurn.isInRoom() || restrictedAsk(
					"Would you like to stay the " + takingTurn.getInRoom().getName() + "?\n 1 - Stay	2 - Move",
					"Error - please enter 1 or 2", Arrays.asList("1", "2")).equals("2")) {
				movePlayer(takingTurn, diceRoll);
			}

			// Check if player is in a room so can make a guess or solve
			if (takingTurn.isInRoom()) {
				if (restrictedAsk("Would you like make a guess or attempt to solve?\n 1 - Guess	2 - Solve",
						"Error - please enter 1 or 2", Arrays.asList("1", "2")).equals("1")) {
					makeGuess(takingTurn);
				} else {
					if (trySolve(takingTurn)) {
						System.out.println("Player " + (turnNum % playerNum) + " (" + takingTurn.getName()
								+ ") Wins! The solution was:");
						solution.print();
						gameOver = true;
					} else {
						System.out.println("WRONG! You are out");
						takingTurn.setOut();
						gameOver = false;
					}
				}
			}
		}
	}

	/**
	 * Ask input from the users but the input is restricted values
	 * 
	 * @param question
	 * @param errorMsg
	 * @param restrictedValues
	 * @return The validated input the user entered in lower case
	 */

	public String restrictedAsk(String question, String errorMsg, List<String> restrictedValues) {
		boolean found = false;
		String result = "";
		final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while (!found) {
			System.out.print(question + "\n>");
			try {
				result = input.readLine().toLowerCase();
			} catch (IOException e) {
				System.err.println("I/O Error - " + e.getMessage());
			}

			for (String i : restrictedValues) {
				if (result.equals(i.toLowerCase())) {
					found = true;
				}
			}
			if (!found) {
				System.out.println(errorMsg);
				System.out.println("Valid Inputs : " + restrictedValues);
			}
		}

		return result;

	}

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
	 * Ask for a player (by position in array) to be give the device
	 * 
	 * @param number Number of player to ask for
	 */
	private void askForPlayer(int number) {
		System.out.println(System.lineSeparator().repeat(100000));
		System.out.print("Please hand the device to player " + (number + 1) + " (" + characters.get(number).getName()
				+ ") and press enter when ready.");
		waitForEnter();
	}

	/**
	 * Wait of the enter key to be pressed
	 */
	private void waitForEnter() {
		final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		try {
			input.readLine();
		} catch (IOException e) {
			System.err.println("I/O Error - " + e.getMessage());
		}
	}

	/**
	 * Allows the given player to move by the given dice roll
	 * 
	 * @param player   Player that is allowed to move
	 * @param diceRoll The dice roll to show max move distance
	 */
	private void movePlayer(Character player, int diceRoll) {
		// To store destinations
		Set<Square> dests = new HashSet<>();
		Set<Room> roomDests = new HashSet<>();

		// Setup fringe
		Queue<List<Square>> fringe = new LinkedList<>();
		fringe.add(Arrays.asList(player.getLocation())); // Starting location

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
						roomDests.add(dest.getPartOf());

					// If the player has more moves add path to fringe
					if (newPath.size() < diceRoll + 1)
						fringe.add(newPath);

					// If out of moves and not in a room record destination
					if (!dest.hasPartOf() && newPath.size() >= diceRoll + 1)
						dests.add(dest);
				}
			}
		}

		roomDests.remove(player.getInRoom());

		// Show options
		System.out.println("Avaliable squares : " + dests);
		System.out.println("Avaliable rooms : " + roomDests);

		String responce = restrictedAsk("Where would you like to move too?", "Please enter a avaliable square or room.",
				Stream.concat(dests.stream().map(i -> i.getPosition()), roomDests.stream().map(i -> i.getName()))
						.collect(Collectors.toList()));

		// Get the square form the user response
		Square newLoc = board.gotFromCode(responce);

		// If a square could be located from the response move to it
		if (newLoc != null) {
			player.setLocation(newLoc);
		}
		// If there was no square, then user entry must have been a room.
		else {
			for (Room r : rooms) {
				if (r.getName().toLowerCase().equals(responce.toLowerCase())) {
					// Room found, move player to it
					moveToRoom(player, r);
					break;
				}
			}
		}
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
	 * Given player makes a guess
	 * 
	 * @param makingGuess Player making the guess
	 */
	public void makeGuess(Character makingGuess) {
		Room inRoom = makingGuess.getInRoom();
		CardTriplet guessToRefute = askForGuess(inRoom);

		this.moveToRoom(guessToRefute.getCharacter(), inRoom);
		guessToRefute.getWeapon().setLocation(inRoom);

		int curPLayerNum = characters.indexOf(makingGuess);
		Card[] toSee = new Card[playerNum - 1];
		for (int i = 1; i < playerNum; i++) {
			toSee[i - 1] = refute(characters.get((curPLayerNum + i) % playerNum), guessToRefute);
		}

		askForPlayer(characters.indexOf(makingGuess));
		System.out.println("You got shown:");
		for (int i = 0; i < toSee.length; i++) {
			int showNum = (curPLayerNum + i + 1) % playerNum;
			System.out.println("Player " + (showNum + 1) + " (" + characters.get(showNum).getName() + ") showed you : "
					+ (toSee[i] != null ? toSee[i] : "Nothing"));
		}
		System.out.println("Press enter when done");
		waitForEnter();
	}

	/**
	 * Given player attempts to solve the murder!
	 * 
	 * @param makingSolve Who is try to solve
	 * @return If they were successful
	 */
	public boolean trySolve(Character makingSolve) {
		Room inRoom = makingSolve.getInRoom();
		CardTriplet guessedSolution = askForGuess(makingSolve.getInRoom());

		guessedSolution.getWeapon().setLocation(inRoom);
		this.moveToRoom(guessedSolution.getCharacter(), inRoom);

		// Returning from the method once solution is checked
		if (guessedSolution.equals(solution)) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * Forces the given player to refute the given card triplet
	 * 
	 * @param refuter  Person who must refute
	 * @param toRefute CardTriplet to refute
	 * @return The card they wish to show (null if no card to show)
	 */
	public Card refute(Character refuter, CardTriplet toRefute) {
		askForPlayer(characters.indexOf(refuter));
		System.out.println("The cards to refute are: " + toRefute.getRoom().getName() + ", "
				+ toRefute.getWeapon().getName() + ", " + toRefute.getCharacter().getName());

		refuter.printHand();

		List<Card> canRefute = toRefute.contains(refuter.getHand());
		if (canRefute.size() == 0) {
			System.out.println("You have no cards to show. Press enter when done.");
			waitForEnter();
			return null;
		}
		if (canRefute.size() == 1) {
			System.out.println("You must show your " + canRefute.get(0).getName() + " card. Press enter when done.");
			waitForEnter();
			return canRefute.get(0);
		}
		Card showing = canRefute
				.get(Integer
						.parseInt(restrictedAsk(
								"Please select the card to show:\n"
										+ (canRefute.stream().map(c -> canRefute.indexOf(c) + 1 + " - " + c.getName())
												.reduce((a, b) -> a + ", " + b)).get(),
								"Error - Ender a number the corresponds to a card", makeSequence(canRefute.size())))
						- 1);
		System.out.println("You will show your " + showing.getName() + " card. Press enter when done.");
		waitForEnter();
		return showing;
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
	 * Create a new guess from questions asked to the current player
	 * 
	 * @param roomToUse Room to use in guess
	 * @return The created CardTriplet of the guess
	 */
	private CardTriplet askForGuess(Room roomToUse) {
		int WeaponGuess = Integer
				.parseInt(restrictedAsk("Press: 1 - Broom, 2 - Scissors, 3 - Knife, 4 - Shovel, 5 - iPad",
						"Error - please enter values 1 to 5 for respective weapons", makeSequence(5)));
		Weapon guessedWeapon = weapons.get(WeaponGuess - 1);

		int CharacterGuess = Integer.parseInt(restrictedAsk("Press: 1 - Lucilla, 2 - Bert, 3 - Maline, 4 - Percy",
				"Error - please enter values 1 to 4 for respective characters", makeSequence(4)));
		Character guessedCharacter = characters.get(CharacterGuess - 1);

		// making required objects to check if the attempt was successful
		return new CardTriplet(roomToUse, guessedWeapon, guessedCharacter);
	}

	/**
	 * @param aCharacter The character to search for
	 * @return The position of a character in the turns order
	 */
	public int indexOfCharacter(Character aCharacter) {
		int index = characters.indexOf(aCharacter);
		return index;
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
	 * @return The current state of making a solve
	 */
	public GameStateMakingSolve getGameStateMakingSolve() {
		return gameStateMakingSolve;
	}

	protected void redraw(Graphics g) {
		// TODO Auto-generated method stub

	}

	protected void onClick(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	protected void onDrag(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
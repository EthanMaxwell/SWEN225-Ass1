import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 
 * @author Runtime Terror
 */
public class Game {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Game Associations
	private Board board;
	private List<Character> characters;
	private List<Room> rooms;
	private List<Weapon> weapons;
	private Solution solution;
	private Accusation accusation;
	private Suggestion suggestion;
	private int playerNum; // Number of people playing the game

	/**
	 * Create a new game object, thus starting the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
	}

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	/**
	 * Create a new game object, setup all object require to run game so they are
	 * ready to go and then start it up
	 */
	public Game() {
		System.out.println("Welcome to a game of Murder Madness!");
		playerNum = Integer.parseInt(
				restrictedAsk("How many players? (3 or 4) ", "Error - please enter 3 or 4", Arrays.asList("3", "4")));

		createRooms();

		createBoard();

		createCharacters();

		createWeapons();

		createCards();

		System.out.println("All setup :^)");

		board.printBoard();

		playGame();
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
		// Set the squares that are parts of rooms as such
		for (int x = 0; x < board.boardSize(); x++) {
			for (int y = 0; y < board.boardSize(); y++) {
				// Haunted House
				if (x >= 2 && x <= 6 && y >= 2 && y <= 6) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(0));
					square.setAccessible(false);
					square.setCharacter(" H "); // To be print for testing
				}
				// Manic Manor
				else if (x >= 17 && x <= 21 && y >= 2 && y <= 6) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(1));
					square.setAccessible(false);
					square.setCharacter(" M "); // To be print for testing
				}
				// Villa Celia
				else if (x >= 9 && x <= 14 && y >= 10 && y <= 13) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(2));
					square.setAccessible(false);
					square.setCharacter(" V "); // To be print for testing
				}
				// Calamity Castle
				else if (x >= 2 && x <= 6 && y >= 17 && y <= 21) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(3));
					square.setAccessible(false);
					square.setCharacter(" C "); // To be print for testing
				}
				// Peril Palace
				else if (x >= 17 && x <= 21 && y >= 17 && y <= 21) {
					Square square = board.getGameSquare(x, y);
					square.setPartOf(rooms.get(4));
					square.setAccessible(false);
					square.setCharacter(" P "); // To be print for testing
				} else if (x >= 5 && x <= 6 && y >= 11 && y <= 12) {
					Square square = board.getGameSquare(x, y);
					square.setAccessible(false);
					square.setCharacter(" W "); // To be print for testing
				} else if (x >= 11 && x <= 12 && y >= 5 && y <= 6) {
					Square square = board.getGameSquare(x, y);
					square.setAccessible(false);
					square.setCharacter(" W "); // To be print for testing
				} else if (x >= 17 && x <= 18 && y >= 11 && y <= 12) {
					Square square = board.getGameSquare(x, y);
					square.setAccessible(false);
					square.setCharacter(" W "); // To be print for testing
				} else if (x >= 11 && x <= 12 && y >= 17 && y <= 18) {
					Square square = board.getGameSquare(x, y);
					square.setAccessible(false);
					square.setCharacter(" W "); // To be print for testing
				}
			}
		}
	}

	/**
	 * Create all the game characters and set their starting positions
	 */
	private void createCharacters() {
		characters = new ArrayList<Character>();
		if (playerNum == 4 || playerNum == 3) {
			characters.add(new Character("Lucilla", board.getGameSquare(11, 1)));
			board.getGameSquare(11, 1).setCharacter("Lu|");
			characters.add(new Character("Bert", board.getGameSquare(1, 9)));
			board.getGameSquare(1, 9).setCharacter("Be|");
			characters.add(new Character("Malina", board.getGameSquare(22, 9)));
			board.getGameSquare(9, 22).setCharacter("Ma|");
			characters.add(new Character("Percy", board.getGameSquare(22, 14)));
			board.getGameSquare(22, 14).setCharacter("Pe|");

		} else {
			throw new RuntimeException(
					"Unable to create Board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
		solution = new Solution(roomCards.get(0), weaponCards.get(0), characterCards.get(0));
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
		boolean gameOver = false; // You can make this global if needed ;^)

		// Run game starting from a random player until it's gameOver
		for (int turnNum = (int) (Math.random() * playerNum); !gameOver; turnNum++) {
			// Check that at least one player can still take turns
			if (allPlayersOut()) {
				gameOver = true;
				System.out.println("Everybody failed their solve attempts so everybody loses!!!");
			}

			Character takingTurn = characters.get(turnNum % playerNum);

			// If the player is out skip their turn
			if (takingTurn.isOut())
				continue;

			System.out
					.println("\nIt's now player " + (turnNum % playerNum + 1) + " (" + takingTurn.getName() + ") turn");
			askForPlayer(turnNum % playerNum);

			// Roll the two dice
			int diceRoll = (int) (Math.random() * 6) + (int) (Math.random() * 6) + 2;
			System.out.println("You rolled a " + diceRoll + "!");

			// If in a room ask if they want to move or stay
			if (!takingTurn.isInRoom()
					|| restrictedAsk("Would you like to stay in your current room?\n 1 - Stay	2 - Move",
							"Error - please enter 1 or 2", Arrays.asList("1", "2")) == "1") {
				// movePlayer(); TODO write move player method
			}

			// Check if player is in a room so can make a guess or solve
			if (takingTurn.isInRoom()) {
				if (restrictedAsk("Would you like make a guess or attempt to solve?\n 1 - Guess	2 - Solve",
						"Error - please enter 1 or 2", Arrays.asList("1", "2")) == "1") {
					// makeGuess() TODO write the make guess method
				} else {
					// trySolve() TODO write the try solve method
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
	 * @return
	 */

	public String restrictedAsk(String question, String errorMsg, List<String> restrictedValues) {
		boolean found = false;
		String result = "";
		final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while (!found) {
			System.out.print(question + "\n>");
			try {
				result = input.readLine();
			} catch (IOException e) {
				System.err.println("I/O Error - " + e.getMessage());
			}

			for (String i : restrictedValues) {
				if (result.equals(i)) {
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
		final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Please hand the device to player " + (number + 1) + " (" + characters.get(number).getName()
				+ ") and press enter when ready.");
		try {
			input.readLine();
		} catch (IOException e) {
			System.err.println("I/O Error - " + e.getMessage());
		}
	}

	/**
	 * @param aCharacter The character to search for
	 * @return The position of a character in the turns order
	 */
	public int indexOfCharacter(Character aCharacter) {
		int index = characters.indexOf(aCharacter);
		return index;
	}
}
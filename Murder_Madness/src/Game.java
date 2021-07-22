import java.io.IOException;
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
		//playerNum = 4; // Just assume 4 players for now (change later)
		playerNum = Integer.parseInt(restrictedAsk("How many players? (3 or 4) ",
				"Error - please enter 3 to 6", new ArrayList<String>(Arrays.asList("3", "4"))));

		createRooms();

		createBoard();

		createCharacters();

		createWeapons();

		createCards();

		System.out.println("All setup :^)");

		board.printBoard();
		// TODO The game should start executing for here
	}


	/**
	 * Ask input from the users but the input is restricted values
	 * @param question
	 * @param errorMsg
	 * @param restrictedValues
	 * @return
	 */

	public String restrictedAsk(String question, String errorMsg, ArrayList<String> restrictedValues) {
		boolean found = false;
		String result = "";
		while (!found) {
			System.out.print(question);
			Scanner sc = new Scanner(System.in);
			result = sc.nextLine();
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
				}
			}
		}
	}

	/**
	 * Create all the game characters and set their starting positions
	 */
	private void createCharacters() {
		characters = new ArrayList<Character>();
		characters.add(new Character("Lucilla", board.getGameSquare(11, 1)));
		board.getGameSquare(11,1).setCharacter("Lu|");
		characters.add(new Character("Bert", board.getGameSquare(1, 9)));
		board.getGameSquare(1,9).setCharacter("Be|");
		characters.add(new Character("Malina", board.getGameSquare(22, 9)));
		board.getGameSquare(9,22).setCharacter("Ma|");
		characters.add(new Character("Percy", board.getGameSquare(22, 14)));
		board.getGameSquare(22,14).setCharacter("Pe|");
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
			// If less player then characters, some character get not cards
			characters.get(i % playerNum).addCard(allCards.get(i));
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	/* Code from template association_GetOne */
	public Board getBoard() {
		return board;
	}

	/* Code from template association_GetMany */
	public Character getCharacter(int index) {
		Character aCharacter = characters.get(index);
		return aCharacter;
	}

	public List<Character> getCharacters() {
		List<Character> newCharacters = Collections.unmodifiableList(characters);
		return newCharacters;
	}

	public int numberOfCharacters() {
		int number = characters.size();
		return number;
	}

	public boolean hasCharacters() {
		boolean has = characters.size() > 0;
		return has;
	}

	public int indexOfCharacter(Character aCharacter) {
		int index = characters.indexOf(aCharacter);
		return index;
	}

	/* Code from template association_GetOne */
	public Solution getSolution() {
		return solution;
	}

	/* Code from template association_GetOne */
	public Accusation getAccusation() {
		return accusation;
	}

	/* Code from template association_GetOne */
	public Suggestion getSuggestion() {
		return suggestion;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setBoard(Board aNewBoard) {
		boolean wasSet = false;
		if (aNewBoard != null) {
			board = aNewBoard;
			wasSet = true;
		}
		return wasSet;
	}

	/* Code from template association_AddUnidirectionalMany */
	public boolean addCharacter(Character aCharacter) {
		boolean wasAdded = false;
		if (characters.contains(aCharacter)) {
			return false;
		}
		characters.add(aCharacter);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeCharacter(Character aCharacter) {
		boolean wasRemoved = false;
		if (characters.contains(aCharacter)) {
			characters.remove(aCharacter);
			wasRemoved = true;
		}
		return wasRemoved;
	}


	/* Code from template association_SetUnidirectionalOne */
	public boolean setSolution(Solution aNewSolution) {
		boolean wasSet = false;
		if (aNewSolution != null) {
			solution = aNewSolution;
			wasSet = true;
		}
		return wasSet;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setAccusation(Accusation aNewAccusation) {
		boolean wasSet = false;
		if (aNewAccusation != null) {
			accusation = aNewAccusation;
			wasSet = true;
		}
		return wasSet;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setSuggestion(Suggestion aNewSuggestion) {
		boolean wasSet = false;
		if (aNewSuggestion != null) {
			suggestion = aNewSuggestion;
			wasSet = true;
		}
		return wasSet;
	}
}
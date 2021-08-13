package GUI;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.swing.*;

import java.util.*;

import GameCode.Game;
import GameCode.Game.GameState;
import GameCode.Room;
import GameCode.Square;

/**
 * TODO Write me
 */
public class GUI implements Observer {

	/**
	 * Game to draw next
	 */
	private Game view;

	private JFrame frame;

	private BoardPanel boardPanel;

	private ControlPanel controlPanel;

	private MenuBar menuBar;

	private Dimension ScreenDimension;
	private boolean setup = false;

	private final Font FONT = new Font("SansSerif", Font.BOLD, 20);

	// Buttons
	JButton roll;
	JButton guess;
	JButton solve;

	JLabel moveInfo;

	private Map<GameCode.Character, String> names = new HashMap<>();

	public GUI() {
		initialise();
	}

	/**
	 * Create default JFrame for the game
	 */
	private void initialise() {

		// Initialise ScreenDimension to the the size of the current window
		ScreenDimension = screenDimension();

		// Create the main frame for the game
		frame = new JFrame();
		frame.setTitle("Murder Madness - Team 22 (Runtime Terror)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);

		// Create the panel for the board
		boardPanel = new BoardPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawGame(g);
				drawControl(g);

			}
		};

		// Create the panel for the controller
		controlPanel = new ControlPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

			}
		};

		// Create MenuBar
		menuBar = new MenuBar();

		// Add MenuBar
		frame.setJMenuBar(menuBar);

		// Set GridBagLayout
		frame.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;

		// Make the width of boardPanel 70% the size of the screen
		gbc.weightx = 0.8;
		boardPanel.setOpaque(true);
		frame.add(boardPanel, gbc);

		// Make the width of controlPanel 30% the size of the screen
		gbc.weightx = 0;
		controlPanel.setOpaque(true);
		frame.add(controlPanel, gbc);

		frame.pack();
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Set frame dimension
		frame.setLocationByPlatform(true);

	}

	/**
	 * @return the dimension of the screen
	 */
	public Dimension screenDimension() {
		return new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
	}

	/**
	 * Draw out the game
	 * 
	 * @param g The graphic to use when drawing the game
	 */
	private void drawGame(Graphics g) {
		if (view != null) {
			g.drawOval(100, 100, 100, 100);
			drawBoard(g, view);
		}
	}

	/**
	 * Draw all controller related objects
	 * 
	 * @param g Graphics object to use when needed
	 */
	private void drawControl(Graphics g) {
		if (view != null && roll != null) {
			// Hide all object by default
			roll.setVisible(false);
			guess.setVisible(false);
			solve.setVisible(false);
			moveInfo.setVisible(false);

			switch (view.getGameState()) {
			// Show roll dice button if applicable
			case RollDice:
				roll.setVisible(true);
				break;

			// Show guess/solve buttons if applicable
			case AskGuessOrSolve:
				guess.setVisible(true);
				solve.setVisible(true);
				break;

			// Show player move instructions
			case MovePlayer:
				moveInfo.setVisible(true);
				break;

			// Show dialogue for player attempting to guess
			case MakingGuess:
				showGuessDialog();
				break;

			// Show dialogue for player attempting to solve
			case MakingSolve:
				showSolveDialog();
				break;

			// Ask for device to be handed on so next player can take their turn
			case AskForNext:
				JOptionPane.showMessageDialog(frame, "It is now " + view.getTakingTurn().getName() + " ("
						+ names.get(view.getTakingTurn()) + ") turn. Please hand the device to them then press ok.");
				view.nextPlayerReady();
				break;

			// Ask if the player would like to stay or move
			case AskToStay:
				askToStay();
				break;

			// Print out gameOver screen
			case GameOver:
				// Everybody is out and the game ends
				if (!view.hasWinner()) {
					JOptionPane.showMessageDialog(frame,
							"The game is over as everybody loses (seriously how did you all lose?)");
				}
				// A winner is to be crowned
				else {
					JOptionPane.showMessageDialog(frame,
							view.getTakingTurn().getName() + " (" + names.get(view.getTakingTurn())
									+ ") Wins! The solution was:/n" + view.getSolution().writeOut());
				}
				break;

			// Print player out message
			case PlayerOut:
				JOptionPane.showMessageDialog(frame, "Sadly our guess of " + view.getLastGuess()
						+ " wasn't correct. You are now out and will miss the rest of your turns.");
				view.acceptedOut();
				break;

			// Show the player
			case ShowRefute:
				JOptionPane.showMessageDialog(frame, " You got shown the cards:\n" + view.getShownCard());
				view.acceptedRefute();
				break;

			// If unsure do nothing
			default:
				break;
			}
		}
	}

	/**
	 * Ask current player if they want to say in their current room
	 */
	private void askToStay() {
		int n = JOptionPane.showOptionDialog(controlPanel, "Stay or more?",
				"Would you like to stay the " + view.getTakingTurn().getInRoom().getName() + " or move ?",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, view.getCharacters().toArray(),
				null);

		// Check the output was a valid one
		if (n == 0)
			view.askToStay(true);
		if (n == 1)
			view.askToStay(false);
		else
			// Failed, retry selection
			askToStay();
	}

	/**
	 * Show dialogue for the player making a guess
	 */
	private void showGuessDialog() {
		// Get the player to choose a weapon
		if (view.getGameStateMakingGuess() == Game.GameStateMakingGuess.SelectingWeapon) {
			// Show the option
			int n = JOptionPane.showOptionDialog(controlPanel, "You are making a guess", "Select a weapon to guess",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, view.getWeapons().toArray(),
					null);

			// Check the output was a valid one
			if (n >= 0)
				// Selected
				view.weaponSelected(view.getWeapons().get(n));
			else
				// Failed, retry selection
				showGuessDialog();
		}
		// Get player to choose a character
		else if (view.getGameStateMakingGuess() == Game.GameStateMakingGuess.SelectingPlayer) {
			int n = JOptionPane.showOptionDialog(controlPanel, "You are making a guess", "Select a character to guess",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					view.getCharacters().toArray(), null);

			// Check the output was a valid one
			if (n >= 0)
				// Selected
				view.characterSelected(view.getCharacters().get(n));
			else
				// Failed, retry selection
				showGuessDialog();
		}
		// Ask for the next refuter
		else if (view.getGameStateMakingGuess() == Game.GameStateMakingGuess.NextPlayer) {
			JOptionPane.showMessageDialog(frame, "Please hand a the device to " + view.getRefuting().getName() + " ("
					+ names.get(view.getRefuting()) + ") then press okay");
			view.refutingPlayerReady();
		}
		// Ask for card to refute with
		else if (view.getGameStateMakingGuess() == Game.GameStateMakingGuess.Refuting) {
			if (view.canRefute().size() == 0) {
				JOptionPane.showMessageDialog(frame,
						"You must refute : " + view.getLastGuess() + "/nYou have no cards to show.");
				view.refuting(null);
			}
			if (view.canRefute().size() == 1) {
				JOptionPane.showMessageDialog(frame, "You must refute : " + view.getLastGuess()
						+ "/nYou must show your " + view.canRefute().get(0).getName() + " card.");
				view.refuting(view.canRefute().get(0));
			}
			// They have multiple card to choose from so must select one
			else {
				// Make the refuter select a card
				int n = JOptionPane.showOptionDialog(controlPanel, "Refute a card",
						"Select a card to show to " + view.getTakingTurn().getName() + " ("
								+ names.get(view.getTakingTurn()) + ")",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						view.canRefute().toArray(), null);

				// Check the output was a valid one
				if (n >= 0)
					// Selected
					view.refuting(view.canRefute().get(n));
				else
					// Failed, retry selection
					showGuessDialog();
			}
		}
	}

	/**
	 * Show dialogue for the player making a solve attempt
	 */
	private void showSolveDialog() {
		// Get player to select a weapon
		if (view.getGameStateMakingSolve() == Game.GameStateMakingSolve.SelectingWeapon) {
			int n = JOptionPane.showOptionDialog(controlPanel, "You are attempting to solve the murder",
					"Select the murder weapon", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					view.getWeapons().toArray(), null);

			// Check the output was a valid one
			if (n >= 0)
				// Selected
				view.weaponSelected(view.getWeapons().get(n));
			else
				// Failed, retry selection
				showSolveDialog();
		}
		// Get player to chose a character
		else if (view.getGameStateMakingSolve() == Game.GameStateMakingSolve.SelectingPlayer) {
			int n = JOptionPane.showOptionDialog(controlPanel, "You are attempting to solve the murder",
					"Select the murderer", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					view.getCharacters().toArray(), null);

			// Check the output was a valid one
			if (n >= 0)
				// Selected
				view.characterSelected(view.getCharacters().get(n));
			else
				// Failed, retry selection
				showSolveDialog();
		}
	}

	private void drawBoard(Graphics g, Game game) {
		int boardWidth = boardPanel.getWidth();
		int boardHeight = boardPanel.getHeight();
		int squareSize;

		if (boardWidth < boardHeight)
			squareSize = boardWidth / 24;
		else
			squareSize = boardHeight / 24;

		for (int row = 0; row < 24; row++) {
			for (int col = 0; col < 24; col++) {

				int x = col * squareSize;
				int y = row * squareSize;

				Square square = view.getBoard().getGameSquare(col, row);
				g.setColor(Color.white);
				if (square.getPartOf() instanceof Room) {
					// Haunted House
					if (square.getPartOf().toString().equals("Haunted House")) {
						g.setColor(Color.blue);
					}
					// Manic Manor
					else if (square.getPartOf().toString().equals("Manic Manor")) {
						g.setColor(Color.magenta);
					}
					// Villa Celia
					else if (square.getPartOf().toString().equals("Villa Celia")) {
						g.setColor(Color.green);
					}
					// Calamity Castle
					else if (square.getPartOf().toString().equals("Calamity Castle")) {
						g.setColor(Color.pink);
					}
					// Peril Palace
					else if (square.getPartOf().toString().equals("Peril Palace")) {
						g.setColor(Color.red);
					}

				} else if (!square.isAccessible()) {
					g.setColor(Color.black);
				}

				g.fillRect(x, y, squareSize, squareSize);
				g.setColor(Color.gray);
				g.drawRect(x, y, squareSize, squareSize);
			}
		}
	}

	private void drawGame() {
		frame.repaint();
	}

	private void setup() {
		// Title at the top
		JLabel title = new JLabel("Player " + (names.size() + 1) + ":");
		title.setFont(FONT);
		controlPanel.add(title);

		// Name box title
		JLabel nameTitle = new JLabel("Enter your name:");
		nameTitle.setFont(FONT);
		controlPanel.add(nameTitle);

		// Box for player to enter their name
		JTextField nameBox = new JTextField("Players name");
		nameBox.setFont(FONT);
		nameBox.setMaximumSize(new Dimension(300, 30));
		controlPanel.add(nameBox);

		// Title for character selection
		JLabel charTitle = new JLabel("Select your character:");
		charTitle.setFont(FONT);
		controlPanel.add(charTitle);

		// Character selection radio buttons
		java.util.List<JRadioButton> options = view.getCharacters().stream().map(i -> new JRadioButton(i.getName()))
				.collect(Collectors.toList());
		ButtonGroup charSelect = new ButtonGroup();
		for (int i = 0; i < options.size(); i++) {
			JRadioButton b = options.get(i);
			b.setOpaque(false);
			b.setFont(FONT);
			controlPanel.add(b);
			charSelect.add(b);
		}

		// Next button to add another player
		JButton next = new JButton("Next");
		next.setFont(FONT);
		controlPanel.add(next);

		// Start button to start game
		JButton start = new JButton("START");
		start.setFont(FONT);
		start.setVisible(false);
		controlPanel.add(start);

		// What happens when next is pressed
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// Search for the selected button
				for (int i = 0; i < options.size(); i++) {
					if (options.get(i).isSelected()) {
						// Record What they selected
						names.put(view.getCharacters().get(i), nameBox.getText());
						// Removed the selected option
						options.get(i).setSelected(false);
						options.get(i).setEnabled(false);
						// Reset the name box
						nameBox.setText("Players name");
						// Update the player number in title
						title.setText("Player " + (names.size() + 1) + ":");
						// Check if the start button should become visable
						if (names.size() >= 3) {
							start.setVisible(true);
						}
						// Action complete
						return;
					}
				}
			}
		});

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// Deal cards to players
				view.dealHands(names.keySet());
				// Start game Controller
				next.setVisible(false);
				start.setVisible(false);
				title.setVisible(false);
				options.stream().forEach(i -> i.setVisible(false));
				nameTitle.setVisible(false);
				nameBox.setVisible(false);
				charTitle.setVisible(false);
			}
		});

		// Add a player move instruction
		moveInfo = new JLabel("Clicked where to move");
		moveInfo.setFont(FONT);
		controlPanel.add(moveInfo);

		// Dice roll button
		roll = new JButton("Roll dice");
		roll.setFont(FONT);
		controlPanel.add(roll);
		roll.setVisible(false);

		roll.addActionListener(new ActionListener() {
			// Roll dice when pressed
			public void actionPerformed(ActionEvent ev) {
				view.rollDice();
			}
		});

		// Guess button
		guess = new JButton("Make a guess");
		guess.setFont(FONT);
		controlPanel.add(guess);
		guess.setVisible(false);

		guess.addActionListener(new ActionListener() {
			// Start guess when pressed
			public void actionPerformed(ActionEvent ev) {
				view.askGuessOrSolve(true);
			}
		});

		// Solve button
		solve = new JButton("Try to solve");
		solve.setFont(FONT);
		controlPanel.add(solve);
		solve.setVisible(false);

		solve.addActionListener(new ActionListener() {
			// Start solve when pressed
			public void actionPerformed(ActionEvent ev) {
				view.askGuessOrSolve(false);
			}
		});

		frame.setVisible(true);
		setup = true;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Game) {
			Game game = (Game) o;
			view = game;
			if (!setup)
				setup();
			drawGame();
		}
	}
}
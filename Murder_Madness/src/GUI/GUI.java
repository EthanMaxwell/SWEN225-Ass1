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
			}
		};

		// Create the panel for the controller
		controlPanel = new ControlPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawControl(g);
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
		gbc.weightx = 1;
		boardPanel.setOpaque(true);
		frame.add(boardPanel, gbc);

		// Make the width of controlPanel 30% the size of the screen
		gbc.weightx = 0.0;
		controlPanel.setOpaque(true);
		frame.add(controlPanel, gbc);

		frame.pack();
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Set frame dimension
		frame.setLocationByPlatform(true);
	}

	public int getNumPlayers() {
		String title = "Welcome to Murder Madness!";
		String question = "How many players do you have?";
		Object[] fixed_options = { "3", "4" };

//		String input = (String) JOptionPane.showInputDialog(null, question, title,
//				JOptionPane.INFORMATION_MESSAGE, null, fixed_option, fixed_option[0]);

		int input = JOptionPane.showOptionDialog(frame, question, title, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, fixed_options, fixed_options[0]);

//		// when user close dialog
//		if (input == null)
//			return -1;

		return input;
	}

	/**
	 * @return the dimension of the screen
	 */
	public Dimension screenDimension() {
		return new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
	}

//
//		JButton rollDice = new JButton("Roll Dice");
//		rollDice.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ev) {
//				view.rollDice();
//			}
//		});
//
//		JButton butt2 = new JButton("Option select");
//		butt2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ev) {
//				Object[] options = { "1", "2", "3", "4" };
//				int n = JOptionPane.showOptionDialog(frame, "You can select numbers lol", "A selection prompt",
//						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
//			}
//		});
//
//		buttonBar.add(rollDice);
//		buttonBar.add(butt2);
//
//		// Make drawing area
//		JPanel drawingArea = new JPanel() {
//			public void paintComponent(Graphics g) {
//				drawGame(g);
//			}
//		};
//
//		frame.getContentPane().add(BorderLayout.NORTH, buttonBar);
//		frame.getContentPane().add(BorderLayout.CENTER, drawingArea);
//
//		frame.setVisible(true);
//	}

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

	private void drawControl(Graphics g) {
		if (view != null) {
			if (view.getGameState().equals(Game.GameState.SelectPlayerNumber)) {

			}
		}
	}

	private void drawBoard(Graphics g, Game game) {
		// TODO : draw out the game board
		int boardWidth = boardPanel.getWidth();
		int boardHeight = boardPanel.getHeight();
		int squareSize;

		if (boardWidth < boardHeight)
			squareSize = boardWidth / 24;
		else
			squareSize = boardHeight / 24;

		for (int row = 0; row < 24; row++){
			for (int col = 0; col < 24; col++){

				int x = col * squareSize;
				int y = row * squareSize;

				Square square = view.getBoard().getGameSquare(col,row);
				g.setColor(Color.yellow);
				if (square.getPartOf() instanceof Room){
					//Haunted House
					if (square.getPartOf().toString().equals("Haunted House")){
						g.setColor(Color.blue);
					}
					//Manic Manor
					else if (square.getPartOf().toString().equals("Manic Manor")){
						g.setColor(Color.magenta);
					}
					//Villa Celia
					else if (square.getPartOf().toString().equals("Villa Celia")){
						g.setColor(Color.green);
					}
					//Calamity Castle
					else if (square.getPartOf().toString().equals("Calamity Castle")){
						g.setColor(Color.pink);
					}
					//Peril Palace
					else if (square.getPartOf().toString().equals("Peril Palace")){
						g.setColor(Color.red);
					}

				}
				else if (!square.isAccessible()){
					g.setColor(Color.black);
				}



				g.fillRect(x,y,squareSize,squareSize);
				g.setColor(Color.gray);
				g.drawRect(x,y,squareSize,squareSize);
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
		JTextField nameBox = new JTextField("Name");
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
						nameBox.setText("Name");
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
				// Remove no longer needed objects
				controlPanel.removeAll();
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
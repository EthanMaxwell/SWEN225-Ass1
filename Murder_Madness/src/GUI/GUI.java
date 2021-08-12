package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import GameCode.Game;
import GameCode.Game.GameState;

//The "interogating" is just asking about the game start like player locations and all that jazz.
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
		gbc.weightx = 0.7;
		boardPanel.setOpaque(true);
		frame.add(boardPanel, gbc);

		// Make the width of controlPanel 30% the size of the screen
		gbc.weightx = 0.3;
		controlPanel.setOpaque(true);
		frame.add(controlPanel, gbc);

		frame.pack();
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize()); // Set frame dimension
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
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
				/*JRadioButton r1 = new JRadioButton("A) Male");
				JRadioButton r2 = new JRadioButton("B) Female");
				r1.setBounds(75, 50, 100, 30);
				r2.setBounds(75, 100, 100, 30);
				ButtonGroup bg = new ButtonGroup();
				bg.add(r1);
				bg.add(r2);
				controlPanel.add(r1);
				controlPanel.add(r2);
				controlPanel.setVisible(true);*/
			}
		}
	}

	private void drawBoard(Graphics g, Game game) {
		// TODO : draw out the game board
	}

	private void drawGame() {
		frame.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Game) {
			Game game = (Game) o;
			view = game;
			drawGame();
		}
	}
}
package GameCode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

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

	JFrame frame;

	public GUI() {
		initialise();
	}

	@SuppressWarnings("serial")
	private void initialise() {

		// Create the frame
		frame = new JFrame("GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

		// Create a menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu hello = new JMenu("Hello");
		menuBar.add(hello);
		JMenuItem there = new JMenuItem("there");
		hello.add(there);
		frame.add(menuBar);

		// Create the bar for the buttons
		JPanel buttonBar = new JPanel();

		JButton rollDice = new JButton("Roll Dice");
		rollDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				view.rollDice();
			}
		});

		JButton butt2 = new JButton("Option select");
		butt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Object[] options = { "1", "2", "3", "4" };
				int n = JOptionPane.showOptionDialog(frame, "You can select numbers lol", "A selection prompt",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
			}
		});

		buttonBar.add(rollDice);
		buttonBar.add(butt2);

		// Make drawing area
		JPanel drawingArea = new JPanel() {
			public void paintComponent(Graphics g) {
				drawGame(g);
			}
		};

		frame.getContentPane().add(BorderLayout.NORTH, buttonBar);
		frame.getContentPane().add(BorderLayout.CENTER, drawingArea);

		frame.setVisible(true);
	}

	/**
	 * Draw out the game
	 * 
	 * @param g The graphic to use when drawing the game
	 */
	private void drawGame(Graphics g) {
		g.drawOval(100, 100, 100, 100);
		Game game = view;
		if (game != null) {
			if (game.getGameState() == GameState.SelectPlayerNumber) {
				Object[] options = { "3", "4" };
				int n = JOptionPane.showOptionDialog(frame,
						"How many player (this dialog box is not permanent, it's an example)", "Select a number",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				game.setupBoard(n + 3);
			}
			else {
				drawBaord(g, game);
			}
		}
	}
	
	private void drawBaord(Graphics g, Game game){
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
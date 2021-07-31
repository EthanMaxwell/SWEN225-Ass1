
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * TODO Write me
 */
public abstract class GUI {

	// these are the methods you need to implement.

	/**
	 * Is called when the drawing area is redrawn and performs all the logic for the
	 * actual drawing, which is done with the passed Graphics object.
	 */
	protected abstract void redraw(Graphics g);

	/**
	 * Is called when the mouse is clicked (actually, when the mouse is released),
	 * and is passed the MouseEvent object for that click.
	 */
	protected abstract void onClick(MouseEvent e);

	/**
	 * @return the dimensions of the drawing area.
	 */
	public Dimension getDrawingAreaDimension() {
		return drawing.getSize();
	}

	/**
	 * Redraws the window (including drawing pane). This is already done whenever a
	 * button is pressed or the search box is updated, so you probably won't need to
	 * call this.
	 */
	public void redraw() {
		frame.repaint();
	}

	// --------------------------------------------------------------------
	// Everything below here is Swing-related and, while it's worth
	// understanding, you don't need to look any further to finish the
	// assignment up to and including completion.
	// --------------------------------------------------------------------

	/*
	 * In Swing, everything is a component; buttons, graphics panes, tool tips, and
	 * the window frame are all components. This is implemented by JComponent, which
	 * sits at the top of the component inheritance hierarchy. A JFrame is a
	 * component that represents the outer window frame (with the minimise,
	 * maximise, and close buttons) of your program. Every swing program has to have
	 * one somewhere. JFrames can, of course, have other components inside them.
	 * JPanels are your bog-standard container component (can have other components
	 * inside them), that are used for laying out your UI.
	 */

	private JFrame frame;

	private JComponent drawing; // we customise this to make it a drawing pane.
	private JTextArea textOutputArea;

	private JFileChooser fileChooser;

	public GUI() {
		initialise();
	}

	@SuppressWarnings("serial")
	private void initialise() {

		/*
		 * first, we make the buttons etc. that go along the top bar.
		 */

		JButton butt1 = new JButton("Press me");
		butt1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JOptionPane.showMessageDialog(frame, "Cheese is a loaf of milk");
				redraw();
			}
		});

		JButton butt2 = new JButton("Option select");
		butt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Object[] options = {"1", "2", "3", "4"};
				int n = JOptionPane.showOptionDialog(frame, "You can select numbers lol",
						"A selection prompt", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[2]);
				redraw();
			}
		});

		// almost any component (JPanel, JFrame, etc.) that contains other
		// components inside it needs a LayoutManager to be useful, these do
		// exactly what you expect. three common LayoutManagers are the BoxLayout,
		// GridLayout, and BorderLayout. BoxLayout, contrary to its name, places
		// components in either a row (LINE_AXIS) or a column (PAGE_AXIS).
		// GridLayout is self-describing. BorderLayout puts a single component
		// on the north, south, east, and west sides of the outer component, as
		// well as one in the centre. google for more information.
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		// make an empty border so the components aren't right up against the
		// frame edge.
		Border edge = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		panel.setBorder(edge);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 5));
		// manually set a fixed size for the panel containing the buttons
		buttons.add(butt1);
		buttons.add(butt2);

		panel.add(buttons);

		panel.add(Box.createRigidArea(new Dimension(15, 0)));
		// glue is another invisible component that grows to take up all the
		// space it can on resize.
		panel.add(Box.createHorizontalGlue());

		panel.add(Box.createRigidArea(new Dimension(5, 0)));

		/*
		 * then make the drawing canvas, which is really just a boring old JComponent
		 * with the paintComponent method overridden to paint whatever we like. this is
		 * the easiest way to do drawing.
		 */

		drawing = new JComponent() {
			protected void paintComponent(Graphics g) {
				redraw(g);
			}
		};
		drawing.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		// this prevents a bug where the component won't be
		// drawn until it is resized.
		drawing.setVisible(true);

		drawing.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				onClick(e);
				redraw();
			}
		});

		/*
		 * finally, make the outer JFrame and put it all together. this is more
		 * complicated than it could be, as we put the drawing and text output
		 * components inside a JSplitPane so they can be resized by the user. the
		 * JScrollPane and the top bar are then added to the frame.
		 */

		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setDividerSize(5); // make the selectable area smaller
		split.setContinuousLayout(true); // make the panes resize nicely
		split.setResizeWeight(1); // always give extra space to drawings
		// JSplitPanes have a default border that makes an ugly row of pixels at
		// the top, remove it.
		split.setBorder(BorderFactory.createEmptyBorder());
		split.setTopComponent(drawing);

		frame = new JFrame("GUI");
		// this makes the program actually quit when the frame's close button is
		// pressed.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.NORTH);
		frame.add(split, BorderLayout.CENTER);

		// always do these two things last, in this order.
		frame.pack();
		frame.setVisible(true);
	}
}
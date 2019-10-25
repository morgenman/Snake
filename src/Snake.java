import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Snake {
	private static Worm worm = new Worm();

	/**
	 * @return the worm
	 */
	public static Worm getWorm() {
		return worm;
	}

	/**
	 * @param worm
	 *            the worm to set
	 */
	public static void setWorm(Worm worm) {
		Snake.worm = worm;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake"); // new window
		Canvas main = new Canvas();
		frame.setPreferredSize(new Dimension(917, 940)); // set size of window. This renders a 900x900 canvas inside,
		                                                 // the numbers are off due to window decorations(title bar)
		frame.getContentPane().add(main); // add canvas to frame
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.Timer;

public class Canvas extends JComponent {
	private static final long	serialVersionUID	= 1L;				// errors without this when extending Jcomponent
	private int					direction			= 2;				// 0 = N, 1 = E, 2 = S, 3 = W;
	private Block				whiteblock			= new Block(10, 10);// set initial white block
	private int					n;										// a number to base the speed off of. as it
	                                                                    // increases, the speed decreases
	private Timer				timer;									// the timer loop
	private Boolean				collided			= false;			// state of collision

	public Canvas() {
		timer = new Timer(300, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				update(); // move snake by one block
				repaint();// repaint window
				eatwhitesquare(); // if there is a white square in front of where its going to be next loop, add that
				                  // square to the linked list and make a new one
				collide(); // see if its collided with the wallls/itself
			}
		}); // every 300 ms initially, gets faster
		timer.start();
		setFocusable(true); // needed for keyboard input
		requestFocus(); // needed for keyboard input
		addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

				// TODO Auto-generated method stub
				int keyCode = e.getKeyCode(); // get code from key event
				switch (keyCode) {
				case KeyEvent.VK_UP:
					direction = 0;
					break;
				case KeyEvent.VK_DOWN:
					direction = 2;
					break;
				case KeyEvent.VK_LEFT:
					direction = 3;
					break;
				case KeyEvent.VK_RIGHT:
					direction = 1;
					break;
				} // controls mapped to up down left right keys

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}

	protected void paintComponent(Graphics g) {
		// fill window with grid (screen width/width of box = number of boxes horizontally)
		for (int i = 0; i < getWidth() / 20; i++) {
			for (int j = 0; j < getHeight() / 20; j++) {
				g.setColor(Color.BLACK);
				g.fillRect(i * 20, j * 20, 20, 20);
				g.setColor(Color.DARK_GRAY);
				g.drawRect(i * 20, j * 20, 20, 20);
				// if the box has the same coordinates as one of the worm blocks, paint it red
				if (inworm(i, j)) {
					g.setColor(Color.red);
					g.fillRect(i * 20, j * 20, 20, 20);
				}
				// if its the white block, paint accordingly
				if (whiteblock.x == i && whiteblock.y == j) {
					g.setColor(Color.WHITE);
					g.fillRect(i * 20, j * 20, 20, 20);
				}
			}

		}
		// game over screen
		if (collided) {
			g.setColor(Color.red);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.white);
			g.setFont(new Font("Calibri", Font.BOLD, 50));
			g.drawString("Game Over", getWidth() / 2 - 100, getHeight() / 2);
			timer.stop();
		}

	}

	private boolean inworm(int x, int y) {
		// for each element in the snake/worm, see if they have the same coordinates ass passed through point
		for (int k = 0; k < Snake.getWorm().worm.size(); k++) {
			if (Snake.getWorm().worm.get(k).getX() == x && Snake.getWorm().worm.get(k).getY() == y) return true;
		}
		return false;

	}

	private void eatwhitesquare() {
		// for each block in snake/worm, see if it's the white block
		for (int k = 0; k < Snake.getWorm().worm.size(); k++) {
			if (Snake.getWorm().worm.get(k).getX() == whiteblock.x && Snake.getWorm().worm.get(k)
			        .getY() == whiteblock.y) {
				growworm();// add one box to the worm
				whiteblock = random(); // respawn the white block
				n += 2; // add to the n value
				// the log function makes it so the speed is playable and doesn't get too fast right away. It smooths
				// the transistion
				timer.setDelay((int) (330 - 70 * Math.log(n)));
			}
		}
	}

	private void collide() {
		// for each block in the snake/worm, see if the block is out of the window
		for (int k = 0; k < Snake.getWorm().worm.size(); k++) {
			if (Snake.getWorm().worm.get(k).getX() < 0 || Snake.getWorm().worm.get(k).getY() < 0 || Snake.getWorm().worm
			        .get(k).getX() >= getWidth() / 20 || Snake.getWorm().worm.get(k).getY() >= getWidth() / 20)
			    collided = true;
			// for each block in the worm, compare it to every other block in the worm
			for (int j = 0; j < Snake.getWorm().worm.size(); j++) {
				if (Snake.getWorm().worm.get(k).getX() == Snake.getWorm().worm.get(j).getX() && Snake.getWorm().worm
				        .get(k).getY() == Snake.getWorm().worm.get(j).getY() && j != k) collided = true; // j!=k is
				                                                                                         // making sure
				                                                                                         // a block
				                                                                                         // isn't
				                                                                                         // positively
				                                                                                         // matced with
				                                                                                         // itself
			}

		}

		if (getWidth() == 0 || getHeight() == 0) collided = false; // on initial launch the width and height is zero, so
		                                                           // the collision is triggered prematurely.
	}

	private void growworm() {
		// add one block to the worm/snake, in the direction of current motion
		Worm temp = Snake.getWorm();
		switch (direction) {
		case 0:
			temp.worm.add(new Block(temp.worm.get(temp.worm.size() - 1).x, temp.worm.get(temp.worm.size() - 1).y - 1));
			break;
		case 1:
			temp.worm.add(new Block(temp.worm.get(temp.worm.size() - 1).x + 1, temp.worm.get(temp.worm.size() - 1).y));
			break;
		case 2:
			temp.worm.add(new Block(temp.worm.get(temp.worm.size() - 1).x, temp.worm.get(temp.worm.size() - 1).y + 1));
			break;
		case 3:
			temp.worm.add(new Block(temp.worm.get(temp.worm.size() - 1).x - 1, temp.worm.get(temp.worm.size() - 1).y));
			break;
		}
		Snake.setWorm(temp);
	}

	private void update() {
		// add one block to the fron tof the worm, remove one from the back
		Worm temp = Snake.getWorm();
		switch (direction) {
		case 0:
			temp.worm.remove(0);
			temp.worm.add(new Block(temp.worm.get(temp.worm.size() - 1).x, temp.worm.get(temp.worm.size() - 1).y - 1));
			break;
		case 1:
			temp.worm.remove(0);
			temp.worm.add(new Block(temp.worm.get(temp.worm.size() - 1).x + 1, temp.worm.get(temp.worm.size() - 1).y));
			break;
		case 2:
			temp.worm.remove(0);
			temp.worm.add(new Block(temp.worm.get(temp.worm.size() - 1).x, temp.worm.get(temp.worm.size() - 1).y + 1));
			break;
		case 3:
			temp.worm.remove(0);
			temp.worm.add(new Block(temp.worm.get(temp.worm.size() - 1).x - 1, temp.worm.get(temp.worm.size() - 1).y));
			break;
		}
		Snake.setWorm(temp);
	}

	private Block random() {
		// generate a random block
		int x, y;
		do {
			x = (int) (Math.random() * getWidth() / 20);
			y = (int) (Math.random() * getHeight() / 20);
		}
		while (inworm(x, y));
		return new Block(x, y);
	}

}

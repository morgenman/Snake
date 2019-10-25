
public class Worm {
	LinkedList<Block> worm = new LinkedList<Block>(); // using LinkedList class

	public Worm() {
		for (int i = 0; i <= 10; i++) { // give it 10 blocks initially
			worm.add(new Block(i + 2, 3)); // initial position in third row of bricks

		}
	}
}

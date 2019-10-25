/**
 * LinkedList
 */
public class LinkedList<T> implements List<T> {
	private int		size;
	private Node<T>	head;
	private Node<T>	tail;

	private void printList() {
		String str = "";
		Node<T> node = head;
		for (int i = 0; i < size; i++) {
			str += node.value + ", ";
			node = node.next;
		}
		System.out.println(str);
	}

	/**
	 * Adds the object x to the end of the list.
	 * 
	 * @param x
	 *            the element to be added to this list
	 * @return true
	 */
	public boolean add(T x) {
		Node<T> newNode = new Node<T>();
		newNode.value = x;
		if (size == 0) {
			head = newNode;
			tail = newNode;
		}
		else {
			tail.next = newNode;
			tail = newNode;
		}
		size++;
		return true;
	}

	/**
	 * Adds the object x at the specified position
	 * 
	 * @param index
	 *            the position to add the element
	 * @param x
	 *            the element to be added to the list
	 * @return true if the operation succeeded, false otherwise
	 * @throws IllegalArgumentException
	 *             - if index is longer
	 *             than the currentlength of the list
	 */
	public boolean add(int index, T x) {
		if (index < 0 || index > size) { throw new IllegalArgumentException(); }
		if (size == 0) {
			add(x);
			return true;
		}
		Node<T> xNode = new Node<T>();
		xNode.value = x;
		if (index == 0) {
			xNode.next = head;
			head = xNode;
		}
		else if (index == size) {
			tail.next = xNode;
			tail = xNode;
		}
		else {
			Node<T> prev = head;
			for (int i = 0; i < index - 1; i++) {
				prev = prev.next;
			}
			xNode.next = prev.next;
			prev.next = xNode;
		}
		size++;
		return true;
	}

	/**
	 * Returns the number of elements in this list
	 * 
	 * @return the number of elements in this list
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the element with the specified position in this list
	 * 
	 * @param index
	 *            the position of the element
	 * @return the element at the specified position in this list
	 * @throws IllegalArugmentException
	 *             if index is longer than the
	 *             number of elements in the list
	 */
	public T get(int index) {
		if (index < 0 || index >= size) { throw new IllegalArgumentException(); }
		Node<T> node = head;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node.value;
	}

	/**
	 * Replaces the object at the specified position
	 * 
	 * @param index
	 *            the position to replace
	 * @param x
	 *            the element to be stored
	 * @return the previous value of the element at index
	 * @throws IllegalArugmentException
	 *             if index is longer than the
	 *             number of elements in the list
	 */
	public T set(int index, T x) {
		if (index < 0 || index >= size) { throw new IllegalArgumentException(); }
		Node<T> node = head;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		T v = node.value;
		node.value = x;
		return v;
	}

	/**
	 * Removes the object at the specified position
	 * 
	 * @param index
	 *            the position to remove
	 * @return the object that was removed
	 * @throws IllegalArugmentException
	 *             if index is more than
	 *             the number of elements in the list
	 */
	public T remove(int index) {
		if (index < 0 || index >= size) { throw new IllegalArgumentException(); }
		if (index == 0) {
			T v = head.value;
			head = head.next;
			size--;
			return v;
		}
		else {
			Node<T> prev = head;
			for (int i = 0; i < index - 1; i++) {
				prev = prev.next;
			}
			T v = prev.next.value;
			prev.next = prev.next.next;
			if (index == size - 1) {
				tail = prev;
			}
			size--;
			return v;
		}
	}

	/**
	 * Tests if this list has no elements.
	 * 
	 * @return <tt>true</tt> if this list has no elements;
	 *         <tt>false</tt> otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 *
	 * @param element
	 *            element whose presence in this List is to be tested.
	 * @return <code>true</code> if the specified element is present;
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(T element) {
		return indexOf(element) != -1;
	}

	/**
	 * Returns the index of the specified element
	 *
	 * @param element
	 *            the element we're looking for
	 * @return the index of the element in the list, or -1 if it is not
	 *         contained within the list
	 */
	public int indexOf(T element) {
		Node<T> node = head;
		for (int i = 0; i < size; i++) {
			if (node.value.equals(element)) { return i; }
			node = node.next;
		}
		return -1;
	}

	private class Node<T> {
		T		value;
		Node<T>	next;
	}

}
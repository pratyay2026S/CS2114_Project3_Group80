
public class LinkedQueue<T> {

    private Node<T> front;
    private Node<T> back;
    private int size;

    /**
     * Creates an empty queue.
     */
    public LinkedQueue() {
        front = null;
        back = null;
        size = 0;
    }

    /**
     * Adds an entry to the back of the queue.
     */
    public void enqueue(T anEntry) {
        if (anEntry == null) {
            throw new IllegalArgumentException("Entry cannot be null.");
        }

        Node<T> newNode = new Node<T>(anEntry);

        if (isEmpty()) {
            front = newNode;
            back = newNode;
        } else {
            back.setNext(newNode);
            back = newNode;
        }

        size++;
    }

    /**
     * Removes and returns the entry at the front.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }

        T frontEntry = front.getData();
        front = front.getNext();
        size--;

        if (front == null) {
            back = null;
        }

        return frontEntry;
    }

    /**
     * Returns the entry at the front without removing it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }

        return front.getData();
    }

    /**
     * Checks whether the queue is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of entries in the queue.
     */
    public int getSize() {
        return size;
    }
}

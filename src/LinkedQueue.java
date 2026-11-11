public class LinkedQueue<T> {
    //~ Fields ................................................................
    private Node<T> firstNode;
    private Node<T> lastNode;
    int size;
    //~ Constructors ..........................................................
    public LinkedQueue() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }
    //~Public  Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param newEntry
     */
    void enqueue(T newEntry) {
        Node<T> newNode = new Node<T>(newEntry);
        if (isEmpty()) {
            firstNode = newNode;
        }
        else {
            lastNode.next = newNode;
        }
        lastNode = newNode;
        size++;
    }
    T dequeque() {
        if(isEmpty()) {
            //exception
        }
        T front = firstNode.data;
        firstNode = firstNode.next;
        size--;
        if(firstNode == null) {
            lastNode = null;
        }
        return front;

    }
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @return
     */
    T peek() {
        return firstNode.data;
    }
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @return
     */
    boolean isEmpty() { //check this method
        return firstNode == null;
    }
    
    /**
     * A node in the chain. Holds one entry and a link to the next node.
     *
     * @param <E> the type of data held in the node
     */
    private static class Node<E>
    {
        private E data;        // entry stored in this node
        private Node<E> next;  // link to the next node, or null

        /**
         * Creates a node with the given data and no next node.
         *
         * @param dataPortion the entry to store
         */
        private Node(E dataPortion)
        {
            this(dataPortion, null);
        }

        /**
         * Creates a node with the given data and next link.
         *
         * @param dataPortion the entry to store
         * @param nextNode the node that follows this one
         */
        private Node(E dataPortion, Node<E> nextNode)
        {
            data = dataPortion;
            next = nextNode;
        }
    }
}


import student.TestCase;

public class LinkedQueueTest extends TestCase {

    private LinkedQueue<String> queue;

    /**
     * Creates a new queue before each test.
     */
    public void setUp() {
        queue = new LinkedQueue<String>();
    }

    /**
     * Tests that a new queue is empty.
     */
    public void testConstructor() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    /**
     * Tests adding one item.
     */
    public void testEnqueue() {
        queue.enqueue("A");

        assertFalse(queue.isEmpty());
        assertEquals(1, queue.getSize());
        assertEquals("A", queue.peek());
    }

    /**
     * Tests adding multiple items.
     */
    public void testEnqueueMultiple() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals(3, queue.getSize());
        assertEquals("A", queue.peek());
    }

    /**
     * Tests rejecting a null item.
     */
    public void testEnqueueNull() {
        try {
            queue.enqueue(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertTrue(queue.isEmpty());
            assertEquals(0, queue.getSize());
        }
    }

    /**
     * Tests removing the first item.
     */
    public void testDequeue() {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.peek());
        assertEquals(1, queue.getSize());
    }

    /**
     * Tests FIFO ordering.
     */
    public void testFIFOOrder() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests removing from an empty queue.
     */
    public void testDequeueEmpty() {
        try {
            queue.dequeue();
            fail("Expected IllegalStateException");
        }
        catch (IllegalStateException e) {
            assertTrue(queue.isEmpty());
        }
    }

    /**
     * Tests viewing the front item.
     */
    public void testPeek() {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.peek());
        assertEquals(2, queue.getSize());
    }

    /**
     * Tests peeking at an empty queue.
     */
    public void testPeekEmpty() {
        try {
            queue.peek();
            fail("Expected IllegalStateException");
        }
        catch (IllegalStateException e) {
            assertTrue(queue.isEmpty());
        }
    }

    /**
     * Tests whether the queue reports
     * its empty state correctly.
     */
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());

        queue.enqueue("A");
        assertFalse(queue.isEmpty());

        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    /**
     * Tests size changes after operations.
     */
    public void testGetSize() {
        assertEquals(0, queue.getSize());

        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals(2, queue.getSize());

        queue.dequeue();

        assertEquals(1, queue.getSize());
    }

    /**
     * EXCEEDS REQUIREMENT:
     *
     * Tests FIFO order after interleaved
     * enqueue and dequeue operations.
     */
    public void testInterleavedEnqueueDequeue() {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.dequeue());

        queue.enqueue("C");
        queue.enqueue("D");

        assertEquals("B", queue.dequeue());

        queue.enqueue("E");

        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.dequeue());
        assertEquals("E", queue.dequeue());

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    /**
     * EXCEEDS REQUIREMENT:
     *
     * Tests enqueueing after the queue
     * becomes completely empty.
     */
    public void testEnqueueAfterEmpty() {
        queue.enqueue("A");
        assertEquals("A", queue.dequeue());

        assertTrue(queue.isEmpty());

        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * EXCEEDS REQUIREMENT:
     *
     * Tests multiple cycles of enqueueing
     * and dequeueing.
     */
    public void testMultipleInterleavedOperations() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());

        queue.enqueue("D");

        assertEquals("B", queue.dequeue());

        queue.enqueue("E");
        queue.enqueue("F");

        assertEquals("C", queue.dequeue());
        assertEquals("D", queue.dequeue());

        queue.enqueue("G");

        assertEquals("E", queue.dequeue());
        assertEquals("F", queue.dequeue());
        assertEquals("G", queue.dequeue());

        assertTrue(queue.isEmpty());
    }

    /**
     * Tests that peek does not remove items.
     */
    public void testPeekDoesNotRemove() {
        queue.enqueue("A");

        assertEquals("A", queue.peek());
        assertEquals("A", queue.peek());

        assertEquals(1, queue.getSize());
        assertEquals("A", queue.dequeue());

        assertTrue(queue.isEmpty());
    }


    /**
     * Tests that the queue works with Integer values.
     */
    public void testIntegerQueue() {
        LinkedQueue<Integer> numbers =
            new LinkedQueue<Integer>();
    
        numbers.enqueue(10);
        numbers.enqueue(20);
        numbers.enqueue(30);
    
        assertEquals(3, numbers.getSize());
    
        assertEquals(10, numbers.dequeue().intValue());
        assertEquals(20, numbers.dequeue().intValue());
        assertEquals(30, numbers.dequeue().intValue());
    
        assertTrue(numbers.isEmpty());
        assertEquals(0, numbers.getSize());
    }

}

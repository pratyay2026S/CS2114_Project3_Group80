
public class LinkedQueueTest extends student.TestCase {

    private LinkedQueue<String> queue;

    public void setUp() {
        queue = new LinkedQueue<String>();
    }

    public void testNewQueueIsEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    public void testEnqueueOneEntry() {
        queue.enqueue("A");

        assertFalse(queue.isEmpty());
        assertEquals(1, queue.getSize());
        assertEquals("A", queue.peek());
    }

    public void testEnqueueMultipleEntries() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals(3, queue.getSize());
        assertEquals("A", queue.peek());
    }

    public void testDequeue() {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.peek());
        assertEquals(1, queue.getSize());
    }

    public void testDequeueUntilEmpty() {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }

    public void testEnqueueAfterEmpty() {
        queue.enqueue("A");
        queue.dequeue();

        queue.enqueue("B");

        assertEquals("B", queue.peek());
        assertEquals(1, queue.getSize());
    }

    public void testPeekDoesNotRemoveEntry() {
        queue.enqueue("A");

        assertEquals("A", queue.peek());
        assertEquals(1, queue.getSize());
        assertFalse(queue.isEmpty());
    }

    public void testEnqueueNull() {
        try {
            queue.enqueue(null);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public void testDequeueEmptyQueue() {
        try {
            queue.dequeue();
            fail("Expected IllegalStateException.");
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
    }

    public void testPeekEmptyQueue() {
        try {
            queue.peek();
            fail("Expected IllegalStateException.");
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
    }
}

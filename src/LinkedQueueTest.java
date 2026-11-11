import java.util.NoSuchElementException;
import student.TestCase;

public class LinkedQueueTest extends TestCase{
    
    private LinkedQueue<String> queue;
    
    public void setUp() {
       queue = new LinkedQueue<String>();
    }
    
    public void testEnqueue() {
        //empty queue
        queue.enqueue("First");
        assertEquals(queue.peek(), "First");
        //not empty queue
        queue.enqueue("Second");
        assertEquals(queue.peek(), "Second");
    }
    
    public void testDequeue() {
        //not empty queue
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");
        assertEquals(queue.dequeque(), "Third");
        assertEquals(queue.dequeque(), "Second");
        assertEquals(queue.dequeque(), "First");
        //empty queue
        Exception exception = null;
        try
        {
            queue.dequeque();
        }
        catch (NoSuchElementException e)
        {
            exception = e;
        }
        assertNotNull(exception);
    }
    public void testPeek() {
        //empty queue
        Exception exception = null;
        try
        {
            queue.dequeque();
        }
        catch (NoSuchElementException e)
        {
            exception = e;
        }
        assertNotNull(exception);
        //not empty queue
        queue.enqueue("Entry");
        assertEquals(queue.peek(), "Entry");
    }
    
    public void testIsEmpty() {
        //empty queue
        assertTrue(queue.isEmpty());
        //not empty queue
        queue.enqueue("Entry");
        assertFalse(queue.isEmpty());
    }

}

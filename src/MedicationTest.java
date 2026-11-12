import student.TestCase;

//need to test exceptions

public class MedicationTest extends TestCase {
    
    Medication med; 
    public void setUp() {
       med = new Medication("Advil", 1, 2, 30);
    }
    
    public void testGetName() {
        assertEquals("Advil", med.getName());
    }
    
    public void testGetDosage() {
        assertEquals(1, med.getDosage());
    }
    
    public void testGetFrequency() {
        assertEquals(2, med.getFrequency());
    }
    
    public void testGetQuantity() {
        assertEquals(30, med.getQuantity());
    }
    
    public void testSetName() {
        med.setName("Robitussin");
        assertEquals("Robitussin", med.getName());
    }
    
    public void testSetDosage() {
        med.setDosage(3);
        assertEquals(3, med.getDosage());
    }
    
    public void testSetFrequency() {
        med.setFrequency(5);
        assertEquals(5, med.getFrequency());
    }
    
    public void testSetQuantity() {
        med.setQuantity(10);
        assertEquals(10, med.getQuantity());
    }
    
    public void testIsRunningLow() {
        assertFalse(med.isRunningLow(med.getQuantity()));
        
        med.setQuantity(5);
        assertTrue(med.isRunningLow(med.getQuantity()));
        
        med.setQuantity(4);
        assertTrue(med.isRunningLow(med.getQuantity()));
    }
    
    
}
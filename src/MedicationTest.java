
import student.TestCase;

public class MedicationTest extends TestCase {

    private Medication medication;

    /**
     * Creates a fresh medication before each test.
     */
    public void setUp() {
        medication = new Medication(
            "Vitamin D", 1000, 2, 30
        );
    }

    /**
     * Tests the constructor and getters.
     */
    public void testConstructorAndGetters() {
        assertEquals("Vitamin D", medication.getName());
        assertEquals(1000, medication.getDosage());
        assertEquals(2, medication.getFrequency());
        assertEquals(30, medication.getQuantity());
    }

    /**
     * Tests changing the medication name.
     */
    public void testSetName() {
        medication.setName("Vitamin C");

        assertEquals("Vitamin C", medication.getName());
    }

    /**
     * Tests rejecting an empty medication name.
     */
    public void testSetNameEmpty() {
        try {
            medication.setName("");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Vitamin D", medication.getName());
        }
    }

    /**
     * Tests rejecting a null medication name.
     */
    public void testSetNameNull() {
        try {
            medication.setName(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Vitamin D", medication.getName());
        }
    }

    /**
     * Tests changing the dosage.
     */
    public void testSetDosage() {
        medication.setDosage(2000);

        assertEquals(2000, medication.getDosage());
    }

    /**
     * Tests rejecting a zero dosage.
     */
    public void testSetDosageZero() {
        try {
            medication.setDosage(0);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(1000, medication.getDosage());
        }
    }

    /**
     * Tests rejecting a negative dosage.
     */
    public void testSetDosageNegative() {
        try {
            medication.setDosage(-100);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(1000, medication.getDosage());
        }
    }

    /**
     * Tests changing medication frequency.
     */
    public void testSetFrequency() {
        medication.setFrequency(3);

        assertEquals(3, medication.getFrequency());
    }

    /**
     * Tests rejecting a zero frequency.
     */
    public void testSetFrequencyZero() {
        try {
            medication.setFrequency(0);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(2, medication.getFrequency());
        }
    }

    /**
     * Tests rejecting a negative frequency.
     */
    public void testSetFrequencyNegative() {
        try {
            medication.setFrequency(-1);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(2, medication.getFrequency());
        }
    }

    /**
     * Tests changing medication quantity.
     */
    public void testSetQuantity() {
        medication.setQuantity(50);

        assertEquals(50, medication.getQuantity());
    }

    /**
     * Tests allowing a quantity of zero.
     */
    public void testSetQuantityZero() {
        medication.setQuantity(0);

        assertEquals(0, medication.getQuantity());
    }

    /**
     * Tests rejecting a negative quantity.
     */
    public void testSetQuantityNegative() {
        try {
            medication.setQuantity(-1);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(30, medication.getQuantity());
        }
    }

    /**
     * Tests low supply when quantity is below 5.
     */
    public void testIsRunningLowBelowFive() {
        assertTrue(medication.isRunningLow(3));
    }

    /**
     * Tests low supply at exactly 5.
     */
    public void testIsRunningLowAtFive() {
        assertTrue(medication.isRunningLow(5));
    }

    /**
     * Tests normal supply above 5.
     */
    public void testIsRunningLowAboveFive() {
        assertFalse(medication.isRunningLow(10));
    }

    /**
     * Tests low supply when quantity is zero.
     */
    public void testIsRunningLowZero() {
        assertTrue(medication.isRunningLow(0));
    }

    /**
     * Tests rejecting a negative quantity
     * when checking low supply.
     */
    public void testIsRunningLowNegative() {
        try {
            medication.isRunningLow(-1);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting an empty name in the constructor.
     */
    public void testConstructorEmptyName() {
        try {
            new Medication("", 1000, 2, 30);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting a null name in the constructor.
     */
    public void testConstructorNullName() {
        try {
            new Medication(null, 1000, 2, 30);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting an invalid dosage
     * in the constructor.
     */
    public void testConstructorInvalidDosage() {
        try {
            new Medication("Vitamin D", 0, 2, 30);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting an invalid frequency
     * in the constructor.
     */
    public void testConstructorInvalidFrequency() {
        try {
            new Medication("Vitamin D", 1000, 0, 30);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting an invalid quantity
     * in the constructor.
     */
    public void testConstructorInvalidQuantity() {
        try {
            new Medication("Vitamin D", 1000, 2, -5);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }
}

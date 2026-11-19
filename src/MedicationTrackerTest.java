
import student.TestCase;
import java.util.List;

public class MedicationTrackerTest extends TestCase {

    private MedicationTracker tracker;
    private Medication medication1;
    private Medication medication2;

    /**
     * Creates a fresh tracker before each test.
     */
    public void setUp() {
        tracker = new MedicationTracker();

        medication1 = new Medication(
            "Vitamin D", 1000, 1, 30
        );

        medication2 = new Medication(
            "Ibuprofen", 200, 2, 20
        );
    }

    /**
     * Tests that a new tracker is empty.
     */
    public void testEmptyTracker() {
        assertTrue(tracker.getMedications().isEmpty());
        assertTrue(tracker.getLowSupply().isEmpty());
    }

    /**
     * Tests adding a medication.
     */
    public void testAddMedication() {
        tracker.addMedication(medication1);

        assertEquals(1, tracker.getMedications().size());
        assertEquals(
            medication1,
            tracker.getMedications().get(0)
        );
    }

    /**
     * Tests adding multiple medications.
     */
    public void testAddMultipleMedications() {
        tracker.addMedication(medication1);
        tracker.addMedication(medication2);

        assertEquals(2, tracker.getMedications().size());
    }

    /**
     * Tests rejecting a null medication.
     */
    public void testAddNullMedication() {
        try {
            tracker.addMedication(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertTrue(tracker.getMedications().isEmpty());
        }
    }

    /**
     * Same-name medications must not make selection-based actions ambiguous.
     */
    public void testRejectDuplicateMedication() {
        tracker.addMedication(medication1);
        try {
            tracker.addMedication(new Medication("vitamin d", 500, 2, 10));
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(1, tracker.getMedications().size());
        }
    }

    /**
     * Increasing frequency adds doses without changing the next medication.
     */
    public void testUpdateFrequencyPreservesQueueOrder() {
        tracker.addMedication(medication1);
        tracker.addMedication(medication2);
        tracker.updateMedication(new Medication("Ibuprofen", 400, 3, 50));
        for (int i = 0; i < 7; i++) {
            assertSame(medication1, tracker.takeNextDose());
        }
        for (int i = 0; i < 21; i++) {
            assertSame(medication2, tracker.takeNextDose());
        }
        assertEquals(400, medication2.getDosage());
        assertEquals(29, medication2.getQuantity());
        try {
            tracker.takeNextDose();
            fail("Expected empty weekly queue");
        }
        catch (IllegalStateException e) {
            assertEquals("No doses remaining.", e.getMessage());
        }
    }

    /**
     * Lowering and raising frequency must not restore doses already taken.
     */
    public void testRepeatedFrequencyUpdatesPreserveTakenDoses() {
        tracker.addMedication(medication2);
        for (int i = 0; i < 10; i++) {
            tracker.takeNextDose();
        }
        tracker.updateMedication(new Medication("Ibuprofen", 200, 1, 10));
        tracker.updateMedication(new Medication("Ibuprofen", 200, 2, 10));
        for (int i = 0; i < 4; i++) {
            tracker.takeNextDose();
        }
        try {
            tracker.takeNextDose();
            fail("Expected empty weekly queue");
        }
        catch (IllegalStateException e) {
            assertEquals(6, medication2.getQuantity());
        }
    }

    /**
     * Changing quantity must not replenish the weekly dose queue.
     */
    public void testUpdateQuantityPreservesRemainingDoses() {
        tracker.addMedication(medication1);
        tracker.takeNextDose();
        tracker.updateMedication(new Medication("Vitamin D", 500, 1, 50));
        for (int i = 0; i < 6; i++) {
            tracker.takeNextDose();
        }
        try {
            tracker.takeNextDose();
            fail("Expected empty weekly queue");
        }
        catch (IllegalStateException e) {
            assertEquals(44, medication1.getQuantity());
        }
    }

    /**
     * Tests removing an existing medication.
     */
    public void testRemoveMedication() {
        tracker.addMedication(medication1);
        tracker.addMedication(medication2);

        assertTrue(tracker.removeMedication("Vitamin D"));

        assertEquals(1, tracker.getMedications().size());

        assertEquals(
            "Ibuprofen",
            tracker.getMedications().get(0).getName()
        );
    }

    /**
     * Tests removing a nonexistent medication.
     */
    public void testRemoveNonexistentMedication() {
        tracker.addMedication(medication1);

        assertFalse(
            tracker.removeMedication("Aspirin")
        );

        assertEquals(1, tracker.getMedications().size());
    }

    /**
     * Tests case-insensitive medication removal.
     */
    public void testRemoveMedicationIgnoreCase() {
        tracker.addMedication(medication1);

        assertTrue(
            tracker.removeMedication("vitamin d")
        );

        assertTrue(tracker.getMedications().isEmpty());
    }

    /**
     * Tests rejecting a null medication name.
     */
    public void testRemoveNullMedication() {
        tracker.addMedication(medication1);

        try {
            tracker.removeMedication(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(1, tracker.getMedications().size());
        }
    }

    /**
     * Tests taking a dose and decreasing quantity.
     */
    public void testTakeNextDose() {
        tracker.addMedication(medication1);

        Medication taken = tracker.takeNextDose();

        assertEquals(medication1, taken);
        assertEquals(29, medication1.getQuantity());
    }

    /**
     * Tests taking multiple doses.
     */
    public void testTakeMultipleDoses() {
        tracker.addMedication(medication1);

        tracker.takeNextDose();
        tracker.takeNextDose();
        tracker.takeNextDose();

        assertEquals(27, medication1.getQuantity());
    }

    /**
     * Tests taking a dose from an empty queue.
     */
    public void testTakeDoseEmptyQueue() {
        try {
            tracker.takeNextDose();
            fail("Expected IllegalStateException");
        }
        catch (IllegalStateException e) {
            // Expected
        }
    }

    /**
     * Tests that a dose cannot be taken
     * when the medication quantity is zero.
     */
    public void testTakeDoseZeroQuantity() {
        Medication empty = new Medication(
            "Vitamin C", 500, 1, 0
        );

        tracker.addMedication(empty);

        try {
            tracker.takeNextDose();
            fail("Expected IllegalStateException");
        }
        catch (IllegalStateException e) {
            assertEquals(0, empty.getQuantity());
        }
    }

    /**
     * Tests medication quantity reaching zero.
     */
    public void testQuantityReachesZero() {
        Medication limited = new Medication(
            "Vitamin C", 500, 1, 2
        );

        tracker.addMedication(limited);

        tracker.takeNextDose();
        tracker.takeNextDose();

        assertEquals(0, limited.getQuantity());

        try {
            tracker.takeNextDose();
            fail("Expected IllegalStateException");
        }
        catch (IllegalStateException e) {
            assertEquals(0, limited.getQuantity());
        }
    }

    /**
     * Tests medications with low supply.
     */
    public void testGetLowSupply() {
        Medication low = new Medication(
            "Vitamin C", 500, 1, 3
        );

        tracker.addMedication(medication1);
        tracker.addMedication(low);

        List<Medication> lowSupply =
            tracker.getLowSupply();

        assertEquals(1, lowSupply.size());
        assertEquals(low, lowSupply.get(0));
    }

    /**
     * Tests the low-supply boundary of five.
     */
    public void testLowSupplyBoundary() {
        Medication five = new Medication(
            "Vitamin C", 500, 1, 5
        );

        Medication six = new Medication(
            "Vitamin B", 100, 1, 6
        );

        tracker.addMedication(five);
        tracker.addMedication(six);

        assertEquals(1, tracker.getLowSupply().size());
        assertEquals(five, tracker.getLowSupply().get(0));
    }

    /**
     * Tests that taking a dose can trigger
     * the low-supply threshold.
     */
    public void testLowSupplyAfterDose() {
        Medication medication = new Medication(
            "Vitamin C", 500, 1, 6
        );

        tracker.addMedication(medication);

        assertTrue(tracker.getLowSupply().isEmpty());

        tracker.takeNextDose();

        assertEquals(5, medication.getQuantity());
        assertEquals(1, tracker.getLowSupply().size());
    }

    /**
     * Tests that removing medication also
     * removes its queued doses.
     */
    public void testRemoveMedicationFromQueue() {
        tracker.addMedication(medication1);
        tracker.removeMedication("Vitamin D");

        try {
            tracker.takeNextDose();
            fail("Expected IllegalStateException");
        }
        catch (IllegalStateException e) {
            // Queue should now be empty
        }
    }

    /**
     * Tests that returned medication lists
     * do not expose the internal list.
     */
    public void testGetMedicationsCopy() {
        tracker.addMedication(medication1);

        List<Medication> medications =
            tracker.getMedications();

        medications.clear();

        assertEquals(1, tracker.getMedications().size());
    }

    /**
     * Tests rejecting a null observer.
     */
    public void testAddNullObserver() {
        try {
            tracker.addObserver(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests that closing and reopening views does not retain old observers.
     */
    public void testRemoveObserver() {
        TestObserver closed = new TestObserver();
        TestObserver active = new TestObserver();
        tracker.addObserver(closed);
        tracker.removeObserver(closed);
        tracker.addObserver(active);
        tracker.addMedication(medication1);

        assertFalse(closed.wasUpdated());
        assertTrue(active.wasUpdated());
        assertEquals(1, active.getLastUpdate().size());
    }

    /**
     * Tests observer notifications after adding.
     */
    public void testObserverAfterAdd() {
        TestObserver observer = new TestObserver();

        tracker.addObserver(observer);
        tracker.addMedication(medication1);

        assertTrue(observer.wasUpdated());
        assertEquals(1, observer.getLastUpdate().size());
    }

    /**
     * Tests observer notifications after a dose.
     */
    public void testObserverAfterDose() {
        TestObserver observer = new TestObserver();

        tracker.addObserver(observer);
        tracker.addMedication(medication1);

        observer.reset();

        tracker.takeNextDose();

        assertTrue(observer.wasUpdated());
        assertEquals(1, observer.getLastUpdate().size());
    }

    /**
     * Tests observer notifications after removal.
     */
    public void testObserverAfterRemove() {
        TestObserver observer = new TestObserver();

        tracker.addObserver(observer);
        tracker.addMedication(medication1);

        observer.reset();

        tracker.removeMedication("Vitamin D");

        assertTrue(observer.wasUpdated());
        assertTrue(observer.getLastUpdate().isEmpty());
    }

    /**
     * Test-only observer implementation.
     */
    private static class TestObserver implements Observer {

        private boolean updated;
        private List<String> lastUpdate;

        @Override
        public void update(List<String> medications) {
            updated = true;
            lastUpdate = medications;
        }

        public boolean wasUpdated() {
            return updated;
        }

        public List<String> getLastUpdate() {
            return lastUpdate;
        }

        public void reset() {
            updated = false;
        }
    }
}

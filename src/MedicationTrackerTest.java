
import student.TestCase;
import java.util.List;

public class MedicationTrackerTest extends TestCase {

    private MedicationTracker tracker;

    public void setUp() {
        tracker = new MedicationTracker();
    }

    private Medication createMedication() {
        return new Medication("Aspirin", 1, 2, 30);
    }

    private class TestObserver implements Observer {

        private boolean wasUpdated = false;
        private List<String> medications;

        public void update(List<String> medications) {
            wasUpdated = true;
            this.medications = medications;
        }
    }

    // addObserver()
    public void testAddObserver() {
        TestObserver observer = new TestObserver();

        tracker.addObserver(observer);
        tracker.addMedication(createMedication());

        assertTrue(observer.wasUpdated);
    }

    public void testAddObserverNull() {
        try {
            tracker.addObserver(null);
            fail("Expected exception");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    // addMedication()
    public void testAddMedication() {
        Medication medication = createMedication();

        tracker.addMedication(medication);

        assertEquals(1, tracker.getMedications().size());
        assertEquals(medication, tracker.getMedications().get(0));
    }

    public void testAddMedicationNull() {
        try {
            tracker.addMedication(null);
            fail("Expected exception");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    // removeMedication()
    public void testRemoveMedication() {
        tracker.addMedication(createMedication());

        assertTrue(tracker.removeMedication("Aspirin"));
        assertEquals(0, tracker.getMedications().size());
    }

    public void testRemoveMedicationNotFound() {
        tracker.addMedication(createMedication());

        assertFalse(tracker.removeMedication("Ibuprofen"));
        assertEquals(1, tracker.getMedications().size());
    }

    // takeNextDose()
    public void testTakeNextDose() {
        Medication medication = createMedication();
        tracker.addMedication(medication);

        Medication taken = tracker.takeNextDose();

        assertEquals(medication, taken);
        assertEquals(29, medication.getQuantity());
    }

    public void testTakeNextDoseEmptyQueue() {
        try {
            tracker.takeNextDose();
            fail("Expected exception");
        }
        catch (IllegalStateException e) {
            // Expected
        }
    }

    public void testTakeNextDoseEmptyBottle() {
        Medication medication =
            new Medication("Aspirin", 1, 1, 0);

        tracker.addMedication(medication);

        try {
            tracker.takeNextDose();
            fail("Expected exception");
        }
        catch (IllegalStateException e) {
            // Expected
        }
    }

    // getLowSupply()
    public void testGetLowSupply() {
        Medication low = new Medication("Aspirin", 1, 1, 3);
        Medication normal = new Medication("Vitamin", 1, 1, 20);

        tracker.addMedication(low);
        tracker.addMedication(normal);

        List<Medication> result = tracker.getLowSupply();

        assertEquals(1, result.size());
        assertEquals(low, result.get(0));
    }

    public void testGetLowSupplyEmpty() {
        Medication medication =
            new Medication("Vitamin", 1, 1, 20);

        tracker.addMedication(medication);

        assertEquals(0, tracker.getLowSupply().size());
    }

    // notifyObservers()
    public void testNotifyObservers() {
        TestObserver observer = new TestObserver();

        tracker.addObserver(observer);
        tracker.addMedication(createMedication());

        assertTrue(observer.wasUpdated);
        assertEquals(1, observer.medications.size());
    }

    // getMedications()
    public void testGetMedications() {
        tracker.addMedication(createMedication());

        assertEquals(1, tracker.getMedications().size());
    }

    public void testGetMedicationsCopy() {
        tracker.addMedication(createMedication());

        List<Medication> copy = tracker.getMedications();
        copy.clear();

        assertEquals(1, tracker.getMedications().size());
    }
}


import student.TestCase;

public class PatientTest extends TestCase {

    private Patient patient;

    /**
     * Creates a new patient before each test.
     */
    public void setUp() {
        patient = new Patient(
            1,
            "John",
            "password123"
        );
    }

    /**
     * Tests the constructor and getters.
     */
    public void testConstructorAndGetters() {
        assertEquals(1, patient.getPatientID());
        assertEquals("John", patient.getName());

        assertNotNull(patient.getTracker());
        assertNotNull(patient.getAppointmentManager());
    }

    /**
     * Tests a successful login.
     */
    public void testLoginSuccess() {
        assertTrue(
            patient.checkLogin("John", "password123")
        );
    }

    /**
     * Tests login with an incorrect password.
     */
    public void testLoginWrongPassword() {
        assertFalse(
            patient.checkLogin("John", "wrongPassword")
        );
    }

    /**
     * Tests login with an incorrect username.
     */
    public void testLoginWrongUsername() {
        assertFalse(
            patient.checkLogin("Jane", "password123")
        );
    }

    /**
     * Tests login with an empty username.
     */
    public void testLoginEmptyUsername() {
        assertFalse(
            patient.checkLogin("", "password123")
        );
    }

    /**
     * Tests login with an empty password.
     */
    public void testLoginEmptyPassword() {
        assertFalse(
            patient.checkLogin("John", "")
        );
    }

    /**
     * Tests login with null credentials.
     */
    public void testLoginNullCredentials() {
        assertFalse(patient.checkLogin(null, null));
    }

    /**
     * Tests changing the patient's name.
     */
    public void testSetName() {
        patient.setName("Jane");

        assertEquals("Jane", patient.getName());
        assertTrue(
            patient.checkLogin("Jane", "password123")
        );
    }

    /**
     * Tests rejecting an empty name.
     */
    public void testSetNameEmpty() {
        try {
            patient.setName("");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals("John", patient.getName());
        }
    }

    /**
     * Tests rejecting a null name.
     */
    public void testSetNameNull() {
        try {
            patient.setName(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals("John", patient.getName());
        }
    }

    /**
     * Tests changing the patient's password.
     */
    public void testSetPassword() {
        patient.setPassword("newPassword123");

        assertTrue(
            patient.checkLogin("John", "newPassword123")
        );

        assertFalse(
            patient.checkLogin("John", "password123")
        );
    }

    /**
     * Tests rejecting an empty password.
     */
    public void testSetPasswordEmpty() {
        try {
            patient.setPassword("");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertTrue(
                patient.checkLogin("John", "password123")
            );
        }
    }

    /**
     * Tests rejecting a null password.
     */
    public void testSetPasswordNull() {
        try {
            patient.setPassword(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertTrue(
                patient.checkLogin("John", "password123")
            );
        }
    }

    /**
     * Tests rejecting a zero patient ID.
     */
    public void testConstructorZeroID() {
        try {
            new Patient(0, "John", "password123");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting a negative patient ID.
     */
    public void testConstructorNegativeID() {
        try {
            new Patient(-1, "John", "password123");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting an empty name
     * in the constructor.
     */
    public void testConstructorEmptyName() {
        try {
            new Patient(1, "", "password123");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting a null name
     * in the constructor.
     */
    public void testConstructorNullName() {
        try {
            new Patient(1, null, "password123");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting an empty password
     * in the constructor.
     */
    public void testConstructorEmptyPassword() {
        try {
            new Patient(1, "John", "");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests rejecting a null password
     * in the constructor.
     */
    public void testConstructorNullPassword() {
        try {
            new Patient(1, "John", null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Tests that the patient has a
     * functioning medication tracker.
     */
    public void testMedicationTracker() {
        MedicationTracker tracker = patient.getTracker();

        Medication medication = new Medication(
            "Vitamin D", 1000, 1, 30
        );

        tracker.addMedication(medication);

        assertEquals(1, tracker.getMedications().size());
    }

    /**
     * Tests that the patient has a
     * functioning appointment manager.
     */
    public void testAppointmentManager() {
        AppointmentManager manager =
            patient.getAppointmentManager();

        assertNotNull(manager);
        assertTrue(manager.getAppointments().isEmpty());
    }
}

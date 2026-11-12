
import student.TestCase;

public class PatientTest extends TestCase {

    private Patient patient;

    public void setUp() {
        patient = new Patient(1, "John Smith", "password123");
    }

    // Constructor
    public void testConstructor() {
        assertEquals(1, patient.getPatientID());
        assertEquals("John Smith", patient.getName());
        assertEquals("password123", patient.getPassword());

        assertNotNull(patient.getTracker());
        assertNotNull(patient.getAppointment());
    }

    public void testConstructorInvalidID() {
        try {
            new Patient(-1, "John Smith", "password123");
            fail("Expected exception");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    public void testConstructorInvalidName() {
        try {
            new Patient(1, "", "password123");
            fail("Expected exception");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    public void testConstructorInvalidPassword() {
        try {
            new Patient(1, "John Smith", "");
            fail("Expected exception");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    // Getters
    public void testGetPatientID() {
        assertEquals(1, patient.getPatientID());
    }

    public void testGetName() {
        assertEquals("John Smith", patient.getName());
    }

    public void testGetPassword() {
        assertEquals("password123", patient.getPassword());
    }

    public void testGetTracker() {
        assertNotNull(patient.getTracker());
    }

    public void testGetAppointment() {
        assertNotNull(patient.getAppointment());
    }

    // Setters
    public void testSetName() {
        patient.setName("Jane Smith");
        assertEquals("Jane Smith", patient.getName());
    }

    public void testSetNameInvalid() {
        try {
            patient.setName("");
            fail("Expected exception");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    public void testSetPassword() {
        patient.setPassword("newPassword");
        assertEquals("newPassword", patient.getPassword());
    }

    public void testSetPasswordInvalid() {
        try {
            patient.setPassword("");
            fail("Expected exception");
        }
        catch (IllegalArgumentException e) {
            // Expected
        }
    }

    // checkLogin()
    public void testCheckLoginCorrect() {
        assertTrue(patient.checkLogin("John Smith", "password123"));
    }

    public void testCheckLoginWrongPassword() {
        assertFalse(patient.checkLogin("John Smith", "wrongPassword"));
    }

    public void testCheckLoginWrongUsername() {
        assertFalse(patient.checkLogin("Jane Smith", "password123"));
    }

    public void testCheckLoginNull() {
        assertFalse(patient.checkLogin(null, "password123"));
        assertFalse(patient.checkLogin("John Smith", null));
    }
}

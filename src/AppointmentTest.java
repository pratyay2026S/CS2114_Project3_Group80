
import student.TestCase;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentTest extends TestCase {

    // Helper method
    private Appointment createAppointment() {
        return new Appointment(
            "Dr. Smith",
            LocalDate.of(2026, 12, 10),
            LocalTime.of(10, 30),
            "Virginia Tech Health Center"
        );
    }

    // Test constructor
    public void testConstructor() {
        Appointment appointment = createAppointment();

        assertEquals("Dr. Smith", appointment.getDoctor());
        assertEquals(LocalDate.of(2026, 12, 10), appointment.getDate());
        assertEquals(LocalTime.of(10, 30), appointment.getTime());
        assertEquals("Virginia Tech Health Center", appointment.getLocation());
    }

    // Test getDoctor()
    public void testGetDoctor() {
        Appointment appointment = createAppointment();
        assertEquals("Dr. Smith", appointment.getDoctor());
    }

    // Test getDate()
    public void testGetDate() {
        Appointment appointment = createAppointment();
        assertEquals(LocalDate.of(2026, 12, 10), appointment.getDate());
    }

    // Test getTime()
    public void testGetTime() {
        Appointment appointment = createAppointment();
        assertEquals(LocalTime.of(10, 30), appointment.getTime());
    }

    // Test getLocation()
    public void testGetLocation() {
        Appointment appointment = createAppointment();
        assertEquals("Virginia Tech Health Center", appointment.getLocation());
    }

    // Test setDoctor()
    public void testSetDoctor() {
        Appointment appointment = createAppointment();

        appointment.setDoctor("Dr. Johnson");

        assertEquals("Dr. Johnson", appointment.getDoctor());
    }

    // Test setDateTime()
    public void testSetDateTime() {
        Appointment appointment = createAppointment();

        LocalDate newDate = LocalDate.of(2026, 12, 15);
        LocalTime newTime = LocalTime.of(14, 0);

        appointment.setDateTime(newDate, newTime);

        assertEquals(newDate, appointment.getDate());
        assertEquals(newTime, appointment.getTime());
    }

    // Test setDateTime() with past date
    public void testSetDateTimePastDate() {
        Appointment appointment = createAppointment();

        LocalDate pastDate = LocalDate.of(2020, 5, 10);
        LocalTime newTime = LocalTime.of(14, 0);

        try {
            appointment.setDateTime(pastDate, newTime);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Test setLocation()
    public void testSetLocation() {
        Appointment appointment = createAppointment();

        appointment.setLocation("Blacksburg Medical Center");

        assertEquals("Blacksburg Medical Center", appointment.getLocation());
    }
}

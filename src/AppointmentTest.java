
import student.TestCase;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentTest extends TestCase {

    private Appointment appointment;
    private LocalDate futureDate;
    private LocalTime appointmentTime;

    /**
     * Sets up the test appointment.
     */
    public void setUp() {
        futureDate = LocalDate.now().plusDays(7);
        appointmentTime = LocalTime.of(10, 30);

        appointment = new Appointment(
            "Dr. Smith",
            futureDate,
            appointmentTime,
            "Medical Center"
        );
    }

    /**
     * Tests the appointment constructor and getters.
     */
    public void testConstructorAndGetters() {
        assertEquals("Dr. Smith", appointment.getDoctor());
        assertEquals(futureDate, appointment.getDate());
        assertEquals(appointmentTime, appointment.getTime());
        assertEquals(
            "Medical Center",
            appointment.getLocation()
        );
    }

    /**
     * Tests changing the doctor.
     */
    public void testSetDoctor() {
        appointment.setDoctor("Dr. Jones");

        assertEquals("Dr. Jones", appointment.getDoctor());
    }

    /**
     * Tests rejecting an empty doctor name.
     */
    public void testSetDoctorEmpty() {
        try {
            appointment.setDoctor("");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Dr. Smith", appointment.getDoctor());
        }
    }

    /**
     * Tests rejecting a null doctor name.
     */
    public void testSetDoctorNull() {
        try {
            appointment.setDoctor(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Dr. Smith", appointment.getDoctor());
        }
    }

    /**
     * Tests changing the appointment location.
     */
    public void testSetLocation() {
        appointment.setLocation("Health Clinic");

        assertEquals(
            "Health Clinic",
            appointment.getLocation()
        );
    }

    /**
     * Tests rejecting an empty location.
     */
    public void testSetLocationEmpty() {
        try {
            appointment.setLocation("");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                "Medical Center",
                appointment.getLocation()
            );
        }
    }

    /**
     * Tests rejecting a null location.
     */
    public void testSetLocationNull() {
        try {
            appointment.setLocation(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                "Medical Center",
                appointment.getLocation()
            );
        }
    }

    /**
     * Tests changing the appointment date and time.
     */
    public void testSetDateTime() {
        LocalDate newDate = LocalDate.now().plusDays(14);
        LocalTime newTime = LocalTime.of(15, 45);

        appointment.setDateTime(newDate, newTime);

        assertEquals(newDate, appointment.getDate());
        assertEquals(newTime, appointment.getTime());
    }

    /**
     * Tests rejecting a past appointment date.
     */
    public void testSetDateTimePast() {
        try {
            appointment.setDateTime(
                LocalDate.now().minusDays(1),
                LocalTime.of(12, 0)
            );

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(futureDate, appointment.getDate());
            assertEquals(appointmentTime, appointment.getTime());
        }
    }

    /**
     * Tests rejecting a null date.
     */
    public void testSetDateTimeNullDate() {
        try {
            appointment.setDateTime(
                null,
                LocalTime.of(12, 0)
            );

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(futureDate, appointment.getDate());
            assertEquals(appointmentTime, appointment.getTime());
        }
    }

    /**
     * Tests rejecting a null time.
     */
    public void testSetDateTimeNullTime() {
        try {
            appointment.setDateTime(
                LocalDate.now().plusDays(10),
                null
            );

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals(futureDate, appointment.getDate());
            assertEquals(appointmentTime, appointment.getTime());
        }
    }

    /**
     * Tests rejecting a null appointment date in the constructor.
     */
    public void testConstructorNullDate() {
        try {
            new Appointment(
                "Dr. Smith",
                null,
                LocalTime.of(10, 30),
                "Medical Center"
            );

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    /**
     * Tests rejecting a null appointment time.
     */
    public void testConstructorNullTime() {
        try {
            new Appointment(
                "Dr. Smith",
                LocalDate.now().plusDays(7),
                null,
                "Medical Center"
            );

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    /**
     * Tests rejecting an empty doctor in the constructor.
     */
    public void testConstructorEmptyDoctor() {
        try {
            new Appointment(
                "",
                LocalDate.now().plusDays(7),
                LocalTime.of(10, 30),
                "Medical Center"
            );

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    /**
     * Tests rejecting an empty location in the constructor.
     */
    public void testConstructorEmptyLocation() {
        try {
            new Appointment(
                "Dr. Smith",
                LocalDate.now().plusDays(7),
                LocalTime.of(10, 30),
                ""
            );

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}

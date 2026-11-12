
import student.TestCase;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentManagerTest extends TestCase {

    private AppointmentManager manager;

    // Creates a fresh manager before each test
    public void setUp() {
        manager = new AppointmentManager();
    }

    // Helper method to create appointments
    private Appointment createAppointment() {
        return new Appointment(
            "Dr. Smith",
            LocalDate.now().plusDays(5),
            LocalTime.of(10, 30),
            "Virginia Tech Health Center"
        );
    }

    // Helper method to create a second appointment
    private Appointment createSecondAppointment() {
        return new Appointment(
            "Dr. Johnson",
            LocalDate.now().plusDays(6),
            LocalTime.of(14, 0),
            "Blacksburg Medical Center"
        );
    }

    // Test addObserver()
    public void testAddObserver() {
        TestObserver observer = new TestObserver();

        manager.addObserver(observer);
        manager.scheduleAppointment(createAppointment());

        assertTrue(observer.wasUpdated);
    }

    // Test addObserver() with null
    public void testAddObserverNull() {
        try {
            manager.addObserver(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Test scheduleAppointment()
    public void testScheduleAppointment() {
        Appointment appointment = createAppointment();

        manager.scheduleAppointment(appointment);

        assertEquals(1, manager.getAppointments().size());
        assertEquals(appointment, manager.getAppointments().get(0));
    }

    // Test scheduleAppointment() with null
    public void testScheduleAppointmentNull() {
        try {
            manager.scheduleAppointment(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Test scheduling an appointment in the past
    public void testScheduleAppointmentPastDate() {
        Appointment appointment = new Appointment(
            "Dr. Smith",
            LocalDate.now().minusDays(5),
            LocalTime.of(10, 30),
            "Virginia Tech Health Center"
        );

        try {
            manager.scheduleAppointment(appointment);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Test scheduling a double-booked appointment
    public void testScheduleAppointmentDuplicate() {
        Appointment appointment1 = createAppointment();
        Appointment appointment2 = createAppointment();

        manager.scheduleAppointment(appointment1);

        try {
            manager.scheduleAppointment(appointment2);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }

        assertEquals(1, manager.getAppointments().size());
    }

    // Test reschedule()
    public void testReschedule() {
        Appointment appointment = createAppointment();
        manager.scheduleAppointment(appointment);

        LocalDate newDate = LocalDate.now().plusDays(10);
        LocalTime newTime = LocalTime.of(15, 0);

        manager.reschedule(0, newDate, newTime);

        assertEquals(newDate, appointment.getDate());
        assertEquals(newTime, appointment.getTime());
    }

    // Test reschedule() with invalid appointment number
    public void testRescheduleInvalidNumber() {
        try {
            manager.reschedule(0,
                LocalDate.now().plusDays(10),
                LocalTime.of(15, 0));

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Test reschedule() with a past date
    public void testReschedulePastDate() {
        manager.scheduleAppointment(createAppointment());

        try {
            manager.reschedule(0,
                LocalDate.now().minusDays(5),
                LocalTime.of(15, 0));

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Test reschedule() with a double-booked time
    public void testRescheduleDuplicate() {
        Appointment appointment1 = createAppointment();
        Appointment appointment2 = createSecondAppointment();

        manager.scheduleAppointment(appointment1);
        manager.scheduleAppointment(appointment2);

        try {
            manager.reschedule(0,
                appointment2.getDate(),
                appointment2.getTime());

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Test cancel()
    public void testCancel() {
        manager.scheduleAppointment(createAppointment());

        manager.cancel(0);

        assertEquals(0, manager.getAppointments().size());
    }

    // Test cancel() with invalid appointment number
    public void testCancelInvalidNumber() {
        try {
            manager.cancel(0);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    // Test notifyObservers()
    public void testNotifyObservers() {
        TestObserver observer = new TestObserver();

        manager.addObserver(observer);
        manager.scheduleAppointment(createAppointment());

        assertTrue(observer.wasUpdated);
        assertEquals(1, observer.appointments.size());
    }

    // Test getAppointments()
    public void testGetAppointments() {
        Appointment appointment = createAppointment();

        manager.scheduleAppointment(appointment);

        List<Appointment> appointments = manager.getAppointments();

        assertEquals(1, appointments.size());
        assertEquals(appointment, appointments.get(0));
    }

    // Test getAppointments() returns a copy
    public void testGetAppointmentsCopy() {
        manager.scheduleAppointment(createAppointment());

        List<Appointment> appointments = manager.getAppointments();

        appointments.clear();

        assertEquals(1, manager.getAppointments().size());
    }

    // Helper observer class for testing notifications
    private class TestObserver implements Observer {

        private boolean wasUpdated = false;
        private List<String> appointments;

        public void update(List<String> appointments) {
            wasUpdated = true;
            this.appointments = appointments;
        }
    }
}

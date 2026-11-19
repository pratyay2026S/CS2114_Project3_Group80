
import student.TestCase;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentManagerTest extends TestCase {

    private AppointmentManager manager;
    private Appointment appointment1;
    private Appointment appointment2;

    public void setUp() {
        manager = new AppointmentManager();

        appointment1 = new Appointment(
            "Dr. Smith",
            LocalDate.now().plusDays(5),
            LocalTime.of(10, 0),
            "Medical Center"
        );

        appointment2 = new Appointment(
            "Dr. Jones",
            LocalDate.now().plusDays(6),
            LocalTime.of(14, 0),
            "Health Clinic"
        );
    }

    /**
     * Tests scheduling an appointment.
     */
    public void testScheduleAppointment() {
        manager.scheduleAppointment(appointment1);

        assertEquals(1, manager.getAppointments().size());
        assertEquals(
            appointment1,
            manager.getAppointments().get(0)
        );
    }

    /**
     * Tests scheduling multiple appointments.
     */
    public void testScheduleMultipleAppointments() {
        manager.scheduleAppointment(appointment1);
        manager.scheduleAppointment(appointment2);

        assertEquals(2, manager.getAppointments().size());
    }

    /**
     * Tests scheduling a null appointment.
     */
    public void testScheduleNullAppointment() {
        try {
            manager.scheduleAppointment(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }


/**
 * Tests that appointments cannot be scheduled
 * with a date in the past.
 */
public void testSchedulePastAppointment() {
    try {
        Appointment past = new Appointment(
            "Dr. Smith",
            LocalDate.now().minusDays(3),
            LocalTime.of(10, 0),
            "Medical Center"
        );

        manager.scheduleAppointment(past);

        fail("Expected IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
        // Expected: invalid appointments are rejected
    }
}



    /**
     * Tests rejecting duplicate appointment times.
     */
    public void testScheduleDuplicateTime() {
        manager.scheduleAppointment(appointment1);

        Appointment duplicate = new Appointment(
            "Dr. Brown",
            appointment1.getDate(),
            appointment1.getTime(),
            "Other Clinic"
        );

        try {
            manager.scheduleAppointment(duplicate);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    /**
     * Tests rescheduling an appointment.
     */
    public void testRescheduleAppointment() {
        manager.scheduleAppointment(appointment1);

        LocalDate newDate = LocalDate.now().plusDays(10);
        LocalTime newTime = LocalTime.of(15, 30);

        manager.reschedule(0, newDate, newTime);

        Appointment updated =
            manager.getAppointments().get(0);

        assertEquals(newDate, updated.getDate());
        assertEquals(newTime, updated.getTime());
    }

    /**
     * Tests rescheduling with an invalid index.
     */
    public void testRescheduleInvalidIndex() {
        manager.scheduleAppointment(appointment1);

        try {
            manager.reschedule(
                5,
                LocalDate.now().plusDays(10),
                LocalTime.of(12, 0)
            );

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    /**
     * Tests rescheduling to a past date.
     */
    public void testReschedulePastDate() {
        manager.scheduleAppointment(appointment1);

        try {
            manager.reschedule(
                0,
                LocalDate.now().minusDays(1),
                LocalTime.of(12, 0)
            );

            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    /**
     * Tests canceling an appointment.
     */
    public void testCancelAppointment() {
        manager.scheduleAppointment(appointment1);
        manager.scheduleAppointment(appointment2);

        manager.cancel(0);

        assertEquals(1, manager.getAppointments().size());

        assertEquals(
            appointment2,
            manager.getAppointments().get(0)
        );
    }

    /**
     * Tests canceling with an invalid index.
     */
    public void testCancelInvalidIndex() {
        try {
            manager.cancel(0);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    /**
     * Tests that appointments are initially empty.
     */
    public void testEmptyAppointments() {
        assertTrue(manager.getAppointments().isEmpty());
    }

    /**
     * Tests that returned appointment lists
     * cannot modify the manager's internal list.
     */
    public void testGetAppointmentsCopy() {
        manager.scheduleAppointment(appointment1);

        List<Appointment> appointments =
            manager.getAppointments();

        appointments.clear();

        assertEquals(1, manager.getAppointments().size());
    }

    /**
     * Tests rejecting a null observer.
     */
    public void testAddNullObserver() {
        try {
            manager.addObserver(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    /**
     * Tests notifying observers when an
     * appointment is scheduled.
     */
    public void testObserverNotification() {

        TestObserver observer = new TestObserver();

        manager.addObserver(observer);
        manager.scheduleAppointment(appointment1);

        assertTrue(observer.wasUpdated());
        assertEquals(1, observer.getLastUpdate().size());
    }

    /**
     * Tests notification after cancellation.
     */
    public void testObserverAfterCancel() {

        TestObserver observer = new TestObserver();

        manager.addObserver(observer);
        manager.scheduleAppointment(appointment1);

        manager.cancel(0);

        assertTrue(observer.wasUpdated());
        assertTrue(observer.getLastUpdate().isEmpty());
    }

    /**
     * Simple observer used only for testing.
     */
    private static class TestObserver implements Observer {

        private boolean updated;
        private List<String> lastUpdate;

        public void update(List<String> appointments) {
            updated = true;
            lastUpdate = appointments;
        }

        public boolean wasUpdated() {
            return updated;
        }

        public List<String> getLastUpdate() {
            return lastUpdate;
        }
    }
}

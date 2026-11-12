
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {

    // Instance variables
    private ArrayList<Appointment> appointments;
    private List<Observer> observers;

    // Constructor
    public AppointmentManager() {
        appointments = new ArrayList<Appointment>();
        observers = new ArrayList<Observer>();
    }

    // Adds an observer
    public void addObserver(Observer o) {
        if (o == null) {
            throw new IllegalArgumentException("Observer cannot be null.");
        }

        observers.add(o);
    }

    // Schedules a new appointment
    public void scheduleAppointment(Appointment a) {
        if (a == null) {
            throw new IllegalArgumentException("Appointment cannot be null.");
        }

        LocalDateTime appointmentDateTime =
            LocalDateTime.of(a.getDate(), a.getTime());

        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                "Appointment cannot be scheduled in the past.");
        }

        // Check for double-booking
        for (Appointment existing : appointments) {
            if (existing.getDate().equals(a.getDate())
                && existing.getTime().equals(a.getTime())) {

                throw new IllegalArgumentException(
                    "This appointment time is already booked.");
            }
        }

        appointments.add(a);
        notifyObservers();
    }

    // Reschedules an existing appointment
    public void reschedule(int appointmentNum,
        LocalDate date, LocalTime time) {

        // Check whether appointment exists
        if (appointmentNum < 0 || appointmentNum >= appointments.size()) {
            throw new IllegalArgumentException(
                "Appointment does not exist.");
        }

        // Check whether the new date and time are in the future
        LocalDateTime newDateTime = LocalDateTime.of(date, time);

        if (newDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                "Appointment cannot be rescheduled to the past.");
        }

        // Check for double-booking
        for (int i = 0; i < appointments.size(); i++) {
            if (i != appointmentNum) {
                Appointment existing = appointments.get(i);

                if (existing.getDate().equals(date)
                    && existing.getTime().equals(time)) {

                    throw new IllegalArgumentException(
                        "This appointment time is already booked.");
                }
            }
        }

        // Update appointment date and time
        appointments.get(appointmentNum).setDateTime(date, time);

        notifyObservers();
    }

    // Cancels an existing appointment
    public void cancel(int appointmentNum) {

        if (appointmentNum < 0 || appointmentNum >= appointments.size()) {
            throw new IllegalArgumentException(
                "Appointment does not exist.");
        }

        appointments.remove(appointmentNum);

        notifyObservers();
    }

    // Notifies all observers of changes
    public void notifyObservers() {

        List<String> appointmentInfo = new ArrayList<String>();

        for (Appointment a : appointments) {
            appointmentInfo.add(
                "Doctor: " + a.getDoctor()
                + ", Date: " + a.getDate()
                + ", Time: " + a.getTime()
                + ", Location: " + a.getLocation()
            );
        }

        for (Observer o : observers) {
            o.update(appointmentInfo);
        }
    }

    // Returns all appointments
    public List<Appointment> getAppointments() {
        return new ArrayList<Appointment>(appointments);
    }
}


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class Appointment {

    private String doctor;
    private LocalDate date;
    private LocalTime time;
    private String location;

    /**
     * Creates a new appointment.
     */
    public Appointment(
        String doctor,
        LocalDate date,
        LocalTime time,
        String location
    ) {
        setDoctor(doctor);
        setLocation(location);
        setDateTime(date, time);
    }

    /**
     * Returns the doctor's name.
     */
    public String getDoctor() {
        return doctor;
    }

    /**
     * Updates the doctor's name.
     */
    public void setDoctor(String doctor) {
        if (doctor == null || doctor.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Doctor name cannot be empty."
            );
        }

        this.doctor = doctor;
    }

    /**
     * Returns the appointment date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the appointment time.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Returns the appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Updates the appointment location.
     */
    public void setLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Location cannot be empty."
            );
        }

        this.location = location;
    }

    /**
     * Updates the appointment date and time.
     */
    public void setDateTime(
        LocalDate date,
        LocalTime time
    ) {
        if (date == null || time == null) {
            throw new IllegalArgumentException(
                "Date and time cannot be null."
            );
        }

        if (date.atTime(time).isBefore(
            LocalDateTime.now()
        )) {
            throw new IllegalArgumentException(
                "Appointment must be in the future."
            );
        }

        this.date = date;
        this.time = time;
    }
}

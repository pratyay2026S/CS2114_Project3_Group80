
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    // Instance variables
    private String doctor;
    private LocalDate date;
    private LocalTime time;
    private String location;

    // Constructor
    public Appointment(String doctor, LocalDate date, LocalTime time, String location) {
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    // Getters
    public String getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    // Setters
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setDateTime(LocalDate date, LocalTime time) {

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Appointment cannot be in the past.");
        }

        this.date = date;
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}

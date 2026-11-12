
public class Patient {

    // Instance variables
    private final int patientID;
    private String name;
    private String password;
    private final MedicationTracker tracker;
    private final AppointmentManager appointment;

    // Constructor
    public Patient(int patientID, String name, String password) {
        if (patientID <= 0) {
            throw new IllegalArgumentException("Invalid patient ID.");
        }

        if (name == null || name.trim().isEmpty()
            || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Name and password are required.");
        }

        this.patientID = patientID;
        this.name = name;
        this.password = password;
        this.tracker = new MedicationTracker();
        this.appointment = new AppointmentManager();
    }

    // Getters
    public int getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public MedicationTracker getTracker() {
        return tracker;
    }

    public AppointmentManager getAppointment() {
        return appointment;
    }

    // Setters
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        this.name = name;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        this.password = password;
    }

    // Checks login credentials
    public boolean checkLogin(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        return this.name.equals(username)
            && this.password.equals(password);
    }
}

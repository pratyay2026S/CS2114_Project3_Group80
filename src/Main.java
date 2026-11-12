
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {

        // -----------------------------
        // 1. Set up patient login
        // -----------------------------

        LoginView loginView = new LoginView();
        PatientController patientController =
                new PatientController(loginView);

        loginView.showCreateAccount();

        patientController.register(1, "John", "password123");

        loginView.showLogin();
        patientController.login("John", "password123");

        Patient patient = patientController.getPatient();

        if (patient == null) {
            System.out.println("Unable to create patient.");
            return;
        }

        // -----------------------------
        // 2. Set up medication tracking
        // -----------------------------

        MedicationView medicationView = new MedicationView();

        MedicationController medicationController =
                new MedicationController(
                        patient.getTracker(),
                        medicationView
                );

        Medication medication =
                new Medication("Vitamin D", 1000, 1, 30);

        medicationController.addMedication(medication);

        System.out.println("\nTaking next medication dose:");
        medicationController.takeDose();

        // -----------------------------
        // 3. Set up appointment tracking
        // -----------------------------

        AppointmentManager appointmentManager =
                new AppointmentManager();

        AppointmentView appointmentView = new AppointmentView();

        AppointmentController appointmentController =
                new AppointmentController(
                        appointmentManager,
                        appointmentView
                );

        Appointment appointment = new Appointment(
                "Dr. Smith",
                LocalDate.now().plusDays(7),
                LocalTime.of(10, 0),
                "Main Medical Center"
        );

        appointmentController.schedule(appointment);

        System.out.println("\nApplication test complete!");
    }
}

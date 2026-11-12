
public class PatientController {

    private Patient patient;
    private LoginView loginView;

    /**
     * Creates a PatientController.
     */
    public PatientController(LoginView loginView) {
        if (loginView == null) {
            throw new IllegalArgumentException("LoginView cannot be null.");
        }

        this.loginView = loginView;
    }

    /**
     * Registers a new patient.
     */
    public void register(int patientID, String name, String password) {
        try {
            patient = new Patient(patientID, name, password);
        }
        catch (IllegalArgumentException e) {
            loginView.showError();
        }
    }

    /**
     * Checks the patient's login information.
     */
    public void login(String username, String password) {
        if (patient == null) {
            loginView.showError();
            return;
        }

        if (patient.checkLogin(username, password)) {
            System.out.println("Login successful!");
        }
        else {
            loginView.showError();
        }
    }

    /**
     * Adds the patient's initial medication.
     */
    public void setupMedication(Medication medication) {
        if (patient == null || medication == null) {
            loginView.showError();
            return;
        }

        patient.getTracker().addMedication(medication);
    }

    /**
     * Returns the current patient.
     */
    public Patient getPatient() {
        return patient;
    }
}

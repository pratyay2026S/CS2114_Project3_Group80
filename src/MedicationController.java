
public class MedicationController {

    private MedicationTracker tracker;
    private MedicationView view;

    /**
     * Creates a MedicationController.
     */
    public MedicationController(MedicationTracker tracker, MedicationView view) {
        if (tracker == null || view == null) {
            throw new IllegalArgumentException("Tracker and view cannot be null.");
        }

        this.tracker = tracker;
        this.view = view;

        // Register the view as an observer.
        tracker.addObserver(view);
    }

    /**
     * Adds a medication to the tracker.
     */
    public void addMedication(Medication m) {
        try {
            tracker.addMedication(m);
        }
        catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    /**
     * Removes a medication by name.
     */
    public void removeMedication(String medicationName) {
        try {
            boolean removed = tracker.removeMedication(medicationName);

            if (!removed) {
                view.showError("Medication not found: " + medicationName);
            }
        }
        catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    /**
     * Updates an existing medication.
     */
    public void updateMedication(Medication m) {
        try {
            tracker.updateMedication(m);
        }
        catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    /**
     * Takes the next medication dose.
     */
    public void takeDose() {
        try {
            tracker.takeNextDose();
        }
        catch (IllegalStateException e) {
            view.showError(e.getMessage());
        }
    }
}

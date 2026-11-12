
import java.util.ArrayList;
import java.util.List;

public class MedicationView implements Observer {

    private List<String> displayedMedications;

    /**
     * Creates an empty MedicationView.
     */
    public MedicationView() {
        displayedMedications = new ArrayList<String>();
    }

    /**
     * Updates the displayed medication list.
     */
    @Override
    public void update(List<String> medications) {
        displayedMedications = new ArrayList<String>(medications);

        System.out.println("Medication List:");

        for (String medication : displayedMedications) {
            System.out.println(medication);
        }
    }

    /**
     * Displays an error message.
     */
    public void showError() {
        System.out.println("An error occurred while managing medications.");
    }

    /**
     * Returns the currently displayed medications.
     */
    public List<String> getDisplayedMedications() {
        return new ArrayList<String>(displayedMedications);
    }
}

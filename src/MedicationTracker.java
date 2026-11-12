
import java.util.ArrayList;
import java.util.List;

public class MedicationTracker {

    // Instance variables
    private ArrayList<Medication> medications;
    private LinkedQueue<Medication> doseQueue;
    private List<Observer> observers;

    // Constructor
    public MedicationTracker() {
        medications = new ArrayList<Medication>();
        doseQueue = new LinkedQueue<Medication>();
        observers = new ArrayList<Observer>();
    }

    // Adds an observer
    public void addObserver(Observer o) {
        if (o == null) {
            throw new IllegalArgumentException("Observer cannot be null.");
        }

        observers.add(o);
    }

    // Notifies observers
    public void notifyObservers() {
        List<String> medicationInfo = new ArrayList<String>();

        for (Medication m : medications) {
            medicationInfo.add(
                "Name: " + m.getName()
                + ", Dosage: " + m.getDosage()
                + ", Frequency: " + m.getFrequency()
                + ", Quantity: " + m.getQuantity()
            );
        }

        for (Observer o : observers) {
            o.update(medicationInfo);
        }
    }

    // Adds a medication
    public void addMedication(Medication m) {
        if (m == null) {
            throw new IllegalArgumentException("Medication cannot be null.");
        }

        medications.add(m);

        // Add weekly doses to the queue
        for (int i = 0; i < m.getFrequency() * 7; i++) {
            doseQueue.enqueue(m);
        }

        notifyObservers();
    }

    // Removes a medication by name
    public boolean removeMedication(String medicationName) {
        if (medicationName == null || medicationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Medication name cannot be empty.");
        }

        Medication removed = null;

        for (Medication m : medications) {
            if (m.getName().equalsIgnoreCase(medicationName)) {
                removed = m;
                break;
            }
        }

        if (removed == null) {
            return false;
        }

        medications.remove(removed);

        // Rebuild the queue without the removed medication
        List<Medication> remainingDoses = new ArrayList<Medication>();

        while (!doseQueue.isEmpty()) {
            Medication current = doseQueue.dequeue();

            if (current != removed) {
                remainingDoses.add(current);
            }
        }

        for (Medication m : remainingDoses) {
            doseQueue.enqueue(m);
        }

        notifyObservers();
        return true;
    }

    // Takes the next dose
    public Medication takeNextDose() {
        if (doseQueue.isEmpty()) {
            throw new IllegalStateException("No doses remaining.");
        }

        Medication next = doseQueue.peek();

        if (next.getQuantity() <= 0) {
            throw new IllegalStateException("Medication supply is empty.");
        }

        doseQueue.dequeue();
        next.setQuantity(next.getQuantity() - 1);

        notifyObservers();

        return next;
    }

    // Returns medications running low
    public List<Medication> getLowSupply() {
        List<Medication> lowSupply = new ArrayList<Medication>();

        for (Medication m : medications) {
            if (m.isRunningLow(m.getQuantity())) {
                lowSupply.add(m);
            }
        }

        return lowSupply;
    }

    // Returns all medications
    public List<Medication> getMedications() {
        return new ArrayList<Medication>(medications);
    }
}


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MedicationTracker {

    // Instance variables
    private ArrayList<Medication> medications;
    private LinkedQueue<Medication> doseQueue;
    private List<Observer> observers;
    private Map<Medication, Integer> dosesTaken;

    // Constructor
    public MedicationTracker() {
        medications = new ArrayList<Medication>();
        doseQueue = new LinkedQueue<Medication>();
        observers = new ArrayList<Observer>();
        dosesTaken = new HashMap<Medication, Integer>();
    }

    // Adds an observer
    public void addObserver(Observer o) {
        if (o == null) {
            throw new IllegalArgumentException("Observer cannot be null.");
        }

        observers.add(o);
    }

    // Removes an observer when its view is closed
    public void removeObserver(Observer o) {
        observers.remove(o);
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

        for (Medication existing : medications) {
            if (existing.getName().equalsIgnoreCase(m.getName())) {
                throw new IllegalArgumentException(
                    "A medication with this name already exists.");
            }
        }

        medications.add(m);
        dosesTaken.put(m, 0);

        // Add weekly doses to the queue
        for (int i = 0; i < m.getFrequency() * 7; i++) {
            doseQueue.enqueue(m);
        }

        notifyObservers();
    }

    /**
     * Updates medication details and adjusts the remaining weekly doses.
     * Doses already taken are not restored when the frequency changes.
     */
    public void updateMedication(Medication medication) {
        if (medication == null) {
            throw new IllegalArgumentException("Medication cannot be null.");
        }

        for (Medication existing : medications) {
            if (existing.getName().equalsIgnoreCase(medication.getName())) {
                if (existing.getFrequency() != medication.getFrequency()) {
                    List<Medication> remaining = new ArrayList<Medication>();
                    while (!doseQueue.isEmpty()) {
                        Medication dose = doseQueue.dequeue();
                        remaining.add(dose);
                    }
                    int newCount = Math.max(0,
                        medication.getFrequency() * 7 - dosesTaken.get(existing));
                    boolean inserted = false;
                    for (Medication dose : remaining) {
                        if (dose == existing) {
                            if (!inserted) {
                                for (int i = 0; i < newCount; i++) {
                                    doseQueue.enqueue(existing);
                                }
                                inserted = true;
                            }
                        }
                        else {
                            doseQueue.enqueue(dose);
                        }
                    }
                    if (!inserted) {
                        for (int i = 0; i < newCount; i++) {
                            doseQueue.enqueue(existing);
                        }
                    }
                }
                existing.setDosage(medication.getDosage());
                existing.setFrequency(medication.getFrequency());
                existing.setQuantity(medication.getQuantity());
                notifyObservers();
                return;
            }
        }
        throw new IllegalArgumentException(
            "Medication not found: " + medication.getName());
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
        dosesTaken.remove(removed);

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
        dosesTaken.put(next, dosesTaken.get(next) + 1);

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

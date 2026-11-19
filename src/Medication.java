
public class Medication {

    private String name;
    private int dosage;
    private int frequency;
    private int quantity;

    /**
     * Creates a new medication.
     */
    public Medication(
        String name,
        int dosage,
        int frequency,
        int quantity
    ) {
        setName(name);
        setDosage(dosage);
        setFrequency(frequency);
        setQuantity(quantity);
    }

    /**
     * Returns the medication name.
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the medication name.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Medication name cannot be empty."
            );
        }

        this.name = name;
    }

    /**
     * Returns the medication dosage.
     */
    public int getDosage() {
        return dosage;
    }

    /**
     * Updates the dosage.
     */
    public void setDosage(int dosage) {
        if (dosage <= 0) {
            throw new IllegalArgumentException(
                "Dosage must be greater than zero."
            );
        }

        this.dosage = dosage;
    }

    /**
     * Returns the number of doses per day.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Updates the number of doses per day.
     */
    public void setFrequency(int frequency) {
        if (frequency <= 0) {
            throw new IllegalArgumentException(
                "Frequency must be greater than zero."
            );
        }

        this.frequency = frequency;
    }

    /**
     * Returns the remaining medication quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Updates the remaining medication quantity.
     */
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(
                "Quantity cannot be negative."
            );
        }

        this.quantity = quantity;
    }

    /**
     * Checks whether medication supply is low.
     * A quantity of 5 or fewer is considered low.
     */
    public boolean isRunningLow(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(
                "Quantity cannot be negative."
            );
        }

        return quantity <= 5;
    }
}

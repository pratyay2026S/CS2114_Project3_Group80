public class Medication {
    //~ Fields ................................................................
    private String name;
    private int dosage;
    private int frequency;
    private int quantity;
    //~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Create a new Medication object.
     * @param name
     * @param dosage
     * @param frequency
     * @param quantity
     */
    public Medication(String name, int dosage, int frequency, int quantity) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.quantity = quantity;
    }
    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param quantity
     * @return
     */
    //~Public  Methods ........................................................
    
    //need to add exceptions
    
    
    public boolean isRunningLow(int quantity) {
        return quantity <= 5;
    }
    public String getName() {
        return name;
    }
    public int getDosage() {
        return dosage;
    }
    public int getFrequency() {
        return frequency;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDosage(int dosage) {
        this.dosage = dosage;
    }
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

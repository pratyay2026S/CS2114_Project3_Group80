
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MedicationView extends JFrame implements Observer {

    private MedicationTracker tracker;
    private MedicationController controller;
    private DashboardView dashboard;

    private DefaultListModel<String> listModel;
    private JList<String> medicationList;

    private JButton addButton;
    private JButton updateButton;
    private JButton removeButton;
    private JButton takeDoseButton;
    private JButton backButton;

    /**
     * Creates the medication management window.
     */
    public MedicationView(
        MedicationTracker tracker,
        DashboardView dashboard
    ) {
        if (tracker == null || dashboard == null) {
            throw new IllegalArgumentException(
                "Tracker and dashboard cannot be null."
            );
        }

        this.tracker = tracker;
        this.dashboard = dashboard;

        setTitle("CareSync - Medications");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        initializeComponents();

        controller = new MedicationController(tracker, this);

        setupActions();

        // Load the model's current medication information.
        tracker.notifyObservers();
    }

    /**
     * Creates the medication screen components.
     */
    private void initializeComponents() {
        setLayout(new BorderLayout(15, 15));

        JLabel title = new JLabel(
            "Medication Management",
            SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(
            BorderFactory.createEmptyBorder(20, 10, 10, 10)
        );

        add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<String>();
        medicationList = new JList<String>(listModel);
        medicationList.setFont(
            new Font("Arial", Font.PLAIN, 15)
        );
        medicationList.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
            new JScrollPane(medicationList);

        scrollPane.setBorder(
            BorderFactory.createTitledBorder(
                "Your Medications"
            )
        );

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(
            new GridLayout(0, 2, 10, 10)
        );

        buttonPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        );

        addButton = new JButton("Add Medication");
        updateButton = new JButton("Update Medication");
        removeButton = new JButton("Remove Medication");
        takeDoseButton = new JButton("Take Next Dose");
        backButton = new JButton("Back to Dashboard");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(takeDoseButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Connects GUI buttons to controller actions.
     */
    private void setupActions() {

        addButton.addActionListener(e -> addMedication());

        updateButton.addActionListener(e -> updateMedication());

        removeButton.addActionListener(e -> removeMedication());

        takeDoseButton.addActionListener(e -> takeDose());

        backButton.addActionListener(e -> goBack());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });
    }

    /**
     * Opens a form for adding a medication.
     */
    private void addMedication() {
        showMedicationForm(null);
    }

    /**
     * Opens a form to edit the selected medication.
     */
    private void updateMedication() {
        int index = medicationList.getSelectedIndex();
        List<Medication> medications = tracker.getMedications();
        if (index < 0 || index >= medications.size()) {
            showError("Please select a medication to update.");
            return;
        }
        showMedicationForm(medications.get(index));
    }

    /**
     * Adds a medication or edits the selected medication's details.
     */
    private void showMedicationForm(Medication selected) {

        JTextField nameField = new JTextField();
        JTextField dosageField = new JTextField();
        JTextField frequencyField = new JTextField();
        JTextField quantityField = new JTextField();

        if (selected != null) {
            nameField.setText(selected.getName());
            nameField.setEditable(false);
            dosageField.setText(String.valueOf(selected.getDosage()));
            frequencyField.setText(String.valueOf(selected.getFrequency()));
            quantityField.setText(String.valueOf(selected.getQuantity()));
        }

        JPanel panel = new JPanel(
            new GridLayout(4, 2, 10, 10)
        );

        panel.add(new JLabel("Medication Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Dosage:"));
        panel.add(dosageField);

        panel.add(new JLabel("Doses Per Day:"));
        panel.add(frequencyField);

        panel.add(new JLabel("Quantity Available:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            selected == null ? "Add Medication" : "Update Medication",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String name = nameField.getText().trim();

            int dosage = Integer.parseInt(
                dosageField.getText().trim()
            );

            int frequency = Integer.parseInt(
                frequencyField.getText().trim()
            );

            int quantity = Integer.parseInt(
                quantityField.getText().trim()
            );

            Medication medication = new Medication(
                name,
                dosage,
                frequency,
                quantity
            );

            if (selected == null) {
                controller.addMedication(medication);
            }
            else {
                controller.updateMedication(medication);
            }

        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Dosage, frequency, and quantity "
                    + "must be valid whole numbers.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
            );
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Invalid Medication",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Removes the selected medication.
     */
    private void removeMedication() {

        int index = medicationList.getSelectedIndex();

        if (index == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a medication to remove.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        List<Medication> medications =
            tracker.getMedications();

        if (index >= medications.size()) {
            showError("Invalid medication selection. Please select it again.");
            return;
        }

        String name = medications.get(index).getName();

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Remove " + name + "?",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            controller.removeMedication(name);
        }
    }

    /**
     * Takes the next medication from the queue.
     */
    private void takeDose() {

        if (tracker.getMedications().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No medications have been added.",
                "No Medications",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        controller.takeDose();
    }

    /**
     * Returns to the patient dashboard.
     */
    private void goBack() {
        dispose();
        dashboard.setVisible(true);
    }

    /**
     * Releases the model subscription when the window is disposed.
     */
    @Override
    public void dispose() {
        tracker.removeObserver(this);
        super.dispose();
    }

    /**
     * Receives model updates and refreshes the list.
     */
    @Override
    public void update(List<String> medications) {

        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(
                () -> update(medications)
            );
            return;
        }

        listModel.clear();

        for (String medication : medications) {
            listModel.addElement(medication);
        }
    }

    /**
     * Displays a medication error.
     */
    public void showError() {
        showError("Unable to complete the medication action.");
    }

    /**
     * Displays the reason a medication action failed.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Medication Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

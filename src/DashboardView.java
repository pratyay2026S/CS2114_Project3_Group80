
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardView extends JFrame implements Observer {

    private Patient patient;
    private PatientController patientController;

    private JLabel welcomeLabel;
    private JButton medicationButton;
    private JButton appointmentButton;
    private JButton logoutButton;
    private AppointmentView appointmentView;

    /**
     * Creates the patient dashboard.
     */
    public DashboardView(
        Patient patient,
        PatientController patientController
    ) {
        if (patient == null || patientController == null) {
            throw new IllegalArgumentException(
                "Patient and controller cannot be null."
            );
        }

        this.patient = patient;
        this.patientController = patientController;

        setTitle("CareSync - Dashboard");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
    }

    /**
     * Creates the dashboard interface.
     */
    private void initializeComponents() {
        setLayout(new BorderLayout(15, 15));

        JPanel headerPanel = new JPanel(
            new BorderLayout()
        );
        headerPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 10, 20)
        );

        JLabel titleLabel = new JLabel("CareSync Dashboard");
        titleLabel.setFont(
            new Font("Arial", Font.BOLD, 26)
        );

        welcomeLabel = new JLabel(
            "Welcome, " + patient.getName() + "!"
        );
        welcomeLabel.setFont(
            new Font("Arial", Font.PLAIN, 16)
        );

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(welcomeLabel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(
            new GridLayout(2, 1, 20, 20)
        );
        buttonPanel.setBorder(
            BorderFactory.createEmptyBorder(30, 80, 30, 80)
        );

        medicationButton = new JButton(
            "Manage Medications"
        );
        medicationButton.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        appointmentButton = new JButton(
            "Manage Appointments"
        );
        appointmentButton.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        buttonPanel.add(medicationButton);
        buttonPanel.add(appointmentButton);

        add(buttonPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );

        logoutButton = new JButton("Logout");
        bottomPanel.add(logoutButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setupActions();
    }

    /**
     * Connects buttons to their actions.
     */
    private void setupActions() {

        medicationButton.addActionListener(e -> {

            MedicationView medicationView =
                new MedicationView(
                    patient.getTracker(),
                    this
                );

            medicationView.setVisible(true);

            setVisible(false);
        });



        appointmentButton.addActionListener(e -> {
        
            if (appointmentView == null) {
                appointmentView = new AppointmentView(
                    patient.getAppointmentManager(),
                    this
                );
            }
        
            appointmentView.setVisible(true);
            setVisible(false);
        });


        logoutButton.addActionListener(e -> {
            dispose();

            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }

    /**
     * Receives updates from a model.
     */
    @Override
    public void update(List<String> list) {
        welcomeLabel.setText(
            "Welcome, " + patient.getName() + "!"
        );
    }
}

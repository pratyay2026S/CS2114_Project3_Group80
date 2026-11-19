
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AppointmentView extends JFrame implements Observer {

    private AppointmentManager appointmentManager;
    private AppointmentController controller;
    private DashboardView dashboard;

    private DefaultListModel<String> listModel;
    private JList<String> appointmentList;

    private JButton scheduleButton;
    private JButton rescheduleButton;
    private JButton cancelButton;
    private JButton backButton;

    /**
     * Creates the appointment management window.
     */
    public AppointmentView(
        AppointmentManager appointmentManager,
        DashboardView dashboard
    ) {
        if (appointmentManager == null || dashboard == null) {
            throw new IllegalArgumentException(
                "Appointment manager and dashboard cannot be null."
            );
        }

        this.appointmentManager = appointmentManager;
        this.dashboard = dashboard;

        setTitle("CareSync - Appointments");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        initializeComponents();

        controller = new AppointmentController(
            appointmentManager, this
        );

        setupActions();

        appointmentManager.notifyObservers();
    }

    /**
     * Creates the GUI components.
     */
    private void initializeComponents() {
        setLayout(new BorderLayout(15, 15));

        JLabel title = new JLabel(
            "Appointment Management",
            SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(
            BorderFactory.createEmptyBorder(20, 10, 10, 10)
        );

        add(title, BorderLayout.NORTH);

        listModel = new DefaultListModel<String>();
        appointmentList = new JList<String>(listModel);

        appointmentList.setFont(
            new Font("Arial", Font.PLAIN, 15)
        );

        appointmentList.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
            new JScrollPane(appointmentList);

        scrollPane.setBorder(
            BorderFactory.createTitledBorder(
                "Your Appointments"
            )
        );

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(
            new GridLayout(2, 2, 10, 10)
        );

        buttonPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        );

        scheduleButton = new JButton(
            "Schedule Appointment"
        );

        rescheduleButton = new JButton(
            "Reschedule Appointment"
        );

        cancelButton = new JButton(
            "Cancel Appointment"
        );

        backButton = new JButton(
            "Back to Dashboard"
        );

        buttonPanel.add(scheduleButton);
        buttonPanel.add(rescheduleButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Connects buttons to controller actions.
     */
    private void setupActions() {

        scheduleButton.addActionListener(
            e -> scheduleAppointment()
        );

        rescheduleButton.addActionListener(
            e -> rescheduleAppointment()
        );

        cancelButton.addActionListener(
            e -> cancelAppointment()
        );

        backButton.addActionListener(e -> goBack());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBack();
            }
        });
    }

    /**
     * Opens a form to schedule a new appointment.
     */
    private void scheduleAppointment() {

        JTextField doctorField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField locationField = new JTextField();

        JPanel panel = new JPanel(
            new GridLayout(4, 2, 10, 10)
        );

        panel.add(new JLabel("Doctor:"));
        panel.add(doctorField);

        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);

        panel.add(new JLabel("Time (HH:MM):"));
        panel.add(timeField);

        panel.add(new JLabel("Location:"));
        panel.add(locationField);

        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Schedule Appointment",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String doctor = doctorField.getText().trim();
            String location = locationField.getText().trim();

            if (doctor.isEmpty() || location.isEmpty()) {
                showError(
                    "Doctor and location cannot be empty."
                );
                return;
            }

            LocalDate date = LocalDate.parse(
                dateField.getText().trim()
            );

            LocalTime time = LocalTime.parse(
                timeField.getText().trim()
            );

            if (date.atTime(time).isBefore(
                java.time.LocalDateTime.now()
            )) {
                showError(
                    "Appointment must be in the future."
                );
                return;
            }

            Appointment appointment = new Appointment(
                doctor,
                date,
                time,
                location
            );

            controller.schedule(appointment);

        }
        catch (DateTimeParseException ex) {
            showError(
                "Invalid date or time format. "
                    + "Use YYYY-MM-DD and HH:MM."
            );
        }
        catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * Reschedules the selected appointment.
     */
    private void rescheduleAppointment() {

        int index = appointmentList.getSelectedIndex();

        if (index == -1) {
            showError(
                "Please select an appointment to reschedule."
            );
            return;
        }

        List<Appointment> appointments =
            appointmentManager.getAppointments();

        if (index >= appointments.size()) {
            showError("Invalid appointment selection.");
            return;
        }

        Appointment selected = appointments.get(index);

        JTextField dateField = new JTextField(
            selected.getDate().toString()
        );

        JTextField timeField = new JTextField(
            selected.getTime().toString()
        );

        JPanel panel = new JPanel(
            new GridLayout(2, 2, 10, 10)
        );

        panel.add(new JLabel("New Date (YYYY-MM-DD):"));
        panel.add(dateField);

        panel.add(new JLabel("New Time (HH:MM):"));
        panel.add(timeField);

        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Reschedule Appointment",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            LocalDate newDate = LocalDate.parse(
                dateField.getText().trim()
            );

            LocalTime newTime = LocalTime.parse(
                timeField.getText().trim()
            );

            if (newDate.atTime(newTime).isBefore(
                java.time.LocalDateTime.now()
            )) {
                showError(
                    "Appointment must be in the future."
                );
                return;
            }

            controller.reschedule(
                index,
                newDate,
                newTime
            );

        }
        catch (DateTimeParseException ex) {
            showError(
                "Invalid date or time format. "
                    + "Use YYYY-MM-DD and HH:MM."
            );
        }
        catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * Cancels the selected appointment.
     */
    private void cancelAppointment() {

        int index = appointmentList.getSelectedIndex();

        if (index == -1) {
            showError(
                "Please select an appointment to cancel."
            );
            return;
        }

        List<Appointment> appointments =
            appointmentManager.getAppointments();

        if (index >= appointments.size()) {
            showError("Invalid appointment selection.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel this appointment?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            controller.cancel(index);
        }
    }

    /**
     * Returns to the dashboard.
     */
    private void goBack() {
        setVisible(false);
        dashboard.setVisible(true);
    }

    /**
     * Updates the displayed appointments when
     * the model changes.
     */
    @Override
    public void update(List<String> appointments) {

        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(
                () -> update(appointments)
            );
            return;
        }

        listModel.clear();

        for (String appointment : appointments) {
            listModel.addElement(appointment);
        }
    }

    /**
     * Displays an error message.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Appointment Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

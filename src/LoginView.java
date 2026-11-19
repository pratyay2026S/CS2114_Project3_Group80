
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private PatientController controller;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginView() {
        setTitle("CareSync - Patient Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        controller = new PatientController(this);

        showLogin();
    }

    /**
     * Displays the login screen.
     */
    public void showLogin() {
        getContentPane().removeAll();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("CareSync Login");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password =
                new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                showError();
                return;
            }

            controller.login(username, password);
        });

        registerButton.addActionListener(e -> {
            showCreateAccount();
        });

        add(panel);

        revalidate();
        repaint();
    }

    /**
     * Displays the account registration screen.
     */
    public void showCreateAccount() {
        getContentPane().removeAll();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Patient ID:"), gbc);

        JTextField idField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Username:"), gbc);

        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);

        JPasswordField newPasswordField =
            new JPasswordField(15);

        gbc.gridx = 1;
        panel.add(newPasswordField, gbc);

        JButton createButton = new JButton("Create Account");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(createButton, gbc);

        JButton backButton = new JButton("Back");
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        createButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(
                    idField.getText().trim()
                );

                String name = nameField.getText().trim();
                String password =
                    new String(newPasswordField.getPassword());

                if (id <= 0 || name.isEmpty()
                    || password.isEmpty()) {
                    showError();
                    return;
                }

                controller.register(id, name, password);

                if (controller.getPatient() != null) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Account created successfully!"
                    );

                    showLogin();
                }
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Patient ID must be a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        backButton.addActionListener(e -> showLogin());

        add(panel);

        revalidate();
        repaint();
    }

    /**
     * Displays an error dialog.
     */
    public void showError() {
        JOptionPane.showMessageDialog(
            this,
            "Invalid information. Please try again.",
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Returns the patient controller.
     */
    public PatientController getController() {
        return controller;
    }
}

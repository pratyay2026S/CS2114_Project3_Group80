
public class LoginView {

    /**
     * Displays the login screen.
     */
    public void showLogin() {
        System.out.println("=== Patient Login ===");
        System.out.println("Please enter your username and password.");
    }

    /**
     * Displays the account creation screen.
     */
    public void showCreateAccount() {
        System.out.println("=== Create Account ===");
        System.out.println("Enter your patient information.");
    }

    /**
     * Displays a login or registration error.
     */
    public void showError() {
        System.out.println("Error: Invalid login information or account details.");
    }
}

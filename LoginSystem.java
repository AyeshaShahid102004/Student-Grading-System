import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginSystem {

    // Shared credentials (to simulate simple authentication)
    public static String registeredUsername = null;
    public static String registeredPassword = null;
    public static String FirstName;

    public static void main(String[] args) {
        GradeDataStore.loadFromFile();  // ⬅️ Add this line first
        SwingUtilities.invokeLater(() -> LoginSystem.launchStudentLogin());
    }


    // 1锔忊儯 Student Login Frame
    public static void launchStudentLogin() {
        JFrame studentLoginFrame = new JFrame("Student Login");
        studentLoginFrame.setSize(420, 400);
        studentLoginFrame.setLayout(null);
        studentLoginFrame.setLocationRelativeTo(null);
        studentLoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel loginLabel = new JLabel(" Student Login");
        loginLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        loginLabel.setBounds(150,0, 200, 150);
        studentLoginFrame.add(loginLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 120, 100, 25);
        studentLoginFrame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 120, 200, 25);
        studentLoginFrame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 160, 100, 25);
        studentLoginFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 160, 200, 25);
        studentLoginFrame.add(passwordField);

        // 馃敼 Login button styled like Admin Login
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
        loginButton.setBounds(150, 200, 100, 30);
        studentLoginFrame.add(loginButton);

        // 馃敼 Signup prompt text
        JLabel signupText = new JLabel("Don't have an account?");
        signupText.setFont(new Font("SansSerif", Font.PLAIN, 14));
        signupText.setForeground(Color.GRAY);
        signupText.setBounds(130, 250, 200, 20);
        studentLoginFrame.add(signupText);
        // 馃敼 Signup button styled like Admin Login
        JButton signupButton = new JButton("Sign Up");

        signupButton.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
        signupButton.setBounds(150, 280, 100, 30);
        studentLoginFrame.add(signupButton);

        // Login action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals(LoginSystem.registeredUsername) && password.equals(LoginSystem.registeredPassword)) {
                JOptionPane.showMessageDialog(studentLoginFrame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                studentLoginFrame.dispose();
                FirstName = registeredUsername;
                showStudentPanel();
            } else {
                JOptionPane.showMessageDialog(studentLoginFrame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // SignUp button opens signup frame
        signupButton.addActionListener(e -> {
            studentLoginFrame.dispose();
            launchStudentSignup();
        });

        studentLoginFrame.setVisible(true);
    }


    // 2锔忊儯 Signup Frame
    public static void launchStudentSignup() {
        JFrame signupFrame = new JFrame("Student Sign Up");
        signupFrame.setSize(420, 400);
        signupFrame.setLayout(null);
        signupFrame.setLocationRelativeTo(null);
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("Student Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBounds(100, 20, 200, 30);
        signupFrame.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 80, 100, 25);
        signupFrame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 80, 200, 25);
        signupFrame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 130, 100, 25);
        signupFrame.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 200, 25);
        signupFrame.add(passwordField);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(10, 180, 130, 25);
        signupFrame.add(confirmLabel);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setBounds(150, 180, 200, 25);
        signupFrame.add(confirmField);

        JButton registerBtn = new JButton("Sign Up");
        registerBtn.setBounds(90, 240, 100, 30);
        registerBtn.setBackground(Color.BLUE);
        registerBtn.setForeground(Color.WHITE);
        signupFrame.add(registerBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(220, 240, 100, 30);
        signupFrame.add(backBtn);
        // Register action
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(signupFrame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(signupFrame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                LoginSystem.registeredUsername = username;
                LoginSystem.registeredPassword = password;
                JOptionPane.showMessageDialog(signupFrame, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                signupFrame.dispose();
                launchStudentLogin(); // Return to login
            }
        });

        // Back to login
        backBtn.addActionListener(e -> {
            signupFrame.dispose();
            launchStudentLogin();
        });

        signupFrame.setVisible(true);
    }

    // 3锔忊儯 Student Panel
    public static void showStudentPanel() {
        StudentPanel.launch(FirstName);
    }
}
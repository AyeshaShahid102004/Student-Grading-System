import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Main {
    // Store registered student credentials (in-memory)
    static String registeredUsername = null;
    static String registeredPassword = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Grading System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);

        // Main layout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        frame.setContentPane(contentPanel);

        // Theme blue
        Color themeBlue = new Color(0, 102, 204);

        // Top menu bar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        topPanel.setBackground(themeBlue);

        JButton homeBtn = createMenuButton("Home");
        JButton aboutBtn = createMenuButton("About");
        JButton contactBtn = createMenuButton("Contact");

        JButton loginBtn = createMenuButton("Login");
        JPopupMenu loginMenu = new JPopupMenu();
        JMenuItem studentLogin = new JMenuItem("Student");
        JMenuItem adminLogin = new JMenuItem("Admin");
        loginMenu.add(studentLogin);
        loginMenu.add(adminLogin);
        loginBtn.addActionListener(e -> loginMenu.show(loginBtn, 0, loginBtn.getHeight()));

        topPanel.add(homeBtn);
        topPanel.add(aboutBtn);
        topPanel.add(contactBtn);
        topPanel.add(loginBtn);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Center title and image
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        File file = new File("C:/Users/fatim/Downloads/sgs.png");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Image not found: " + file.getAbsolutePath());
        }
        ImageIcon bgIcon = new ImageIcon(file.getAbsolutePath());
        Image scaledImg = bgIcon.getImage().getScaledInstance(1227, 709, Image.SCALE_SMOOTH);
        bgIcon = new ImageIcon(scaledImg);

// ✅ Set background label
        JLabel backgroundLabel = new JLabel(bgIcon);
        backgroundLabel.setBounds(0, 0, 1227, 709);
        backgroundLabel.setLayout(null); // Allows adding components absolutely

// ✅ Load and place icon on top
        ImageIcon iconImg = new ImageIcon("C:/Users/fatim/Downloads/sgs.png"); // or different image
        Image icon = iconImg.getImage().getScaledInstance(137, 128, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(icon));


// ✅ Add icon to background
        contentPanel.add(backgroundLabel);



        frame.setVisible(true);

        // ðŸ‘¤ Student Login Action
        studentLogin.addActionListener(e -> {
            LoginSystem.launchStudentLogin();        });
        // ðŸ›¡ Admin Login Action
        adminLogin.addActionListener(e -> {
            JFrame adminLoginFrame = new JFrame("Admin Login");
            adminLoginFrame.setSize(400, 300);
            adminLoginFrame.setLayout(null);
            adminLoginFrame.setLocationRelativeTo(null);
            adminLoginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JLabel userLabel = new JLabel("Username:");
            userLabel.setBounds(50, 50, 100, 25);
            adminLoginFrame.add(userLabel);

            JTextField userField = new JTextField();
            userField.setBounds(150, 50, 180, 25);
            adminLoginFrame.add(userField);

            JLabel passLabel = new JLabel("Password:");
            passLabel.setBounds(50, 100, 100, 25);
            adminLoginFrame.add(passLabel);
            JPasswordField passField = new JPasswordField();
            passField.setBounds(150, 100, 180, 25);
            adminLoginFrame.add(passField);

            JButton login = new JButton("Login");
            login.setBounds(150, 150, 100, 30);
            adminLoginFrame.add(login);
            login.addActionListener(ae -> {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                if (username.equals("Laiba123") && password.equals("12345")) {
                    JOptionPane.showMessageDialog(adminLoginFrame, "Admin login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    adminLoginFrame.dispose();
                    AdminPanel.launch();// Admin dashboard
                } else {
                    JOptionPane.showMessageDialog(adminLoginFrame, "Invalid admin credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            adminLoginFrame.setVisible(true);
        });
    }

    private static JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0, 102, 204));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        return btn;
    }
    // 3锔忊儯 Admin Panel
    public static void showStudentPanel() {
        AdminPanel.launch();
    }
}
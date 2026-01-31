import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Main {
    static String registeredUsername = null;
    static String registeredPassword = null;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Form");
        frame.setSize(1104, 663);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0, 0, 139));

        ImageIcon iconImg = new ImageIcon("C:/Users/fatim/Downloads/user12.png");
        Image img = iconImg.getImage().getScaledInstance(300, 270, Image.SCALE_SMOOTH);
        JLabel icon = new JLabel(new ImageIcon(img));
        icon.setBounds(469, 49, 137, 128);
        frame.add(icon);

        JLabel usernameLabel = new JLabel("Enter Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 24));
        usernameLabel.setBounds(403, 188, 265, 39);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setForeground(Color.BLACK);
        usernameField.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        usernameField.setBounds(403, 230, 265, 26);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Enter Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 24));
        passwordLabel.setBounds(403, 271, 259, 39);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setForeground(Color.BLACK);
        passwordField.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 24));
        passwordField.setBounds(403, 313, 265, 26);
        frame.add(passwordField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBackground(Color.RED);
        cancelButton.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        cancelButton.setBounds(403, 362, 102, 34);
        frame.add(cancelButton);

        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.BLACK);
        loginButton.setBackground(Color.GREEN);
        loginButton.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        loginButton.setBounds(542, 362, 102, 34);
        frame.add(loginButton);

        UIManager.put("ProgressBar.selectionBackground", Color.BLACK); // Text outside the bar (if unfilled)
        UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(328, 431, 471, 25);
        progressBar.setFont(new Font("SansSerif", Font.BOLD, 20));
        progressBar.setForeground(Color.GREEN);
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setStringPainted(true);
        frame.add(progressBar);

        JLabel signUpText = new JLabel("Don't you have an account?");
        signUpText.setForeground(Color.WHITE);
        signUpText.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
        signUpText.setBounds(384, 509, 313, 26);
        frame.add(signUpText);

        JButton signupButton = new JButton("SignUp");
        signupButton.setForeground(new Color(0, 0, 139));
        signupButton.setBackground(Color.WHITE);
        signupButton.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
        signupButton.setBounds(650, 501, 90, 32);
        frame.add(signupButton);

        loginButton.addActionListener(new ActionListener() {
            Timer timer;
            int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both username and password.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if ((username.equals(registeredUsername) && password.equals(registeredPassword))){
                    progress = 0;
                    progressBar.setValue(0);
                    progressBar.setString("0%");
                    timer = new Timer(50, new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            progress += 4;
                            progressBar.setValue(progress);
                            progressBar.setString(progress + "%");
                            if (progress >= 100) {
                                timer.stop();
                                JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                frame.dispose();
                                JFrame menuFrame = new JFrame();
                                menuFrame.setSize(1227, 709);
                                menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                menuFrame.setLayout(null);

// ✅ Load and scale background image
                                ImageIcon bgIcon = new ImageIcon("C:/Users/fatim/Downloads/admin.png");
                                Image scaledImg = bgIcon.getImage().getScaledInstance(1227, 709, Image.SCALE_SMOOTH);
                                bgIcon = new ImageIcon(scaledImg);

// ✅ Set background label
                                JLabel backgroundLabel = new JLabel(bgIcon);
                                backgroundLabel.setBounds(0, 0, 1227, 709);
                                backgroundLabel.setLayout(null); // Allows adding components absolutely

// ✅ Load and place icon on top
                                ImageIcon iconImg = new ImageIcon("C:/Users/fatim/Downloads/admin.png"); // or different image
                                Image icon = iconImg.getImage().getScaledInstance(137, 128, Image.SCALE_SMOOTH);
                                JLabel iconLabel = new JLabel(new ImageIcon(icon));


// ✅ Add icon to background
                                backgroundLabel.add(iconLabel);

// ✅ Add menu bar
                                JMenuBar menuBar = new JMenuBar();
                                menuBar.setBackground(new Color(176, 196, 222));
                                menuBar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                                Font menuFont = new Font("SansSerif", Font.BOLD, 14);

// Home Menu
                                JMenu homeMenu = new JMenu("Home");
                                homeMenu.setFont(menuFont);
                                JMenuItem goHome = new JMenuItem("Go To Home Page");
                                JMenuItem refresh = new JMenuItem("Refresh View");
                                JMenuItem exitHome = new JMenuItem("Exit");
                                homeMenu.add(goHome);
                                homeMenu.add(refresh);
                                homeMenu.addSeparator();
                                homeMenu.add(exitHome);
                                menuBar.add(homeMenu);

// Student Menu
                                JMenu studentMenu = new JMenu("Student");
                                studentMenu.setFont(menuFont);
                                JMenuItem studentDetails = new JMenuItem("Student Details");
                                JMenuItem parentsDetails = new JMenuItem("Parents/Guardian Details");
                                studentMenu.add(studentDetails);
                                studentDetails.addActionListener(f -> {


                                });
                                studentMenu.add(new JMenuItem("Academic Records"));
                                studentMenu.add(new JMenuItem("Calculate Grade"));
                                studentMenu.add(new JMenuItem("Generate Report Card"));
                                studentMenu.add(new JMenuItem("Export Student Profile"));
                                studentMenu.addSeparator();
                                studentMenu.add(new JMenuItem("Exit"));
                                menuBar.add(studentMenu);

// Subjects Menu
                                JMenu subjectMenu = new JMenu("Subjects");
                                subjectMenu.setFont(menuFont);
                                subjectMenu.add(new JMenuItem("View Subjects"));
                                subjectMenu.add(new JMenuItem("Assign Subjects to Student"));
                                subjectMenu.add(new JMenuItem("Edit Subject Details"));
                                menuBar.add(subjectMenu);

// Grades Menu
                                JMenu gradesMenu = new JMenu("Grades");
                                gradesMenu.setFont(menuFont);
                                gradesMenu.add(new JMenuItem("Grade Entry"));
                                gradesMenu.add(new JMenuItem("Grade History"));
                                gradesMenu.add(new JMenuItem("Calculate GPA"));
                                gradesMenu.add(new JMenuItem("Grade Conversion Table"));
                                gradesMenu.add(new JMenuItem("Performance Graph"));
                                menuBar.add(gradesMenu);

// Reports Menu
                                JMenu reportsMenu = new JMenu("Reports");
                                reportsMenu.setFont(menuFont);
                                reportsMenu.add(new JMenuItem("Report Card"));
                                reportsMenu.add(new JMenuItem("Progress Report"));
                                reportsMenu.add(new JMenuItem("Print / Export PDF"));
                                menuBar.add(reportsMenu);

// Help Menu
                                JMenu helpMenu = new JMenu("Help");
                                helpMenu.setFont(menuFont);
                                helpMenu.add(new JMenuItem("User Manual"));
                                helpMenu.add(new JMenuItem("FAQs"));
                                helpMenu.add(new JMenuItem("Contact Support"));
                                helpMenu.add(new JMenuItem("About"));
                                menuBar.add(helpMenu);

// ✅ Set background as content pane
                                menuFrame.setContentPane(backgroundLabel);
                                menuFrame.setJMenuBar(menuBar);
                                menuFrame.setLocationRelativeTo(null);
                                menuFrame.setVisible(true);

// Exit Action
                                exitHome.addActionListener(f -> System.exit(0));

                            }
                        }
                    });
                    timer.start();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        signupButton.addActionListener(e -> {
            frame.dispose();
            JFrame signupFrame=new JFrame();
            signupFrame.setSize(1227, 709);
            signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            signupFrame.setLayout(null);
            signupFrame.getContentPane().setBackground(new Color(100, 149, 237));
            JLabel signUpLabel = new JLabel("SignUp", SwingConstants.CENTER);
            signUpLabel.setForeground(new Color(0, 0, 139));
            signUpLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 28));
            signUpLabel.setBounds(499, 53, 171, 48);
            signupFrame.add(signUpLabel);

            JLabel userLabel = new JLabel("Enter Username");
            userLabel.setForeground(Color.WHITE);
            userLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 24));
            userLabel.setBackground(new Color(0, 0, 139));
            userLabel.setOpaque(true); // 🔹 Important to show background
            userLabel.setBounds(377, 151, 263, 37);
            Dimension textSize = userLabel.getPreferredSize();
            userLabel.setBounds(377, 151, textSize.width + 10, textSize.height);
            signupFrame.add(userLabel);

            JTextField userNameField = new JTextField();
            userNameField.setForeground(Color.BLACK);
            userNameField.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
            userNameField.setBounds(381, 200, 273, 30);
            signupFrame.add(userNameField);

            JLabel passLabel = new JLabel("Enter Password");
            passLabel.setForeground(Color.WHITE);
            passLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 24));
            passLabel.setBackground(new Color(0, 0, 139));
            passLabel.setOpaque(true); // 🔹 Important to show background
            Dimension textSize1 = userLabel.getPreferredSize();
            passLabel.setBounds(377, 253, textSize1.width + 10, textSize1.height);
            signupFrame.add(passLabel);

            JPasswordField passField = new JPasswordField();
            passField.setForeground(Color.BLACK);
            passField.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
            passField.setBounds(381, 303, 273, 30);
            signupFrame.add(passField);

            JLabel confirmLabel = new JLabel("Confirm Password");
            confirmLabel.setForeground(Color.WHITE);
            confirmLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 22));
            confirmLabel.setBackground(new Color(0, 0, 139));
            confirmLabel.setOpaque(true); // 🔹 Important to show background
            Dimension textSize2 = userLabel.getPreferredSize();
            confirmLabel.setBounds(377, 357, textSize2.width + 10, textSize2.height);
            signupFrame.add(confirmLabel);

            JPasswordField confirmPasswordField = new JPasswordField();
            confirmPasswordField.setForeground(Color.BLACK);
            confirmPasswordField.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
            confirmPasswordField.setBounds(381, 408, 273, 30);
            signupFrame.add(confirmPasswordField);

            JButton signUpBtn = new JButton("SignUp");
            signUpBtn.setForeground(new Color(0, 0, 139));
            signUpBtn.setBackground(Color.WHITE);
            signUpBtn.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
            signUpBtn.setBounds(450, 465, 118, 44);
            signupFrame.add(signUpBtn);

            JLabel loginText = new JLabel("Already have an account?");
            loginText.setForeground(new Color(0, 0, 139));
            loginText.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 20));
            loginText.setBounds(346, 551, 294, 26);
            signupFrame.add(loginText);

            JButton loginBtn = new JButton("Login");
            loginBtn.setForeground(Color.WHITE);
            loginBtn.setBackground(new Color(0, 0, 139));
            loginBtn.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
            loginBtn.setBounds(602, 547, 90, 32);
            signupFrame.add(loginBtn);

            // Action for SignUp button
            signUpBtn.addActionListener(f -> {
                String username = userNameField.getText();
                String password = new String(passField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(signupFrame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(signupFrame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    registeredUsername = username;
                    registeredPassword = password;
                    JOptionPane.showMessageDialog(signupFrame, "Sign Up Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    signupFrame.dispose();
                    frame.setVisible(true);
                }
            });

            // Action for Login button
            loginBtn.addActionListener(f -> {
                signupFrame.dispose();
                frame.setVisible(true);
            });

            signupFrame.setLocationRelativeTo(null);
            signupFrame.setVisible(true);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

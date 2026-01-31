import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class AdminPanel {
    private static java.util.List<String[]> studentList = new ArrayList<>();

    public static void launch() {
        JFrame panelFrame = new JFrame("Admin Panel");
        panelFrame.setSize(1200, 700);
        panelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelFrame.setLocationRelativeTo(null);
        panelFrame.setLayout(new BorderLayout());

        // Content Panel (center area)
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        File file = new File("C:/Users/fatim/Downloads/admin.png");
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
        ImageIcon iconImg = new ImageIcon("C:/Users/fatim/Downloads/admin.png"); // or different image
        Image icon = iconImg.getImage().getScaledInstance(137, 128, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(icon));


// ✅ Add icon to background
        contentPanel.add(backgroundLabel);

        // Sidebar Panel (left)
        JPanel sidebar = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(2, 27, 71));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(290, 0));
        sidebar.setOpaque(false);

        // Sidebar Title
        JLabel sidebarTitle = new JLabel("Admin Dashboard");
        sidebarTitle.setForeground(Color.WHITE);
        sidebarTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        sidebarTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        sidebar.add(sidebarTitle);

        // Sidebar Menu Items
        String[] menuItems = {
                "─── 🎓 Students ───",
                "📚 Manage Student Records",
                "🧾 View Student Profile",

                "─── 📝 Grades ───",
                "🧮 Enter Grades",

                "─── 🚪 Logout ───",
                "🚪 Logout"
        };


        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);
            btn.setMaximumSize(new Dimension(300, 20));
            btn.setFocusPainted(false);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
            btn.setBackground(new Color(2, 27, 71));
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(btn);

            // Handle actions
            btn.addActionListener(e -> {
                contentPanel.removeAll();

                switch (item) {
                    case "🧮 Enter Grades":
                        new GradeEntryForm(LoginSystem.FirstName);
                        break;
                    case "📚 Manage Student Records":
                        new StudentRecordForm(studentList);
                        break;
                    case "🧾 View Student Profile":
                        if (studentList.isEmpty()) {
                            JOptionPane.showMessageDialog(panelFrame,
                                    "No student records found.\nPlease add students first.",
                                    "No Data",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            new StudentProfileForm(studentList);
                        }
                        break;
                    case "🚪 Logout":
                        panelFrame.dispose(); // Close dashboard
                        LoginSystem.launchStudentLogin(); // Return to login
                        return;
                }
                contentPanel.revalidate();
                contentPanel.repaint();
            });
        }
        panelFrame.add(sidebar, BorderLayout.WEST);
        panelFrame.add(contentPanel, BorderLayout.CENTER);
        panelFrame.setVisible(true);
    }

    // 📊 View Grades Panel
    private static JPanel getGradesPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Your Grades"));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel("Math:"));
        panel.add(new JLabel("95"));
        panel.add(new JLabel("English:"));
        panel.add(new JLabel("88"));
        panel.add(new JLabel("Science:"));
        panel.add(new JLabel("92"));
        panel.add(new JLabel("Computer:"));
        panel.add(new JLabel("100"));
        panel.add(new JLabel("History:"));
        panel.add(new JLabel("89"));
        return panel;
    }

    // 📄 Download Report Panel (mocked functionality)
    private static JPanel getReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Download Report Feature Coming Soon!", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.setBackground(Color.WHITE);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // 📨 Messages Panel
    private static JPanel getMessagesPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Recent Announcements"));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("📌 Exam starts from 25th June"));
        panel.add(new JLabel("📌 Submit assignments by 20th June"));
        panel.add(new JLabel("📌 Fee deadline extended to 30th June"));

        return panel;
    }
}
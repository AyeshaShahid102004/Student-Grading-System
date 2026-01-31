import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class DownloadReport {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private String studentID;

    public DownloadReport(String studentID) {
        this.studentID = studentID;

        frame = new JFrame("📄 Download Report Card");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Report Card for Student ID: " + studentID, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(title, BorderLayout.NORTH);

        String[] columns = {"StudentID", "FirstName", "LastName", "Subject", "Grade"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);

        boolean hasGrades = false;

        for (String[] row : GradeDataStore.gradeRecords) {
            if (row[0].equals(studentID)) {
                tableModel.addRow(new String[]{row[0], row[1], row[2], row[3], row[4]});
                hasGrades = true;
            }
        }

        if (!hasGrades) {
            JOptionPane.showMessageDialog(frame, "No grades found for this student.", "No Data", JOptionPane.WARNING_MESSAGE);
            frame.dispose();
            return;
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton downloadBtn = new JButton("Download Report");
        downloadBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        downloadBtn.setBackground(new Color(0, 153, 76));
        downloadBtn.setForeground(Color.WHITE);
        downloadBtn.setFocusPainted(false);
        downloadBtn.setPreferredSize(new Dimension(180, 40));

        downloadBtn.addActionListener((ActionEvent e) -> downloadReportToFile(studentID)); // ✅ Correct value

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(downloadBtn);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void downloadReportToFile(String studentID) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("Student_Report_" + studentID + ".txt"));

        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(selectedFile)) {
                writer.write("📄 Report Card for Student ID: " + studentID + "\n\n");
                writer.write(String.format("%-12s %-12s %-12s %-12s %-8s\n", "StudentID", "FirstName", "LastName", "Subject", "Grade"));
                writer.write("--------------------------------------------------------\n");

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String id = tableModel.getValueAt(i, 0).toString();
                    String firstName = tableModel.getValueAt(i, 1).toString();
                    String lastName = tableModel.getValueAt(i, 2).toString();
                    String subject = tableModel.getValueAt(i, 3).toString();
                    String grade = tableModel.getValueAt(i, 4).toString();

                    writer.write(String.format("%-12s %-12s %-12s %-12s %-8s\n", id, firstName, lastName, subject, grade));
                }

                JOptionPane.showMessageDialog(frame, "Report downloaded successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Failed to save report.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GradesForm {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public GradesForm(String firstName) {
        frame = new JFrame("📊 Your Grades");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Grades for Student name: " + firstName, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(title, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"StudentID","FirstName","LastName","Subject", "Grade"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Sanserif", Font.PLAIN, 16));
        table.setRowHeight(25);

        // Load student grades from shared GradeDataStore
        for (String[] row : GradeDataStore.gradeRecords) {
            if (row[1].equals(LoginSystem.FirstName)) { // Match student ID
                tableModel.addRow(new String[]{row[0],row[1],row[2],row[3], row[4]}); // Subject & Grade
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        System.out.println("Loaded records:");
        for (String[] record : GradeDataStore.gradeRecords) {
            System.out.println(java.util.Arrays.toString(record));
        }

        frame.setVisible(true);
    }
}

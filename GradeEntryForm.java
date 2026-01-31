import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.util.ArrayList;
public class GradeEntryForm {
    private static final int MaxAssignmentMarks = 10;
    private static final int MaxQuizMarks = 10;
    private static final int MaxProjectMarks = 10;
    private static final int MaxAttendanceMarks = 10;
    private static final int MaxMidMarks = 20;
    private static final int MaxFinalMarks = 40;
    private String studentID;

    private JFrame frame;
    private JTextField txtID, txtFirstName, txtLastName, txtAssignment, txtQuiz, txtProject, txtAttendance, txtMid, txtFinal, txtTotal, txtGrade, txtSearchID;
    private JComboBox<String> subjectBox;
    private JTable table;
    private DefaultTableModel tableModel;

    private java.util.List<String[]> data = GradeDataStore.gradeRecords;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeEntryForm("test123"));
    }
    public GradeEntryForm(String studentID) {
        this.studentID = studentID;
        frame = new JFrame("Grade Calculation Form");
        frame.setSize(1000, 700);
        frame.getContentPane().setBackground(new Color(176, 196, 222));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Input Fields
        JLabel lblID = new JLabel("Student ID:");
        lblID.setForeground(Color.WHITE);
        lblID.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
        lblID.setBounds(20, 20, 100, 25);
        lblID.setOpaque(true);
        lblID.setBackground(new Color(0, 0, 139));
        frame.add(lblID);
        txtID = new JTextField();
        txtID.setBounds(130, 20, 150, 25);
        frame.add(txtID);

        JLabel lblFN = new JLabel("First Name:");
        lblFN.setBounds(20, 60, 100, 25);
        lblFN.setForeground(Color.WHITE);
        lblFN.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
        lblFN.setOpaque(true);
        lblFN.setBackground(new Color(0, 0, 139));
        frame.add(lblFN);
        txtFirstName = new JTextField();
        txtFirstName.setBounds(130, 60, 150, 25);
        frame.add(txtFirstName);

        JLabel lblLN = new JLabel("Last Name:");
        lblLN.setBounds(20, 100, 100, 25);
        lblLN.setForeground(Color.WHITE);
        lblLN.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
        lblLN.setOpaque(true);
        lblLN.setBackground(new Color(0, 0, 139));
        frame.add(lblLN);
        txtLastName = new JTextField();
        txtLastName.setBounds(130, 100, 150, 25);
        frame.add(txtLastName);

        JLabel lblSubject = new JLabel("Subject:");
        lblSubject.setBounds(20, 140, 100, 25);
        lblSubject.setForeground(Color.WHITE);
        lblSubject.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
        lblSubject.setOpaque(true);
        lblSubject.setBackground(new Color(0, 0, 139));
        frame.add(lblSubject);
        subjectBox = new JComboBox<>(new String[]{"Statistics", "Maths", "English", "Urdu", "Iqbaliyat"});
        subjectBox.setBounds(130, 140, 150, 25);
        frame.add(subjectBox);

        String[] labels = {"Assignment", "Quiz", "Project", "Attendance", "Mid", "Final", "Total", "Grade"};
        JTextField[] textFields = new JTextField[labels.length];
        int y = 180;
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i] + ":");
            label.setBounds(20, y, 100, 25);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
            label.setOpaque(true);
            label.setBackground(new Color(0, 0, 139));
            frame.add(label);

            textFields[i] = new JTextField();
            textFields[i].setBounds(130, y, 150, 25);
            textFields[i].setEditable(!labels[i].equals("Total") && !labels[i].equals("Grade"));
            frame.add(textFields[i]);
            y += 40;
        }

        txtAssignment = textFields[0];
        txtQuiz = textFields[1];
        txtProject = textFields[2];
        txtAttendance = textFields[3];
        txtMid = textFields[4];
        txtFinal = textFields[5];
        txtTotal = textFields[6];
        txtGrade = textFields[7];

        // Buttons
        txtSearchID = new JTextField();
        txtSearchID.setBounds(300, 60, 150, 30);
        txtSearchID.setToolTipText("Enter ID to delete/search");
        frame.add(txtSearchID);

        JButton btnSearch = new JButton("Search by ID");
        btnSearch.setBounds(300, 100, 150, 30);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBackground(new Color(75, 0, 130));
        btnSearch.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
        btnSearch.addActionListener(e -> searchByID(txtSearchID.getText()));
        frame.add(btnSearch);

        JButton btnDelete = new JButton("Delete by ID");
        btnDelete.setBounds(300, 140, 150, 30);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setBackground(Color.RED);
        btnDelete.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
        btnDelete.addActionListener(e -> deleteByID());
        frame.add(btnDelete);

        JButton btnCalc = new JButton("Calculate Grade");
        btnCalc.setBounds(300, 180, 150, 30);
        btnCalc.setForeground(Color.WHITE);
        btnCalc.setBackground(Color.BLUE);
        btnCalc.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
        btnCalc.addActionListener(this::calculateGrade);
        frame.add(btnCalc);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(300, 220, 150, 25);
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(Color.darkGray);
        btnSave.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
        btnSave.addActionListener(this::saveRecord);
        frame.add(btnSave);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(300, 260, 150, 30);
        btnClear.setForeground(Color.WHITE);
        btnClear.setBackground(Color.GRAY);
        btnClear.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 14));
        btnClear.addActionListener(e -> clearForm());
        frame.add(btnClear);

        // Table
        // Create the table model
        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "Subject", "Grade"}, 0);

// ✅ Initialize the table first
        table = new JTable(tableModel);

// Set row height
        table.setRowHeight(30);

// Set font for table content
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

// Set grid color
        table.setGridColor(Color.LIGHT_GRAY);

// Set header font and background
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(102, 0, 153));  // Dark purple
        header.setForeground(Color.WHITE);

// Alternate row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(153, 102, 204)); // Selected row color
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 255)); // Stripe effect
                    c.setForeground(Color.BLACK);
                }
                setHorizontalAlignment(CENTER); // Keep center alignment
                return c;
            }
        });

// ✅ Now add it to the scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(500, 20, 460, 600);
        frame.add(scrollPane);


        frame.setVisible(true);
    }

    private void calculateGrade(ActionEvent e) {
        try {
            double assignment = Double.parseDouble(txtAssignment.getText());
            double quiz = Double.parseDouble(txtQuiz.getText());
            double project = Double.parseDouble(txtProject.getText());
            double attendance = Double.parseDouble(txtAttendance.getText());
            double mid = Double.parseDouble(txtMid.getText());
            double finalExam = Double.parseDouble(txtFinal.getText());

            if (assignment > 10 || quiz > 10 || project > 10 || attendance > 10 || mid > 20 || finalExam > 40) {
                JOptionPane.showMessageDialog(frame, "Entered marks exceed maximum allowed values.");
                return;
            }

            double total = assignment + quiz + project + attendance + mid + finalExam;
            txtTotal.setText(String.valueOf(total));
            txtGrade.setText(determineGrade(total));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numeric marks.");
        }
    }

    private void saveRecord(ActionEvent e) {
        if (txtID.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtGrade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields and calculate grade before saving.");
            return;
        }
        String[] row = {
                txtID.getText(),
                txtFirstName.getText(),
                txtLastName.getText(),
                (String) subjectBox.getSelectedItem(),
                txtGrade.getText()
        };
        tableModel.addRow(row);
        data.add(row);

// ✅ ADD THIS:
        System.out.println("Saved records:");
        for (String[] record : GradeDataStore.gradeRecords) {
            System.out.println(java.util.Arrays.toString(record));
        }
        GradeDataStore.saveToFile();  // Save to file when data added

        clearForm();
    }

    private void deleteByID() {
        String idToDelete = txtSearchID.getText().trim();
        if (idToDelete.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a student ID to delete.");
            return;
        }
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(idToDelete)) {
                tableModel.removeRow(i);
                JOptionPane.showMessageDialog(frame, "Record deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "ID not found.");
    }
    private void searchByID(String txtSearchID) {
        if (txtSearchID.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a Student ID to search.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int searchID = Integer.parseInt(txtSearchID);
            boolean found = false;

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                int currentID = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
                if (currentID == searchID) {
                    String studentInfo = "ID: " + tableModel.getValueAt(i, 0)
                            + "\nFirst Name: " + tableModel.getValueAt(i, 1)
                            + "\nLast Name: " + tableModel.getValueAt(i, 2)
                            + "\nSubject: " + tableModel.getValueAt(i, 3)
                            + "\nGrade: " + tableModel.getValueAt(i, 4);

                    JOptionPane.showMessageDialog(frame, studentInfo, "Student Found", JOptionPane.INFORMATION_MESSAGE);
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(frame, "Student ID not found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid numeric Student ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtID.setText("");        // ✅ pre-fill student ID
        txtFirstName.setText(LoginSystem.FirstName);
        txtFirstName.setEditable(false);
        txtLastName.setText("");
        txtAssignment.setText("");
        txtQuiz.setText("");
        txtProject.setText("");
        txtAttendance.setText("");
        txtMid.setText("");
        txtFinal.setText("");
        txtTotal.setText("");
        txtGrade.setText("");
        txtSearchID.setText("");
        subjectBox.setSelectedIndex(0);
    }

    private String determineGrade(double marks) {
        if (marks >= 90) return "A";
        if (marks >= 80) return "B";
        if (marks >= 70) return "C";
        if (marks >= 60) return "D";
        return "F";
    }
}

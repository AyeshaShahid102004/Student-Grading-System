import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentProfileForm {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea bioArea;

    public StudentProfileForm(List<String[]> studentList) {
        frame = new JFrame("📝 Student Profiles");
        frame.setSize(1200, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        String[] columns = {"First Name", "Last Name", "DOB", "Gender", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

// Set header style
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(102, 0, 153));
        header.setForeground(Color.WHITE);

// Set preferred column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(120); // First Name
        columnModel.getColumn(1).setPreferredWidth(120); // Last Name
        columnModel.getColumn(2).setPreferredWidth(120); // DOB
        columnModel.getColumn(3).setPreferredWidth(100);  // Gender
        columnModel.getColumn(4).setPreferredWidth(180); // Email
        columnModel.getColumn(5).setPreferredWidth(120); // Phone

// ScrollPane settings
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 760, 500);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane);
        // Populate table with data
        for (String[] student : studentList) {
            tableModel.addRow(student);
        }

        JLabel lblDetails = new JLabel("Student Bio Data:");
        lblDetails.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDetails.setBounds(840, 50, 200, 25);
        frame.add(lblDetails);

        bioArea = new JTextArea();
        bioArea.setEditable(false);
        bioArea.setFont(new Font("SansSerif", Font.BOLD, 16));
        JScrollPane bioScroll = new JScrollPane(bioArea);
        bioScroll.setBounds(840, 50, 280, 370);
        frame.add(bioScroll);

        table.getSelectionModel().addListSelectionListener(e -> showSelectedStudent());

        frame.setVisible(true);
    }

    private void showSelectedStudent() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("First Name: ").append(table.getValueAt(row, 0)).append("\n");
            sb.append("Last Name: ").append(table.getValueAt(row, 1)).append("\n");
            sb.append("Date of Birth: ").append(table.getValueAt(row, 2)).append("\n");
            sb.append("Gender: ").append(table.getValueAt(row, 3)).append("\n");
            sb.append("Email: ").append(table.getValueAt(row, 4)).append("\n");
            sb.append("Phone: ").append(table.getValueAt(row, 5)).append("\n");

            bioArea.setText(sb.toString());
        }
    }
}

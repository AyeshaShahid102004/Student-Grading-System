import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.text.ParseException;
import java.util.Date;


public class StudentRecordForm {
    private JFrame frame;
    private JTextField txtFirstName, txtLastName, txtEmail, txtPhone;
    private JComboBox<String> cmbGender;
    private JFormattedTextField txtDOB;
    private JTable table;
    private DefaultTableModel tableModel;

    private java.util.List<String[]> studentList = new ArrayList<>();
    private int selectedRowIndex = -1;


    public StudentRecordForm(List<String[]> studentList) {
        this.studentList = studentList;
        frame = new JFrame("Student Records");
        frame.setSize(1200, 600);
        frame.getContentPane().setBackground(new Color(176, 196, 222)); // Light Steel Blue
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelBg = new Color(0, 0, 139);
        Color labelFg = Color.WHITE;

        int left = 20, top = 20, width = 150, height = 25;

        JLabel lblFN = createLabel("First Name:", left, top, width, height, labelFont, labelBg, labelFg);
        frame.add(lblFN);
        txtFirstName = createTextField(180, top);
        frame.add(txtFirstName);

        JLabel lblLN = createLabel("Last Name:", left, top += 40, width, height, labelFont, labelBg, labelFg);
        frame.add(lblLN);
        txtLastName = createTextField(180, top);
        frame.add(txtLastName);

        JLabel lblDOB = createLabel("Date of Birth:", left, top += 40, width, height, labelFont, labelBg, labelFg);
        frame.add(lblDOB);
        txtDOB = new JFormattedTextField();
        txtDOB.setBounds(180, top, 200, 25);
        txtDOB.setForeground(Color.GRAY);
        txtDOB.setText("MM/DD/YYYY");

        txtDOB.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtDOB.getText().equals("MM/DD/YYYY")) {
                    txtDOB.setText("");
                    txtDOB.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtDOB.getText().trim().isEmpty()) {
                    txtDOB.setForeground(Color.GRAY);
                    txtDOB.setText("MM/DD/YYYY");
                }
            }
        });
        frame.add(txtDOB);


        JLabel lblGender = createLabel("Gender:", left, top += 40, width, height, labelFont, labelBg, labelFg);
        frame.add(lblGender);
        cmbGender = new JComboBox<>(new String[]{"", "Male", "Female", "Other"});
        cmbGender.setBounds(180, top, 200, 25);
        frame.add(cmbGender);

        JLabel lblEmail = createLabel("Email Address:", left, top += 40, width, height, labelFont, labelBg, labelFg);
        frame.add(lblEmail);
        txtEmail = createTextField(180, top);
        frame.add(txtEmail);

        JLabel lblPhone = createLabel("Phone Number:", left, top += 40, width, height, labelFont, labelBg, labelFg);
        frame.add(lblPhone);
        txtPhone = createTextField(180, top);
        frame.add(txtPhone);

        // Buttons
        JButton btnAdd = createButton("Add", 50, top += 50);
        btnAdd.addActionListener(e -> addStudent());
        frame.add(btnAdd);

        JButton btnUpdate = createButton("Update", 150, top);
        btnUpdate.addActionListener(e -> updateStudent());
        frame.add(btnUpdate);

        JButton btnDelete = createButton("Delete", 270, top);
        btnDelete.addActionListener(e -> deleteStudent());
        frame.add(btnDelete);

        JButton btnClear = createButton("Clear", 150, 330);
        btnClear.addActionListener(e -> clearForm());
        frame.add(btnClear);


        String[] columns = {"First Name", "Last Name", "DOB", "Gender", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> populateSelectedRow());

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
        scrollPane.setBounds(400, 20, 760, 500);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.add(scrollPane);
        frame.setVisible(true);

    }

    private JLabel createLabel(String text, int x, int y, int w, int h, Font f, Color bg, Color fg) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, w, h);
        label.setOpaque(true);
        label.setFont(f);
        label.setBackground(bg);
        label.setForeground(fg);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 200, 25);
        return tf;
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 90, 30);
        btn.setBackground(new Color(102, 0, 153));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }

    private void addStudent() {
        if (!validateForm()) return;
        String[] row = getFormData();
        studentList.add(row);
        tableModel.addRow(row);
        clearForm();
        JOptionPane.showMessageDialog(frame, "Student added.");
    }

    private void updateStudent() {
        if (selectedRowIndex == -1) {
            showError("Please select a student from the table to update.");
            return;
        }
        if (!validateForm()) return;
        String[] updatedData = getFormData();
        for (int i = 0; i < updatedData.length; i++) {
            tableModel.setValueAt(updatedData[i], selectedRowIndex, i);
        }
        studentList.set(selectedRowIndex, updatedData);
        clearForm();
        JOptionPane.showMessageDialog(frame, "Student updated.");
        selectedRowIndex = -1;
    }

    private void deleteStudent() {
        if (selectedRowIndex == -1) {
            showError("Please select a student to delete.");
            return;
        }
        tableModel.removeRow(selectedRowIndex);
        studentList.remove(selectedRowIndex);
        clearForm();
        JOptionPane.showMessageDialog(frame, "Student deleted.");
        selectedRowIndex = -1;
    }


    private void populateSelectedRow() {
        selectedRowIndex = table.getSelectedRow();
        if (selectedRowIndex >= 0) {
            txtFirstName.setText((String) tableModel.getValueAt(selectedRowIndex, 0));
            txtLastName.setText((String) tableModel.getValueAt(selectedRowIndex, 1));
            txtDOB.setText((String) tableModel.getValueAt(selectedRowIndex, 2));
            cmbGender.setSelectedItem(tableModel.getValueAt(selectedRowIndex, 3));
            txtEmail.setText((String) tableModel.getValueAt(selectedRowIndex, 4));
            txtPhone.setText((String) tableModel.getValueAt(selectedRowIndex, 5));
        }
    }

    private boolean validateForm() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String dobText = txtDOB.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String gender = (String) cmbGender.getSelectedItem();

        if (firstName.isEmpty() || lastName.isEmpty() || dobText.isEmpty() ||
                email.isEmpty() || phone.isEmpty() || gender == null || gender.trim().isEmpty()) {
            showError("All fields are required.");
            return false;
        }
        // Validate First Name
        if (firstName.isEmpty()) {
            showError("First Name is required.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z ]+$", firstName)) {
            showError("First Name must contain only letters.");
            return false;
        }

        // Validate Last Name
        if (lastName.isEmpty()) {
            showError("Last Name is required.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z ]+$", lastName)) {
            showError("Last Name must contain only letters.");
            return false;
        }

        // Validate DOB
        if (dobText.isEmpty()) {
            showError("Date of Birth is required.");
            return false;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            sdf.setLenient(false);
            Date dob = sdf.parse(dobText);
            if (dob.after(new Date())) {
                showError("Date of Birth cannot be in the future.");
                return false;
            }
        } catch (ParseException e) {
            showError("Invalid Date format. Use MM/DD/YYYY.");
            return false;
        }

        // Validate Gender
        if (gender == null || gender.trim().isEmpty()) {
            showError("Gender is required.");
            return false;
        }

        // Validate Email
        if (email.isEmpty()) {
            showError("Email Address is required.");
            return false;
        }
        if (!Pattern.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", email)) {
            showError("Invalid Email Address.");
            return false;
        }

        // Validate Phone Number
        if (phone.isEmpty()) {
            showError("Phone Number is required.");
            return false;
        }
        if (!Pattern.matches("\\d{10}", phone)) {
            showError("Phone Number must be exactly 10 digits.");
            return false;
        }

        return true;
    }


    private String[] getFormData() {
        return new String[]{
                txtFirstName.getText().trim(),
                txtLastName.getText().trim(),
                txtDOB.getText().trim(),
                (String) cmbGender.getSelectedItem(),
                txtEmail.getText().trim(),
                txtPhone.getText().trim()
        };
    }

    private void clearForm() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtDOB.setText("");
        cmbGender.setSelectedIndex(0);
        txtEmail.setText("");
        txtPhone.setText("");
        table.clearSelection();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

}
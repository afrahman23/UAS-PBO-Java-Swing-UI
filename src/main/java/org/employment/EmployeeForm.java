package org.employment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

public class EmployeeForm extends JFrame {

    private JLabel lblName;
    private JLabel lblNikId;
    private JLabel lblTempatLahir;
    private JLabel lblJenisKelamin;
    private JLabel lblAlamat;
    private JLabel lblStatus;
    private JTextField txtName;
    private JTextField txtNikId;
    private JTextField txtTempatLahir;
    private JComboBox<String> cmbJenisKelamin;
    private JTextField txtAlamat;
    private JComboBox<String> cmbStatus;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnRetrieve;
    private JTextArea displayArea;

    public EmployeeForm() {
        setTitle("Employee Management System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

//        lblName = new JLabel("Name:");
//        txtName = new JTextField(20);
//
//        JLabel lblNikId = new JLabel("NIK ID:");
//        txtNikId = new JTextField(20);
//
//        JLabel lblTempatLahir = new JLabel("Tempat Lahir:");
//        txtTempatLahir = new JTextField(20);
//
//        JLabel lblJenisKelamin = new JLabel("Jenis Kelamin:");
//        cmbJenisKelamin = new JComboBox<>(new String[]{"Laki-Laki", "Perempuan"});
//
//        JLabel lblAlamat = new JLabel("Alamat:");
//        txtAlamat = new JTextField(20);
//
//        JLabel lblStatus = new JLabel("Status:");
//        cmbStatus = new JComboBox<>(new String[]{"Single", "Married"});
//
//        btnSave = new JButton("Save");
//        btnUpdate = new JButton("Update");
//        btnDelete = new JButton("Delete");
//        btnRetrieve = new JButton("Retrieve");
//
//        displayArea = new JTextArea(10, 30);
//        displayArea.setEditable(false);

        add(lblName);
        add(txtName);
        add(lblNikId);
        add(txtNikId);
        add(lblTempatLahir);
        add(txtTempatLahir);
        add(lblJenisKelamin);
        add(cmbJenisKelamin);
        add(lblAlamat);
        add(txtAlamat);
        add(lblStatus);
        add(cmbStatus);
        add(btnSave);
        add(btnUpdate);
        add(btnDelete);
        add(btnRetrieve);
        add(new JScrollPane(displayArea));

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        btnSave.addActionListener(e -> {
            try {
                Employee employee = new Employee(
                        txtName.getText(),
                        txtNikId.getText(),
                        txtTempatLahir.getText(),
                        Objects.requireNonNull(cmbJenisKelamin.getSelectedItem()).toString(),
                        txtAlamat.getText(),
                        Objects.requireNonNull(cmbStatus.getSelectedItem()).toString()
                );

                DBUtil.insertEmployee(employee);
                displayArea.append(employee.getName() + " added successfully!\n");
                clearForm();
            } catch (SQLException ex) {
                displayArea.append("Error: " + ex.getMessage() + "\n");
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                Employee employee = new Employee(
                        txtName.getText(),
                        txtNikId.getText(),
                        txtTempatLahir.getText(),
                        Objects.requireNonNull(cmbJenisKelamin.getSelectedItem()).toString(),
                        txtAlamat.getText(),
                        Objects.requireNonNull(cmbStatus.getSelectedItem()).toString()
                );

                DBUtil.updateEmployee(employee);
                displayArea.append(employee.getName() + " updated successfully!\n");
                clearForm();
            } catch (SQLException ex) {
                displayArea.append("Error: " + ex.getMessage() + "\n");
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                String nikId = txtNikId.getText();
                DBUtil.deleteEmployee(nikId);
                displayArea.append("Employee with NIK ID " + nikId + " deleted successfully!\n");
                clearForm();
            } catch (SQLException ex) {
                displayArea.append("Error: " + ex.getMessage() + "\n");
            }
        });

        btnRetrieve.addActionListener(e -> {
            try {
                String nikId = txtNikId.getText();
                Employee employee = DBUtil.getEmployee(nikId);
                if (employee != null) {
                    txtName.setText(employee.getName());
                    txtTempatLahir.setText(employee.getTempatLahir());
                    cmbJenisKelamin.setSelectedItem(employee.getJenisKelamin());
                    txtAlamat.setText(employee.getAlamat());
                    cmbStatus.setSelectedItem(employee.getStatus());
                } else {
                    displayArea.append("Employee not found!\n");
                }
            } catch (SQLException ex) {
                displayArea.append("Error: " + ex.getMessage() + "\n");
            }
        });
    }

    private void clearForm() {
        txtName.setText("");
        txtNikId.setText("");
        txtTempatLahir.setText("");
        cmbJenisKelamin.setSelectedIndex(0);
        txtAlamat.setText("");
        cmbStatus.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeForm().setVisible(true));
    }
}
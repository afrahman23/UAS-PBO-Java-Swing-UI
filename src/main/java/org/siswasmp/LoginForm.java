package org.siswasmp;

import org.siswasmp.FormSiswa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginForm() {
        setTitle("Login Form Aplikasi SMP Jakenan");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // inisialisasi komponen form login
        JPanel loginPanel = new JPanel();
//        txtUsername = new JTextField(20);
//        txtPassword = new JPasswordField(20);
//        btnLogin = new JButton("Login");

        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        btnLogin = new JButton("Masuk");
        btnLogin.setFont(new Font("Lucida Grande", Font.BOLD, 24));

JLabel lblUsername = new JLabel("Username:");
lblUsername.setFont(new Font("Lucida Grande", Font.BOLD, 16));
loginPanel.add(lblUsername);
loginPanel.add(txtUsername);

JLabel lblPassword = new JLabel("Password:");
lblPassword.setFont(new Font("Lucida Grande", Font.BOLD, 16));
loginPanel.add(lblPassword);
loginPanel.add(txtPassword);
        loginPanel.add(btnLogin);

        add(loginPanel);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                if (validateLogin(username, password)) {
                    openMainApplication();
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "username atau password tidak cocok", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        // validasi login
        return "guru".equals(username) && "sekolah".equals(password);
    }

//    private boolean validateLogin(String username, String password) {
//        Connection con = null;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//        try {
//            // Assuming you have a method to get a connection
//            con = getConnection();
//            pst = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
//            pst.setString(1, username);
//            pst.setString(2, password);
//            rs = pst.executeQuery();
//            return rs.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            // Close resources
//            try {
//                if (rs != null) rs.close();
//                if (pst != null) pst.close();
//                if (con != null) con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    private void openMainApplication() {
        dispose();
        JFrame mainFrame = new JFrame("Main Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);

        FormSiswa formSiswa = new FormSiswa();
        mainFrame.add(formSiswa.getMainPanel());

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}
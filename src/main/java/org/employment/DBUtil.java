package org.employment;

import java.sql.*;

public class DBUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employment";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void insertEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employees (name, nik_id, tempat_lahir, jenis_kelamin, alamat, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getNikId());
            pstmt.setString(3, employee.getTempatLahir());
            pstmt.setString(4, employee.getJenisKelamin());
            pstmt.setString(5, employee.getAlamat());
            pstmt.setString(6, employee.getStatus());
            pstmt.executeUpdate();
        }
    }

    public static void updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employees SET name = ?, tempat_lahir = ?, jenis_kelamin = ?, alamat = ?, status = ? WHERE nik_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getTempatLahir());
            pstmt.setString(3, employee.getJenisKelamin());
            pstmt.setString(4, employee.getAlamat());
            pstmt.setString(5, employee.getStatus());
            pstmt.setString(6, employee.getNikId());
            pstmt.executeUpdate();
        }
    }

    public static void deleteEmployee(String nikId) throws SQLException {
        String sql = "DELETE FROM employees WHERE nik_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nikId);
            pstmt.executeUpdate();
        }
    }

    public static Employee getEmployee(String nikId) throws SQLException {
        String sql = "SELECT * FROM employees WHERE nik_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nikId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getString("name"),
                            rs.getString("nik_id"),
                            rs.getString("tempat_lahir"),
                            rs.getString("jenis_kelamin"),
                            rs.getString("alamat"),
                            rs.getString("status")
                    );
                } else {
                    return null;
                }
            }
        }
    }
}
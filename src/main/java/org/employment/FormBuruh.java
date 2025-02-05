package org.employment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class FormBuruh {
    private JTextField txtName;
    private JTextField txtNikId;
    private JComboBox cmbJabatan;
    private JComboBox cmbJenisKelamin;
    private JTextField txtAlamat;
    private JComboBox cmbStatus;

    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JPanel utama;
    private JTable table_1;

    private DefaultTableModel tableModel;
    private JTextField txtId;
    private JScrollPane tebel1;


    public static void main(String[] args) {
        JFrame frame = new JFrame("FormBuruh");
        frame.setContentPane(new FormBuruh().utama);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public void connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employment", "root", "901n6@Allah");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }


    void table_load() {
        try {
            pst = con.prepareStatement("SELECT * FROM employees");
            ResultSet rs = pst.executeQuery();

            // 1. Buat model tabel kosong
            DefaultTableModel model = new DefaultTableModel();

            // 2. Ambil metadata untuk kolom
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 3. Tambahkan nama kolom ke model
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // 4. Tambahkan data baris dari ResultSet
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                model.addRow(rowData);
            }

            // 5. Set model ke JTablex`
            table_1.setModel(model);

            // Tutup ResultSet dan PreparedStatement
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading table: " + e.getMessage());
        }
    }



    public FormBuruh() {
        connect ();
        table_load();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name,nik_id,jabatan_kerja,jenis_kelamin,alamat,status;
                name = txtName.getText();
                nik_id = txtNikId.getText();
                jabatan_kerja = cmbJabatan.getSelectedItem().toString();
                jenis_kelamin = cmbJenisKelamin.getSelectedItem().toString();
                alamat = txtAlamat.getText();
                status = cmbStatus.getSelectedItem().toString();


                try {
                    pst = con.prepareStatement("insert into employees(name,nik_id,jabatan_kerja,jenis_kelamin,alamat,status)values(?,?,?,?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, nik_id);
                    pst.setString(3, jabatan_kerja);
                    pst.setString(4, jenis_kelamin);
                    pst.setString(5, alamat);
                    pst.setString(6, status);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data baru telah ditambahkan!");
//                    table_load();
                    txtName.setText("");
                    txtNikId.setText("");
                    cmbJabatan.setSelectedIndex(-1);
                    cmbJenisKelamin.setSelectedIndex(-1);
                    txtAlamat.setText("");
                    cmbStatus.setSelectedIndex(-1);
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }


        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nik_id = txtId.getText();

                    pst = con.prepareStatement("select name,nik_id,jabatan_kerja,jenis_kelamin,alamat,status from employees where nik_id = ?");
                    pst.setString(1, nik_id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String nik_id1 = rs.getString(2);
                        String jabatan_kerja = rs.getString(3);
                        String jenis_kelamin = rs.getString(4);
                        String alamat = rs.getString(5);
                        String status = rs.getString(6);

                        txtName.setText(name);
                        txtNikId.setText(nik_id1);
                        cmbJabatan.setSelectedItem(jabatan_kerja);
                        cmbJenisKelamin.setSelectedItem(jenis_kelamin);
                        txtAlamat.setText(alamat);
                        cmbStatus.setSelectedItem(status);
                    }
                    else
                    {
                        txtName.setText("");
                        txtNikId.setText("");
                        cmbJabatan.setSelectedIndex(-1);
                        cmbJenisKelamin.setSelectedIndex(-1);
                        txtAlamat.setText("");
                        cmbStatus.setSelectedIndex(-1);

                        JOptionPane.showMessageDialog(null, "Tidak ditemukan data dengan NIK ID tersebut!");
                    }
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,nik_id,jabatan_kerja,jenis_kelamin,alamat,status;
                name = txtName.getText();
                nik_id = txtNikId.getText();
                jabatan_kerja = cmbJabatan.getSelectedItem().toString();
                jenis_kelamin = cmbJenisKelamin.getSelectedItem().toString();
                alamat = txtAlamat.getText();
                status = cmbStatus.getSelectedItem().toString();

                try {
                    pst = con.prepareStatement("update employees set name= ?,jabatan_kerja= ?,jenis_kelamin= ?,alamat= ?,status= ? where nik_id= ?");
                    pst.setString(1, name);
                    pst.setString(2, jabatan_kerja);
                    pst.setString(3, jenis_kelamin);
                    pst.setString(4, alamat);
                    pst.setString(5, status);
                    pst.setString(6, nik_id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data telah diupdate!");
//                    table_load();
                    txtName.setText("");
                    txtNikId.setText("");
                    cmbJabatan.setSelectedIndex(-1);
                    cmbJenisKelamin.setSelectedIndex(-1);
                    txtAlamat.setText("");
                    cmbStatus.setSelectedIndex(-1);
                    txtName.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String snik_id;
                snik_id = txtNikId.getText();
                try {
                    pst = con.prepareStatement("delete from employees where nik_id = ?");
                    pst.setString(1, snik_id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data telah dihapus!");
//                    table_load();
                    txtName.setText("");
                    txtNikId.setText("");
                    cmbJabatan.setSelectedIndex(-1);
                    cmbJenisKelamin.setSelectedIndex(-1);
                    txtAlamat.setText("");
                    cmbStatus.setSelectedIndex(-1);
                    txtName.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}

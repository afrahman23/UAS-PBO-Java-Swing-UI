package org.siswasmp;

                import net.sf.jasperreports.engine.JasperCompileManager;
                import net.sf.jasperreports.engine.JasperFillManager;
                import net.sf.jasperreports.engine.JasperPrint;
                import net.sf.jasperreports.view.JasperViewer;

                import javax.swing.*;
                import javax.swing.table.DefaultTableModel;
                import java.awt.*;
                import java.awt.event.ActionEvent;
                import java.awt.event.ActionListener;
                import java.sql.*;
                import java.util.HashMap;



public class FormSiswa {
                    private JTextField txtName;
                    private JTextField txtSiswaId;
                    private JComboBox<String> cmbPembayaran;
                    private JComboBox<String> cmbKelas;
                    private JComboBox<String> cmbJurusan;
                    private JTextField txtJumlah;
                    private JButton btnSave;
                    private JButton btnUpdate;
                    private JButton btnDelete;
                    private JButton btnSearch;
                    private JPanel utama;
                    private JTable table_1;
                    private DefaultTableModel tableModel;
                    private JTextField txtId;
                    private JScrollPane tebel1;
                    private JButton cetakButton;
                    private JButton PRINTButton;
//                    private JPanel mainPanel;



                    public static void main(String[] args) {
                        JFrame frame = new JFrame("FormSiswa");
                        frame.setContentPane(new FormSiswa().utama);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                    }

                    Connection con;
                    PreparedStatement pst;

                    public void connect() {
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbProjectSiswa", "root", "901n6@Allah");
                            System.out.println("Sukses terhubung ke database");
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }

                    void table_load() {
                        try {
                            pst = con.prepareStatement("SELECT * FROM ProjectSiswa");
                            ResultSet rs = pst.executeQuery();

                            DefaultTableModel model = new DefaultTableModel();
                            ResultSetMetaData metaData = rs.getMetaData();
                            int columnCount = metaData.getColumnCount();

                            for (int i = 1; i <= columnCount; i++) {
                                model.addColumn(metaData.getColumnName(i));
                            }

                            while (rs.next()) {
                                Object[] rowData = new Object[columnCount];
                                for (int i = 1; i <= columnCount; i++) {
                                    rowData[i - 1] = rs.getObject(i);
                                }
                                model.addRow(rowData);
                            }

                            table_1.setModel(model);
                            rs.close();
                            pst.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error mengambil data dari MySQL: " + e.getMessage());
                        }
                    }

                    public FormSiswa() {
                        connect();
                        table_load();

                        Timer timer = new Timer(5000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                table_load();
                            }
                        });
                        timer.start();

                        btnSave.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String name, siswa_id, jenis_pembayaran, nama_kelas, jumlah, jurusan;
                                name = txtName.getText();
                                siswa_id = txtSiswaId.getText();
                                jenis_pembayaran = cmbPembayaran.getSelectedItem().toString();
                                nama_kelas = cmbKelas.getSelectedItem().toString();
                                jumlah = txtJumlah.getText();
                                jurusan = cmbJurusan.getSelectedItem().toString();

                                try {
                                    pst = con.prepareStatement("insert into ProjectSiswa(name,siswa_id,jenis_pembayaran,nama_kelas,jumlah,jurusan)values(?,?,?,?,?,?)");
                                    pst.setString(1, name);
                                    pst.setString(2, siswa_id);
                                    pst.setString(3, jenis_pembayaran);
                                    pst.setString(4, nama_kelas);
                                    pst.setString(5, jumlah);
                                    pst.setString(6, jurusan);
                                    pst.executeUpdate();
                                    JOptionPane.showMessageDialog(null, "Data baru telah ditambahkan!");
                                    txtName.setText("");
                                    txtSiswaId.setText("");
                                    cmbPembayaran.setSelectedIndex(-1);
                                    cmbKelas.setSelectedIndex(-1);
                                    txtJumlah.setText("");
                                    cmbJurusan.setSelectedIndex(-1);
                                    txtName.requestFocus();
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });

                        btnSearch.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    String siswa_id = txtId.getText();
                                    pst = con.prepareStatement("select name,siswa_id,jenis_pembayaran,nama_kelas,jumlah,jurusan from ProjectSiswa where siswa_id = ?");
                                    pst.setString(1, siswa_id);
                                    ResultSet rs = pst.executeQuery();

                                    if (rs.next() == true) {
                                        String name = rs.getString(1);
                                        String siswa_id1 = rs.getString(2);
                                        String jenis_pembayaran = rs.getString(3);
                                        String nama_kelas = rs.getString(4);
                                        String jumlah = rs.getString(5);
                                        String jurusan = rs.getString(6);

                                        txtName.setText(name);
                                        txtSiswaId.setText(siswa_id1);
                                        cmbPembayaran.setSelectedItem(jenis_pembayaran);
                                        cmbKelas.setSelectedItem(nama_kelas);
                                        txtJumlah.setText(jumlah);
                                        cmbJurusan.setSelectedItem(jurusan);
                                    } else {
                                        txtName.setText("");
                                        txtSiswaId.setText("");
                                        cmbPembayaran.setSelectedIndex(-1);
                                        cmbKelas.setSelectedIndex(-1);
                                        txtJumlah.setText("");
                                        cmbJurusan.setSelectedIndex(-1);
                                        JOptionPane.showMessageDialog(null, "Tidak ditemukan data dengan ID Siswa tersebut!");
                                    }
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });

                        btnUpdate.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String name, siswa_id, jenis_pembayaran, nama_kelas, jumlah, jurusan;
                                name = txtName.getText();
                                siswa_id = txtSiswaId.getText();
                                jenis_pembayaran = cmbPembayaran.getSelectedItem().toString();
                                nama_kelas = cmbKelas.getSelectedItem().toString();
                                jumlah = txtJumlah.getText();
                                jurusan = cmbJurusan.getSelectedItem().toString();

                                try {
                                    pst = con.prepareStatement("update ProjectSiswa set name= ?,jenis_pembayaran= ?,nama_kelas= ?,jumlah= ?,jurusan= ? where siswa_id= ?");
                                    pst.setString(1, name);
                                    pst.setString(2, jenis_pembayaran);
                                    pst.setString(3, nama_kelas);
                                    pst.setString(4, jumlah);
                                    pst.setString(5, jurusan);
                                    pst.setString(6, siswa_id);
                                    pst.executeUpdate();
                                    JOptionPane.showMessageDialog(null, "Data telah diupdate!");
                                    txtName.setText("");
                                    txtSiswaId.setText("");
                                    cmbPembayaran.setSelectedIndex(-1);
                                    cmbKelas.setSelectedIndex(-1);
                                    txtJumlah.setText("");
                                    cmbJurusan.setSelectedIndex(-1);
                                    txtName.requestFocus();
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });

                        btnDelete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String siswa_id;
                                siswa_id = txtSiswaId.getText();
                                try {
                                    pst = con.prepareStatement("delete from ProjectSiswa where siswa_id = ?");
                                    pst.setString(1, siswa_id);
                                    pst.executeUpdate();
                                    JOptionPane.showMessageDialog(null, "Data telah dihapus!");
                                    txtName.setText("");
                                    txtSiswaId.setText("");
                                    cmbPembayaran.setSelectedIndex(-1);
                                    cmbKelas.setSelectedIndex(-1);
                                    txtJumlah.setText("");
                                    cmbJurusan.setSelectedIndex(-1);
                                    txtName.requestFocus();
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });


//                        cetakButton.addActionListener(new ActionListener() {
//                            @Override
//                            public void actionPerformed(ActionEvent e) {
//
//                            }
//                        });

                        // Add ActionListener to the button
                        cetakButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
//                                try{
//                                    java.sql.Connection conn = (Connection)KoneksiDatabase.koneksiDB();
//                                    String path = "src/javaapplication132/CetakKaryawan.jasper";
//                                    HashMap<String, Object>  parameters = new HashMap<> ();
//                                    JasperPrint print = JasperFillManager.fillReport(path, parameters, conn);
//                                    JasperViewer viewer = new JasperViewer(print, false);
//                                    viewer.setVisible(true);
//                                }
//                                catch (Exception ex) {
//                                    ex.printStackTrace();
//                                }
try {
    // Compile the .jrxml file to .jasper file
    String jrxmlFilePath = "src/main/java/org/siswasmp/report.jrxml";
    String jasperFilePath = "src/main/java/org/siswasmp/report.jasper";
    JasperCompileManager.compileReportToFile(jrxmlFilePath, jasperFilePath);

    // Establish a database connection
//    java.sql.Connection conn = KoneksiDatabase.koneksiDB();

    // Fill the report with data
    HashMap<String, Object> parameters = new HashMap<>();
    java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbProjectSiswa", "root", "901n6@Allah");
    JasperPrint print = JasperFillManager.fillReport(jasperFilePath, parameters, conn);

    // Display the report
    JasperViewer viewer = new JasperViewer(print, false);
    viewer.setVisible(true);
} catch (Exception ex) {
    ex.printStackTrace();
}

//                                try {
//                                    // Step 1: Load the compiled .jasper file
//                                    // Path to your compiled .jasper file
//                                    String jrxmlFilePath = "src/main/java/org/siswasmp/report.jrxml";
//                                    // Path where the compiled .jasper file will be saved
//                                    String jasperFilePath = "src/main/java/org/siswasmp//report.jasper";
//
//                                    // Compile the .jrxml file to .jasper file
//                                    JasperCompileManager.compileReportToFile(jrxmlFilePath, jasperFilePath);
//
//                                    System.out.println("Report compiled successfully!");
//
//                                    // Step 2: Establish a database connection (if needed)
//                                    Connection conn = DriverManager.getConnection(
//                                            "jdbc:mysql://localhost:3306/dbProjectSiswa", "root", "901n6@Allah");
//
//                                    // Step 3: Fill the report with data
//                                    HashMap<String, Object> parameters = new HashMap<>();
//                                    parameters.put("ReportTitle", "Sample Report"); // Example parameter
//
//                                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFilePath, parameters, conn);
//
//                                    // Step 4: Display or print the report
//                                    JasperViewer viewer = new JasperViewer(jasperPrint, false); // Open the report in a viewer
//                                    viewer.setVisible(true);
//
//                                    // Alternatively, print directly without showing the viewer:
//                                    // JasperPrintManager.printReport(jasperPrint, true);
//
//                                } catch (Exception ex) {
//                                    ex.printStackTrace();
//
//                                }
                            }
                        });
                    }

                    private void createUIComponents() {
                        cmbPembayaran = new JComboBox<>(new String[]{"Tunai", "Transfer"});
                        cmbKelas = new JComboBox<>(new String[]{"1A", "1B", "2A", "2B", "3A", "3B"});
                        cmbJurusan = new JComboBox<>(new String[]{"IPA", "IPS", "Bahasa"});
                    }

                    public Component getMainPanel() {
                        return utama;

                    }
                }
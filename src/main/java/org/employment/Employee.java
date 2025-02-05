package org.employment;
// Employee.java
public class Employee {
    private String name;
    private String nikId;
    private String tempatLahir;
    private String jenisKelamin;
    private String alamat;
    private String status;

    // Constructor
    public Employee(String name, String nikId, String tempatLahir, String jenisKelamin, String alamat, String status) {
        this.name = name;
        this.nikId = nikId;
        this.tempatLahir = tempatLahir;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.status = status;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getNikId() {
        return nikId;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getStatus() {
        return status;
    }
}
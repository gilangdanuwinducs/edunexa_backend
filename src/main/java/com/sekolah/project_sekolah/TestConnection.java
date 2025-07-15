package com.sekolah.project_sekolah;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/db_edunexa2";
        String username = "root";
        String password = ""; // kosong jika pakai XAMPP default

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("✅ Koneksi ke database berhasil!");
        } catch (Exception e) {
            System.out.println("❌ Gagal konek ke DB:");
            e.printStackTrace();
        }
    }
}

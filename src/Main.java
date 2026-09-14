package az.example.ledger;

import az.example.ledger.config.DbConfig;
import az.example.ledger.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DbConfig config = new DbConfig();

        // Diagnostic prints
        System.out.println("--- DEBUG INFO ---");
        System.out.println("URL: " + config.getUrl());
        System.out.println("USER: " + config.getUser());
        System.out.println("PASSWORD: " + config.getPassword());
        System.out.println("------------------");

        try (Connection conn = ConnectionFactory.open(config);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {

            if (rs.next()) {
                System.out.println("Connection Successful! Test query output: " + rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
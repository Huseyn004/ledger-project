package az.example.ledger.db;

import az.example.ledger.config.DbConfig;
import az.example.ledger.exception.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private final DbConfig config;

    public ConnectionFactory(DbConfig config) {
        this.config = config;
    }

    public static Connection open(DbConfig config) {
        try {
            return DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
        } catch (SQLException e) {
            throw new DataAccessException("Cannot connect to database", e);
        }
    }
}
package az.example.ledger.db;

import az.example.ledger.exception.DataAccessException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

public class DbInitializer {
    private final ConnectionFactory connectionFactory;

    public DbInitializer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void init() {
        try (InputStream is = getClass().getResourceAsStream("/schema.sql");
             Connection conn = connectionFactory.open();
             Statement stmt = conn.createStatement()) {

            if (is == null) return;
            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            stmt.execute(sql);
        } catch (Exception e) {
            throw new DataAccessException("Database initialization failed", e);
        }
    }
}
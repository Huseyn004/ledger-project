package az.example.ledger.repository;

import az.example.ledger.db.ConnectionFactory;
import az.example.ledger.exception.DataAccessException;

import java.sql.*;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository {
    private final ConnectionFactory connectionFactory;

    public JdbcUserRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Optional<UserRecord> findByUsername(String username) {
        String sql = "SELECT id, username, password_hash, salt FROM app_user WHERE username = ?";
        try (Connection conn = connectionFactory.open();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new UserRecord(
                            rs.getLong("id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getString("salt")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to find user " + username, e);
        }
    }
}
package az.example.ledger.service;

import az.example.ledger.db.ConnectionFactory;
import az.example.ledger.exception.DataAccessException;
import az.example.ledger.repository.UserRepository;
import az.example.ledger.repository.UserRepository.UserRecord;
import az.example.ledger.security.PasswordHasher;

import java.sql.*;
import java.util.Arrays;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConnectionFactory connectionFactory;

    public UserServiceImpl(UserRepository userRepository, ConnectionFactory connectionFactory) {
        this.userRepository = userRepository;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Optional<UserRecord> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserRecord create(String username, char[] rawPassword) {
        byte[] salt = PasswordHasher.newSalt();
        byte[] hash = PasswordHasher.hash(rawPassword, salt);
        String saltStr = PasswordHasher.toBase64(salt);
        String hashStr = PasswordHasher.toBase64(hash);
        Arrays.fill(rawPassword, '\0');

        String sql = "INSERT INTO app_user (username, password_hash, salt) VALUES (?, ?, ?)";
        try (Connection conn = connectionFactory.open();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, hashStr);
            ps.setString(3, saltStr);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return new UserRecord(rs.getLong(1), username, hashStr, saltStr);
                throw new DataAccessException("Failed to get generated user ID");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error creating user", e);
        }
    }
}
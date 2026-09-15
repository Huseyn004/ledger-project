package az.example.ledger.repository;

import java.util.Optional;

public interface UserRepository {
    Optional<UserRecord> findByUsername(String username);
    record UserRecord(Long id, String username, String passwordHash, String salt) {}
}
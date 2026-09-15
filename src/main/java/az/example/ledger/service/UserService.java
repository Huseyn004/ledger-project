package az.example.ledger.service;

import az.example.ledger.repository.UserRepository.UserRecord;
import java.util.Optional;

public interface UserService {
    Optional<UserRecord> findByUsername(String username);
    UserRecord create(String username, char[] rawPassword);
}
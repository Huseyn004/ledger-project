package az.example.ledger.security;

import az.example.ledger.exception.AuthenticationException;
import az.example.ledger.repository.UserRepository;
import az.example.ledger.repository.UserRepository.UserRecord;

import java.util.Arrays;

public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Session login(String username, char[] password) {
        try {
            UserRecord user = userRepository.findByUsername(username)
                    .orElseThrow(() ->
                            new AuthenticationException("Invalid credentials"));

            byte[] salt = PasswordHasher.fromBase64(user.salt());
            byte[] expectedHash = PasswordHasher.fromBase64(user.passwordHash());

            if (!PasswordHasher.matches(password, salt, expectedHash)) {
                throw new AuthenticationException("Invalid credentials");
            }

            return new Session(user.id(), user.username());

        } finally {
            Arrays.fill(password, '\0');
        }
    }
}
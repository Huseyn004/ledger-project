package az.example.ledger.security;

import az.example.ledger.exception.LedgerException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordHasher {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 120_000;
    private static final int KEY_LENGTH = 256;

    private PasswordHasher() {
    }

    public static byte[] newSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public static byte[] hash(char[] password, byte[] salt) {
        try {
            PBEKeySpec spec =
                    new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);

            try {
                SecretKeyFactory factory =
                        SecretKeyFactory.getInstance(ALGORITHM);

                return factory.generateSecret(spec).getEncoded();

            } finally {
                spec.clearPassword();
            }

        } catch (GeneralSecurityException e) {
            throw new LedgerException("Error hashing password", e);
        }
    }

    public static boolean matches(
            char[] password,
            byte[] salt,
            byte[] expectedHash) {

        byte[] actualHash = hash(password, salt);

        return MessageDigest.isEqual(actualHash, expectedHash);
    }

    public static String toBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] fromBase64(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
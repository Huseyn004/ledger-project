package az.example.ledger.security;

public class PasswordGenerator {

    public static void main(String[] args) {
        char[] password = "admin123".toCharArray();

        byte[] salt = PasswordHasher.newSalt();
        byte[] hash = PasswordHasher.hash(password, salt);

        System.out.println("Username: admin");
        System.out.println("Password: admin123");
        System.out.println("Salt: " + PasswordHasher.toBase64(salt));
        System.out.println("Hash: " + PasswordHasher.toBase64(hash));
    }
}
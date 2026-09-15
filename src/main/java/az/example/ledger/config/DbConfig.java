package az.example.ledger.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbConfig {

    private final String url;
    private final String username;
    private final String password;

    public DbConfig() {
        Map<String, String> env = loadEnvFile();

        this.url = env.get("DB_URL");
        this.username = env.get("DB_USER");
        this.password = env.get("DB_PASSWORD");
    }

    public DbConfig(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Map<String, String> loadEnvFile() {
        Map<String, String> values = new HashMap<>();

        Path envFile = Path.of(".env").toAbsolutePath();

        System.out.println("Looking for .env at: " + envFile);
        System.out.println("File exists: " + Files.exists(envFile));

        try {
            List<String> lines = Files.readAllLines(envFile);

            for (String line : lines) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    values.put(key, value);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not read .env file: " + envFile,
                    e
            );
        }

        return values;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
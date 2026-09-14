package az.example.ledger.config;

public class DbConfig {
    private final String url;
    private final String user;
    private final String password;

    public DbConfig() {
        this.url = "jdbc:postgresql://localhost:5433/ledger";
        this.user = "ledger_user";
        this.password = "ledger_secret_pass_123";
    }

    public String getUrl() { return url; }
    public String getUser() { return user; }
    public String getPassword() { return password; }
}
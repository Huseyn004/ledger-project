package az.example.ledger.repository;

import az.example.ledger.db.ConnectionFactory;
import az.example.ledger.exception.DataAccessException;
import az.example.ledger.model.Account;
import az.example.ledger.model.CurrentAccount;
import az.example.ledger.model.SavingsAccount;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcAccountRepository implements AccountRepository {
    private final ConnectionFactory connectionFactory;

    public JdbcAccountRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        String sql = "SELECT account_number, account_type, balance FROM account WHERE account_number = ?";
        try (Connection conn = connectionFactory.open();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, number);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch account: " + number, e);
        }
    }

    @Override
    public List<Account> findAll() {
        String sql = "SELECT account_number, account_type, balance FROM account";
        List<Account> list = new ArrayList<>();
        try (Connection conn = connectionFactory.open();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("Failed to fetch accounts", e);
        }
    }

    @Override
    public void save(Account account) {
        String sql = "INSERT INTO account (account_number, customer_id, account_type, balance) VALUES (?, 1, ?, ?) " +
                "ON CONFLICT (account_number) DO UPDATE SET balance = EXCLUDED.balance";
        try (Connection conn = connectionFactory.open();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getType());
            ps.setBigDecimal(3, account.getBalance());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Failed to save account", e);
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        String num = rs.getString("account_number");
        BigDecimal bal = rs.getBigDecimal("balance");
        String type = rs.getString("account_type");
        return "SAVINGS".equalsIgnoreCase(type) ? new SavingsAccount(num, bal) : new CurrentAccount(num, bal);
    }
}
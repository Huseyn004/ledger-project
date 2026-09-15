package az.example.ledger.model;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    public CurrentAccount(String accountNumber, BigDecimal balance) {
        super(accountNumber, balance);
    }

    @Override
    public String getType() { return "CURRENT"; }
}
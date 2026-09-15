package az.example.ledger.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(Long id, String accountNumber, TransactionType type, BigDecimal amount, LocalDateTime timestamp) {}
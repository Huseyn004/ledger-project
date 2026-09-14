package az.example.ledger.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MoneyFormatter {
    private static final DecimalFormat FORMATTER = new DecimalFormat("#,##0.00 AZN");

    public static String format(BigDecimal amount) {
        if (amount == null) {
            return "0.00 AZN";
        }
        return FORMATTER.format(amount);
    }
}
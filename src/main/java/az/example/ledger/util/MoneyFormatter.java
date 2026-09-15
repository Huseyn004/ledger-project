package az.example.ledger.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyFormatter {
    public static String format(BigDecimal amount) {
        if (amount == null) return "$0.00";
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }
}
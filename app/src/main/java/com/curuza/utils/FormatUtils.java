package com.curuza.utils;

import java.text.NumberFormat;

public class FormatUtils {
    private FormatUtils() {}

    public static String getLocalizedMonetaryAmountString(Double amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(amount);
    }

    public static String getLocalizedMonetaryAmountString(long amount) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(amount);
    }
}

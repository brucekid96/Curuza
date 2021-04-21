package com.curuza.domain;


import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

public class DateTimeUtils {

    public static String getDateString(String timestamp){
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(timestamp), ZoneId.systemDefault());
        String dateStr = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(dateTime);
        return dateStr;
    }
}

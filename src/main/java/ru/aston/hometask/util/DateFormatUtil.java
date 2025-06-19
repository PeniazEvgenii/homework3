package ru.aston.hometask.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DateFormatUtil {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public LocalDate parseDateFromString(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static boolean isValidDate(String date) {
        try {
            parseDateFromString(date);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }
}

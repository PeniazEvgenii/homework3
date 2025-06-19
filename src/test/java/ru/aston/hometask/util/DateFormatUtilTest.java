package ru.aston.hometask.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DateFormatUtilTest {

    @ParameterizedTest
    @MethodSource("getTrueDateStrings")
    void shouldReturnTrueOnCorrectDate(String date) {
        assertTrue(DateFormatUtil.isValidDate(date));
    }

    @ParameterizedTest
    @MethodSource("getFalseDateStrings")
    void shouldReturnFalseOnIncorrectDate(String date) {
        assertFalse(DateFormatUtil.isValidDate(date));
    }

    static Stream<String> getTrueDateStrings() {
        return Stream.of(
                "01-01-2020",
                "31-01-2018",
                "31-12-2024");
    }

    static Stream<String> getFalseDateStrings() {
        return Stream.of(
                "1-1-2020",
                "12-31-2018",
                "2020-12-31",
                "01-12-24");
    }

    @Test
    void shouldGetLocalDateFromString() {
        LocalDate expectResult = LocalDate.of(2020, 1, 1);

        LocalDate actualResult = DateFormatUtil.parseDateFromString("01-01-2020");

        assertEquals(expectResult, actualResult);
    }
}
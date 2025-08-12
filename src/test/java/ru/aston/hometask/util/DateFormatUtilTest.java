package ru.aston.hometask.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DateFormatUtilTest {

    @ParameterizedTest
    @MethodSource("getValidDateStrings")
    void when_dateStringIsValid_then_returnTrue(String date) {
        assertTrue(DateFormatUtil.isValidDate(date));
    }

    @ParameterizedTest
    @MethodSource("getInvalidDateStrings")
    void when_dateStringIsInvalid_then_returnFalse(String date) {
        assertFalse(DateFormatUtil.isValidDate(date));
    }

    static Stream<String> getValidDateStrings() {
        return Stream.of(
                "01-01-2020",
                "31-01-2018",
                "31-12-2024");
    }

    static Stream<String> getInvalidDateStrings() {
        return Stream.of(
                "1-1-2020",
                "12-31-2018",
                "2020-12-31",
                "01-12-24");
    }

    @Test
    void   when_parseValidDateString_then_returnLocalDate() {
        LocalDate expectResult = LocalDate.of(2020, 1, 1);

        LocalDate actualResult = DateFormatUtil.parseDateFromString("01-01-2020");

        assertEquals(expectResult, actualResult);
    }
}
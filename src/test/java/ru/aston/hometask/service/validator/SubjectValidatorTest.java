package ru.aston.hometask.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.exception.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

class SubjectValidatorTest {

    private SubjectValidator subjectValidator;

    @BeforeEach
    void init() {
        subjectValidator = new SubjectValidator();
    }

    @Test
    void shouldThrowOnNull() {
        assertThrows(ValidationException.class, () -> subjectValidator.valid(null));
    }

    @Test
    void shouldThrowOnEmpty() {
        assertThrows(ValidationException.class, () -> subjectValidator.valid(""));
    }

    @Test
    void shouldThrowOnSpaces() {
        assertThrows(ValidationException.class, () -> subjectValidator.valid("    "));
    }

    @Test
    void shouldThrowOnForeign() {
        assertThrows(ValidationException.class, () -> subjectValidator.valid("math"));
    }

    @Test
    void shouldNotThrowOnOneRusWord() {
        assertDoesNotThrow(() -> subjectValidator.valid("математика"));
    }

    @Test
    void shouldNotThrowOnTwoRusWord() {
        assertDoesNotThrow(() -> subjectValidator.valid("высшая математика"));
    }

    @Test
    void shouldNotThrowOnTwoRusWordWithHyphen() {
        assertDoesNotThrow(() -> subjectValidator.valid("высшая-математика"));
    }

}
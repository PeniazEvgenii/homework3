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
    void when_subjectIsNull_then_throwValidationException() {
        assertThrows(ValidationException.class, () -> subjectValidator.valid(null));
    }

    @Test
    void when_subjectIsEmptyString_then_throwValidationException() {
        assertThrows(ValidationException.class, () -> subjectValidator.valid(""));
    }

    @Test
    void when_subjectIsOnlySpaces_then_throwValidationException() {
        assertThrows(ValidationException.class, () -> subjectValidator.valid("    "));
    }

    @Test
    void when_subjectInForeignLanguage_then_throwValidationException() {
        assertThrows(ValidationException.class, () -> subjectValidator.valid("math"));
    }

    @Test
    void when_subjectIsSingleRussianWord_then_notThrow() {
        assertDoesNotThrow(() -> subjectValidator.valid("математика"));
    }

    @Test
    void when_subjectIsTwoRussianWords_then_notThrow() {
        assertDoesNotThrow(() -> subjectValidator.valid("высшая математика"));
    }

    @Test
    void when_subjectHasHyphenatedRussianWords_then_notThrow() {
        assertDoesNotThrow(() -> subjectValidator.valid("высшая-математика"));
    }

}
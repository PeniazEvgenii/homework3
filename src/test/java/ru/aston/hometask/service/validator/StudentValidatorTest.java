package ru.aston.hometask.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.exception.ValidationException;
import ru.aston.hometask.exception.error.ValidationError;
import ru.aston.hometask.model.Student;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentValidatorTest {

    private Student student;
    private StudentValidator studentValidator;

    @BeforeEach
    void init() {
        studentValidator = new StudentValidator();
        student = Student.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Test
    void when_allFieldsEmpty_then_throwThreeErrors() {
        student.setFirstname("");
        student.setLastname("");
        student.setBirthDate("");

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> studentValidator.valid(student));

        List<ValidationError> errors = ex.getErrors();

        assertEquals(3, errors.size(), "Должно быть три ошибки валидации");
    }

    @Test
    void when_firstnameNull_then_throwValidationException() {
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void when_firstnameHasTwoWords_then_throwValidationException() {
        student.setFirstname("Иван иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void when_firstnameInForeignLanguage_then_throwValidationException() {
        student.setFirstname("Ivan");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void when_firstnameIsSingleRussianWord_then_notThrow() {
        student.setFirstname("Иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void when_firstnameContainsHyphen_then_notThrow() {
        student.setFirstname("Иван-иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void when_lastnameNull_then_throwValidationException() {
        student.setFirstname("Иван");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void when_lastnameHasTwoWords_then_throwValidationException() {
        student.setFirstname("Иван");
        student.setLastname("Петров петров");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void when_lastnameInForeignLanguage_then_throwValidationException() {
        student.setFirstname("Иван");
        student.setLastname("Ivanov");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void when_lastnameIsSingleRussianWord_then_notThrow() {
        student.setFirstname("Иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void when_lastnameContainsHyphen_then_notThrow() {
        student.setFirstname("Иван");
        student.setLastname("Петров-Иванов");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void when_birthDateIsCorrectFormat_then_notThrow() {
        student.setFirstname("Иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void when_birthDateIsIncorrectFormat_then_throwValidationException() {
        student.setFirstname("Иван");
        student.setLastname("Петров");
        student.setBirthDate("2000-12-31");

        assertThrows(ValidationException.class ,() -> studentValidator.valid(student));
    }
}
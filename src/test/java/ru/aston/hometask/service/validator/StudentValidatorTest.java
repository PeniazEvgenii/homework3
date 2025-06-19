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
    void shouldThrowExceptionWithThreeError() {
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
    void shouldThrowOnFirstnameNull() {
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void shouldThrowOnFirstnameTwoWord() {
        student.setFirstname("Иван иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void shouldThrowOnFirstnameInForeignLanguage() {
        student.setFirstname("Ivan");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void shouldNotThrowOnFirstnameWithOneRusWord() {
        student.setFirstname("Иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void shouldNotThrowOnFirstnameTwoWordWithHyphen() {
        student.setFirstname("Иван-иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void shouldThrowOnLastnameNull() {
        student.setFirstname("Иван");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void shouldThrowOnLastnameTwoWord() {
        student.setFirstname("Иван");
        student.setLastname("Петров петров");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void shouldThrowOnLastnameInForeignLanguage() {
        student.setFirstname("Иван");
        student.setLastname("Ivanov");
        student.setBirthDate("31-10-2010");

        assertThrows(ValidationException.class, () -> studentValidator.valid(student));
    }

    @Test
    void shouldNotThrowOnLastnameWithOneRusWord() {
        student.setFirstname("Иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void shouldNotThrowOnLastnameTwoWordWithHyphen() {
        student.setFirstname("Иван");
        student.setLastname("Петров-Иванов");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void shouldNotThrowOnCorrectDate() {
        student.setFirstname("Иван");
        student.setLastname("Петров");
        student.setBirthDate("31-10-2010");

        assertDoesNotThrow(() -> studentValidator.valid(student));
    }

    @Test
    void shouldThrowOnIncorrectDate() {
        student.setFirstname("Иван");
        student.setLastname("Петров");
        student.setBirthDate("2000-12-31");

        assertThrows(ValidationException.class ,() -> studentValidator.valid(student));
    }
}
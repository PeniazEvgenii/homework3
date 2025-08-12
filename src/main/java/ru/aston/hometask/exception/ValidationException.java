package ru.aston.hometask.exception;

import ru.aston.hometask.exception.error.ValidationError;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private final List<ValidationError> errors;

    public ValidationException(String message, List<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(List<ValidationError> errors) {
        this.errors = errors;
    }
}

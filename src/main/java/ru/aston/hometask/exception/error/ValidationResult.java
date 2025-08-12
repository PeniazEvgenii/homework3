package ru.aston.hometask.exception.error;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ValidationResult {
    private final List<ValidationError> errors = new ArrayList<>();

    public void addError(ValidationError error) {
        errors.add(error);
    }

    public boolean isErrorsEmpty() {
        return errors.isEmpty();
    }
}

package ru.aston.hometask.service.validator;

import ru.aston.hometask.exception.ValidationException;
import ru.aston.hometask.exception.error.ValidationError;
import ru.aston.hometask.exception.error.ValidationResult;
import ru.aston.hometask.service.api.IValidator;

public class SubjectValidator implements IValidator<String> {
    private static final String PATTERN_SUBJECT_NAME = "[а-яА-яЁё]+([- ][а-яА-яЁё]+)*";
    private static final String ERROR_NAME = "Неверное название предмета";

    @Override
    public void valid(String subject) {
        ValidationResult result = new ValidationResult();

        if (subject == null || subject.isBlank()) {
            result.addError(new ValidationError(ERROR_NAME, "Не введено название предмета или состоит из пробельных символов"));
        } else if (!subject.matches(PATTERN_SUBJECT_NAME)) {
            result.addError(new ValidationError(ERROR_NAME, "Название предмета должно быть на русском с использованием символов пробела и дефис"));
        }

        if (!result.isErrorsEmpty()) {
            throw new ValidationException(result.getErrors());
        }
    }
}

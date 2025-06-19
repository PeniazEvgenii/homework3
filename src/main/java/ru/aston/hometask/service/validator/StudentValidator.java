package ru.aston.hometask.service.validator;

import ru.aston.hometask.model.Student;
import ru.aston.hometask.exception.ValidationException;
import ru.aston.hometask.exception.error.ValidationError;
import ru.aston.hometask.exception.error.ValidationResult;
import ru.aston.hometask.service.api.IValidator;
import ru.aston.hometask.util.DateFormatUtil;

public class StudentValidator implements IValidator<Student> {
    private static final String PATTERN_NAME = "[а-яА-ЯёЁ-]+";

    @Override
    public void valid(Student dto) {
        ValidationResult result = new ValidationResult();

        String firstname = dto.getFirstname();
        if (firstname == null || firstname.isBlank()) {
            result.addError(new ValidationError("Неверное имя", "Не введено имя или состоит из пробельных символов"));
        } else if (!firstname.matches(PATTERN_NAME)) {
            result.addError(new ValidationError("Неверное имя", "Имя должно быть на русском"));
        }


        String lastname = dto.getLastname();
        if (lastname == null || lastname.isBlank()) {
            result.addError(new ValidationError("Неверная фамилия", "Не введена фамилия или состоит из пробельных символов"));
        } else if (!lastname.matches(PATTERN_NAME)) {
            result.addError(new ValidationError("Неверная фамилия", "Фамилия должна быть на русском"));
        }

        String birthDate = dto.getBirthDate();
        if(birthDate == null || birthDate.isBlank()) {
            result.addError(new ValidationError("некорректная дата", "Не введена дата"));
        } else if (!DateFormatUtil.isValidDate(birthDate)) {
            result.addError(new ValidationError("некорректная дата", "введенная дата не соответствует формату"));
        }

        if(!result.isErrorsEmpty()) {
            throw new ValidationException(result.getErrors());
        }
    }
}

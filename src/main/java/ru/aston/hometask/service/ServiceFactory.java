package ru.aston.hometask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.hometask.config.PathConfiguration;
import ru.aston.hometask.model.Student;
import ru.aston.hometask.model.StudentMark;
import ru.aston.hometask.service.api.IFileService;
import ru.aston.hometask.service.api.IMarkService;
import ru.aston.hometask.service.api.IStudentService;
import ru.aston.hometask.service.api.ISubjectService;
import ru.aston.hometask.service.api.IValidator;
import ru.aston.hometask.service.validator.StudentValidator;
import ru.aston.hometask.service.validator.SubjectValidator;

public class ServiceFactory {
    private final PathConfiguration pathConfiguration;
    private final ObjectMapper objectMapper;

    private IStudentService studentService;
    private ISubjectService subjectService;
    private IMarkService markService;

    public ServiceFactory(PathConfiguration pathConfiguration, ObjectMapper objectMapper) {
        this.pathConfiguration = pathConfiguration;
        this.objectMapper = objectMapper;
    }

    public IStudentService getStudentService() {
        if (studentService == null) {
            studentService = createStudentService();
        }

        return studentService;
    }

    public ISubjectService getSubjectService() {
        if (subjectService == null) {
            subjectService = createSubjectService();
        }

        return subjectService;
    }

    public IMarkService getMarkService() {
        if (markService == null) {
            markService = createMarkService();
        }

        return markService;
    }

    private IStudentService createStudentService() {
        IFileService<Student> studentFileService = new FileService<>(objectMapper);
        IValidator<Student> studentValidator = new StudentValidator();

        return new StudentService(studentValidator, studentFileService, pathConfiguration.getStorePath());
    }

    private ISubjectService createSubjectService() {
        IFileService<String> subjectFileService = new FileService<>(objectMapper);
        IValidator<String> subjectValidator = new SubjectValidator();

        return new SubjectService(subjectValidator, subjectFileService, pathConfiguration.getStorePath());
    }

    private IMarkService createMarkService() {
        IFileService<StudentMark> markFileService = new FileService<>(objectMapper);

        return new MarkService(markFileService, pathConfiguration.getStorePath());
    }
}

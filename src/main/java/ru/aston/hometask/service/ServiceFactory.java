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
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final IStudentService studentService;
    private final ISubjectService subjectService;
    private final IMarkService markService;

    private ServiceFactory() {
        ObjectMapper objectMapper = new ObjectMapper();
        IFileService<Student> studentFileService = new FileService<>(objectMapper);
        IFileService<String> subjectFileService = new FileService<>(objectMapper);
        IFileService<StudentMark> markFileService = new FileService<>(objectMapper);

        IValidator<Student> studentValidator = new StudentValidator();
        IValidator<String> subjectValidator = new SubjectValidator();

        PathConfiguration pathConfiguration = new PathConfiguration();
        String storePath = pathConfiguration.getStorePath();

        this.studentService = new StudentService(studentValidator, studentFileService,storePath);
        this.subjectService = new SubjectService(subjectValidator, subjectFileService, storePath);
        this.markService = new MarkService(markFileService, storePath);
    }

    public IStudentService getStudentService() {
        return studentService;
    }

    public ISubjectService getSubjectService() {
        return subjectService;
    }

    public IMarkService getMarkService() {
        return markService;
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }
}

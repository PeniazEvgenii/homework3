package ru.aston.hometask.service;

import ru.aston.hometask.service.api.IFileService;
import ru.aston.hometask.service.api.ISubjectService;
import ru.aston.hometask.service.api.IValidator;

import java.nio.file.Path;
import java.util.List;

public class SubjectService implements ISubjectService {
    private static final String PATH_SUBJECT = "src/main/resources/subjects/listSubject.data";

    private final IFileService<String> fileService;
    private final IValidator<String> validator;
    private final Path path;

    public SubjectService(IValidator<String> validator, IFileService<String> fileService) {
        this.validator = validator;
        this.fileService = fileService;
        this.path = Path.of(PATH_SUBJECT);
    }

    @Override
    public void save(String subject) {
        validator.valid(subject);
        fileService.printString(path, subject);
    }

    @Override
    public List<String> getAll() {
        return fileService.readAllString(path);
    }
}

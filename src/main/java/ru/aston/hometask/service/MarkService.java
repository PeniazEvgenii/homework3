package ru.aston.hometask.service;

import ru.aston.hometask.model.StudentMark;
import ru.aston.hometask.service.api.IFileService;
import ru.aston.hometask.service.api.IMarkService;

import java.nio.file.Path;
import java.util.List;

public class MarkService implements IMarkService {
    private static final String PATH_SUBJECT_PREFIX = "marks/";
    private static final String PATH_SUBJECT_POSTFIX = ".data";

    private final IFileService<StudentMark> fileService;
    private final String pathDir;

    public MarkService(IFileService<StudentMark> fileService, String pathDir) {
        this.fileService = fileService;
        this.pathDir = pathDir;
    }

    @Override
    public void save(List<StudentMark> result, String subject) {
        Path path = Path.of(pathDir, PATH_SUBJECT_PREFIX, subject + PATH_SUBJECT_POSTFIX);

        fileService.printObject(path, result);
    }

    @Override
    public List<StudentMark> getAllBy(String subject) {
        Path path = Path.of(PATH_SUBJECT_PREFIX, subject + PATH_SUBJECT_POSTFIX);

        return fileService.readAll(path, StudentMark.class);
    }
}

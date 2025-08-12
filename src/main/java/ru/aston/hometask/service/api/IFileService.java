package ru.aston.hometask.service.api;

import java.nio.file.Path;
import java.util.List;

public interface IFileService<T> {
    void printString(Path path, String... strs);
    List<String> readAllString(Path path);
    void printObject(Path path, List<T> objects);
    List<T> readAll(Path path, Class<T> clazz);
}

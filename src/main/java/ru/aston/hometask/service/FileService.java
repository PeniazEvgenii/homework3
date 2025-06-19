package ru.aston.hometask.service;

import ru.aston.hometask.exception.FileServiceException;
import ru.aston.hometask.exception.SerializableException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.hometask.service.api.IFileService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileService<T> implements IFileService<T> {
    private final ObjectMapper objectMapper;

    public FileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void printString(Path path, String... strs) {
        checkExistDirectory(path);
        // Files.write(path,Arrays.asList(strs), StandardOpenOption.CREATE,StandardOpenOption.APPEND)
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            for (String str : strs) {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            throw new FileServiceException("Ошибка записи в файл", e);
        }
    }

    @Override
    public void printObject(Path path, List<T> objects) {
        String[] arrObjects = objects.stream()
                .map(this::serializeFrom)
                .toArray(String[]::new);
        printString(path, arrObjects);
    }

    @Override
    public List<String> readAllString(Path path) {
        if (!Files.exists(path)) {
            throw new FileServiceException("Файл не найден: " + path.getFileName());
        }

        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            return bufferedReader.lines()                                            // можно Files.readAllLines(path).stream()
                    .filter(s -> !s.isBlank())
                    .toList();
        } catch (IOException e) {
            throw new FileServiceException("Ошибка чтения файла " + path.getFileName(), e);
        }
    }

    @Override
    public List<T> readAll(Path path, Class<T> clazz) {
        return readAllString(path).stream()
                .map(s -> deserializeObject(s, clazz))
                .toList();
    }

    private static void checkExistDirectory(Path path) {
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new FileServiceException("Не удалось создать директорию", e);
        }
    }

    private String serializeFrom(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new SerializableException("Ошибка сериализации объекта", e);
        }
    }

    private T deserializeObject(String s, Class<T> clazz) {
        try {
            return objectMapper.readValue(s, clazz);
        } catch (JsonProcessingException e) {
            throw new SerializableException("Ошибка десериализации объекта", e);
        }
    }
}

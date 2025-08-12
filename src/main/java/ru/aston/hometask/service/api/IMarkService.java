package ru.aston.hometask.service.api;

import ru.aston.hometask.model.StudentMark;

import java.util.List;

public interface IMarkService {
    void save(List<StudentMark> result, String subject);
    List<StudentMark> getAllBy(String subject);
}

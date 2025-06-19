package ru.aston.hometask.service.api;


import java.util.List;

public interface ISubjectService {
    void save(String subject);
    List<String> getAll();
}

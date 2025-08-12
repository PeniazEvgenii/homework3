package ru.aston.hometask.service.api;

import ru.aston.hometask.model.Student;
import ru.aston.hometask.model.StudentFilter;

import java.util.List;

public interface IStudentService {
    void save(Student dto);
    List<Student> getAll();
    List<Student> findByFilter(StudentFilter filter);
}

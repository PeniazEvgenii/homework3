package ru.aston.hometask.service;

import ru.aston.hometask.model.Student;
import ru.aston.hometask.model.StudentFilter;
import ru.aston.hometask.service.api.IFileService;
import ru.aston.hometask.service.api.IStudentService;
import ru.aston.hometask.service.api.IValidator;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class StudentService implements IStudentService {
    private static final String PATH_STUDENT = "src/main/resources/students/listStudent.data";
    private static final String PATH_SEARCH_HISTORY = "src/main/resources/HISTORY/history_search.data";

    private final IValidator<Student> validator;
    private final IFileService<Student> fileService;

    public StudentService(IValidator<Student> validator, IFileService<Student> fileService) {
        this.validator = validator;
        this.fileService = fileService;
    }

    @Override
    public void save(Student student) {
        validator.valid(student);
        fileService.printObject(Path.of(PATH_STUDENT), List.of(student));
    }

    @Override
    public List<Student> getAll() {
        return fileService.readAll(Path.of(PATH_STUDENT), Student.class)
                .stream()
                .sorted(Comparator.comparing(Student::getLastname)
                        .thenComparing(Student::getFirstname))
                .toList();
    }

    @Override
    public List<Student> findByFilter(StudentFilter filter) {
        String firstname = filter.getFirstname();
        String lastname = filter.getLastname();

        List<Student> students = getAll().stream()
                .filter(student -> firstname == null ||
                        student.getFirstname().toLowerCase().contains(firstname.toLowerCase()))
                .filter(student -> lastname == null ||
                        student.getLastname().toLowerCase().contains(lastname.toLowerCase()))
                .sorted(Comparator.comparing(Student::getLastname)
                        .thenComparing(Student::getFirstname))
                .toList();

        fileService.printObject(Path.of(PATH_SEARCH_HISTORY), students);

        return students;
    }
}

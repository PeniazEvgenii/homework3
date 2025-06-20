package ru.aston.hometask;

import ru.aston.hometask.model.Student;
import ru.aston.hometask.model.StudentFilter;
import ru.aston.hometask.model.StudentMark;
import ru.aston.hometask.exception.FileServiceException;
import ru.aston.hometask.exception.ValidationException;
import ru.aston.hometask.exception.error.ValidationError;
import ru.aston.hometask.service.api.IMarkService;
import ru.aston.hometask.service.api.IStudentService;
import ru.aston.hometask.service.api.ISubjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Menu {
    private static final String MESSAGE_INCORRECT_INPUT = "Вы ввели неверное значение";
    private static final int MIN_MARK = 1;
    private static final int MAX_MARK = 10;

    private final Scanner scanner;
    private final IStudentService studentService;
    private final ISubjectService subjectService;
    private final IMarkService markService;

    public Menu(Scanner scanner,
                IStudentService studentService,
                ISubjectService subjectService,
                IMarkService markService) {
        this.scanner = scanner;
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.markService = markService;
    }

    public void run() {
        while (true) {
            Section.MAIN.printMenu();
            int choice = getChoice(Section.MAIN.menus.size());
            switch (choice) {
                case 1 -> menuStudents();
                case 2 -> menuSubject();
                case 3 -> menuMark();
                case 4 -> {
                    System.out.println("Выход");
                    return;
                }
            }
        }
    }

    private void menuStudents() {
        while (true) {
            Section.STUDENTS.printMenu();
            int choice = getChoice(Section.STUDENTS.menus.size());
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> getAllStudents();
                case 3 -> findStudents();
                case 4 -> {
                    return;
                }
            }
        }
    }

    private void menuSubject() {
        while (true) {
            Section.SUBJECTS.printMenu();
            int choice = getChoice(Section.SUBJECTS.menus.size());
            switch (choice) {
                case 1 -> addSubject();
                case 2 -> getAllSubject();
                case 3 -> {
                    return;
                }
            }
        }
    }

    private void menuMark() {
        while (true) {
            Section.MARKS.printMenu();
            int choice = getChoice(Section.MARKS.menus.size());
            switch (choice) {
                case 1 -> addMark();
                case 2 -> getMark();
                case 3 -> {
                    return;
                }
            }
        }
    }

    private void addStudent() {
        Student student = getInputStudent();

        executeHandler(() -> studentService.save(student));
    }

    private void getAllStudents() {
        executeHandler(() -> studentService.getAll().forEach(System.out::println));
    }


    private void findStudents() {
        StudentFilter studentFilter = getInputStudentFilter();

        executeHandler(() ->
                studentService.findByFilter(studentFilter).forEach(System.out::println));
    }


    private void addSubject() {
        System.out.println("Введите название предмета");
        String subject = scanner.nextLine();

        executeHandler(() -> subjectService.save(subject));
    }

    private void getAllSubject() {
        executeHandler(() -> subjectService.getAll().forEach(System.out::println));
    }

    private void addMark() {
        Optional<List<String>> maybeSubjects = executeReturnValueHandler(subjectService::getAll);
        if (maybeSubjects.isEmpty() || maybeSubjects.get().isEmpty()) {
            System.out.println("нет сохраненных предметов");
            return;
        }
        List<String> subjects = maybeSubjects.get();

        String subject = getChoiceSubjectFrom(subjects);
        System.out.println("выбран " + subject);

        Optional<List<Student>> maybeStudents = executeReturnValueHandler(studentService::getAll);
        if (maybeStudents.isEmpty() || maybeStudents.get().isEmpty()) {
            System.out.println("Нет сохраненных студентов");
            return;
        }
        List<Student> students = maybeStudents.get();

        List<StudentMark> result = new ArrayList<>();
        System.out.printf("Введите оценки для студентов: Минимальный бал %d, максимальный %d\n", MIN_MARK, MAX_MARK);
        for (Student student : students) {
            System.out.println(student.getFirstname() + " " + student.getLastname());
            result.add(new StudentMark(student, getChoiceInRange(MIN_MARK, MAX_MARK)));
        }

        executeHandler(() -> markService.save(result, subject));
    }

    private void getMark() {
        Optional<List<String>> maybeSubjects = executeReturnValueHandler(subjectService::getAll);
        if (maybeSubjects.isEmpty() || maybeSubjects.get().isEmpty()) {
            System.out.println("нет сохраненных предметов");
            return;
        }
        List<String> subjects = maybeSubjects.get();

        String subject = getChoiceSubjectFrom(subjects);
        System.out.println("выбран " + subject);

        executeHandler(() -> markService.getAllBy(subject).forEach(System.out::println));
    }

    private void executeHandler(Runnable action) {
        try {
            action.run();
            System.out.println("Успешное выполнение операции");
        } catch (ValidationException validationException) {
            validationException.getErrors()
                    .stream()
                    .map(ValidationError::description)
                    .forEach(System.out::println);
        } catch (FileServiceException fileEx) {
            System.out.println(fileEx.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла внутреняя ошибка приложения: " + e);
        }
    }

    public <T> Optional<T> executeReturnValueHandler(Supplier<T> supplier) {
        try {
            return Optional.ofNullable(supplier.get());
        } catch (FileServiceException fileEx) {
            System.out.println(fileEx.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла внутреняя ошибка приложения: " + e);
        }
        return Optional.empty();
    }

    private String getChoiceSubjectFrom(List<String> subjects) {
        System.out.println("Выберите необходимый предмет");
        AtomicInteger i = new AtomicInteger();
        subjects.stream()
                .map(s -> i.incrementAndGet() + ". " + s)
                .forEach(System.out::println);
        int choice = getChoice(subjects.size());

        return subjects.get(choice - 1);
    }

    private int getChoice(int max) {
        return getChoiceInRange(1, max);
    }

    private int getChoiceInRange(int min, int max) {
        while (true) {
            System.out.printf("Введите номер от %d до %d%n", min, max);
            String inputChoice = scanner.nextLine();
            try {
                int value = Integer.parseInt(inputChoice);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException exception) {
                System.out.println(MESSAGE_INCORRECT_INPUT);
            }
        }
    }

    private Student getInputStudent() {
        System.out.println("Введите имя студента");
        String firstname = scanner.nextLine();
        System.out.println("Введите фамилию студента");
        String lastname = scanner.nextLine();
        System.out.println("Введите дату рождения в формате 31-12-2025");
        String birthDate = scanner.nextLine();

        return Student.builder()
                .id(UUID.randomUUID())
                .firstname(firstname)
                .lastname(lastname)
                .birthDate(birthDate)
                .build();
    }

    private StudentFilter getInputStudentFilter() {
        System.out.println("Введите имя студента");
        String firstname = scanner.nextLine().trim();
        System.out.println("Введите фамилию студента");
        String lastname = scanner.nextLine().trim();
        return new StudentFilter(firstname, lastname);
    }


    private enum Section {
        MAIN(List.of("Студенты", "Предметы", "Итоговые отметки", "Выход")),
        STUDENTS(List.of("добавить студента", "список студентов", "поиск студента", "назад")),
        SUBJECTS(List.of("добавить предмет", "список предметов", "назад")),
        MARKS(List.of("выставление отметок", "список отметок", "назад"));

        private final List<String> menus;

        private Section(List<String> menus) {
            this.menus = menus;
        }

        public void printMenu() {
            System.out.println("Выберите необходимый раздел");
            AtomicInteger i = new AtomicInteger(1);
            this.menus.forEach(s -> System.out.printf("%d. %s\n", i.getAndIncrement(), s));
        }
    }
}


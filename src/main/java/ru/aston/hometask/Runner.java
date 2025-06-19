package ru.aston.hometask;

import ru.aston.hometask.service.ServiceFactory;
import ru.aston.hometask.service.api.IMarkService;
import ru.aston.hometask.service.api.IStudentService;
import ru.aston.hometask.service.api.ISubjectService;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        ServiceFactory factory = ServiceFactory.getInstance();

        IStudentService studentService = factory.getStudentService();
        ISubjectService subjectService = factory.getSubjectService();
        IMarkService markService = factory.getMarkService();

        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(scanner, studentService, subjectService, markService);
        menu.run();
    }
}

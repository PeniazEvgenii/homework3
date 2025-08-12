package ru.aston.hometask;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.hometask.config.PathConfiguration;
import ru.aston.hometask.service.ServiceFactory;
import ru.aston.hometask.service.api.IMarkService;
import ru.aston.hometask.service.api.IStudentService;
import ru.aston.hometask.service.api.ISubjectService;
import ru.aston.hometask.ui.MenuUI;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        PathConfiguration pathConfiguration = new PathConfiguration();
        ObjectMapper objectMapper = new ObjectMapper();

        ServiceFactory factory = new ServiceFactory(pathConfiguration, objectMapper);

        IStudentService studentService = factory.getStudentService();
        ISubjectService subjectService = factory.getSubjectService();
        IMarkService markService = factory.getMarkService();
        Scanner scanner = new Scanner(System.in);

        MenuUI menu = new MenuUI(scanner, studentService, subjectService, markService);
        menu.run();
    }
}

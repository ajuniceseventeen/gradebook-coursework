package ru.coursework.gradebook.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coursework.gradebook.professor.Professor;
import ru.coursework.gradebook.student.Student;
import ru.coursework.gradebook.student.StudentService;
import ru.coursework.gradebook.user.CustomUserDetailsService;
import ru.coursework.gradebook.user.User;
import ru.coursework.gradebook.user.UserService;
import ru.coursework.gradebook.util.PasswordGeneratorService;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrationService {

    private final UserService userService;
    private final StudentService studentService;
    @Autowired
    private PasswordGeneratorService passwordGeneratorService;

    // Конструктор сервиса регистрации, внедрение зависимостей UserService и StudentService
    @Autowired
    public RegistrationService(UserService userService, StudentService studentService) {
        this.userService = userService;
        this.studentService = studentService;
    }


    // Метод проверки и регистрации пользователя
    public Map<String, String> checkAndRegister(User user) {

        // Создание отчета о результатах регистрации
        Map<String, String> report = new HashMap<>();

        // Проверка наличия пользователя с таким же именем
        if (userService.getUserByUsername(user.getUsername()) != null) {
            report.put("existNameError", "Пользователь с таким именем уже существует");
        }
        else {
            // Если пользователя с таким именем нет, регистрируем его
            userService.addStudent(user);
        }
        return report; // Возвращаем отчет о регистрации
    }

    public Map<String, String> checkAndRegister(Student student) {

        Map<String, String> report = new HashMap<>();

        if (userService.getUserByUsername(student.getUsername()) != null) {
            report.put("existNameError", "Студент с таким именем уже существует");
        }
        else {
            student.setPassword(passwordGeneratorService.generateTempPassword());
            // пока в консоль
            System.out.println(student.getPassword());
            studentService.addStudent(student);
        }
        return report;
    }

    public Map<String, String> checkAndRegister(Professor professor) {

        // Создание отчета о результатах регистрации
        Map<String, String> report = new HashMap<>();

        // Проверка наличия пользователя с таким же именем
        if (userService.getUserByUsername(professor.getUsername()) != null) {
            report.put("existNameError", "Преподаватель с таким именем уже существует");
        }
        else {
            // Если пользователя с таким именем нет, регистрируем его
            professor.setPassword(passwordGeneratorService.generateTempPassword());
            // пока в консоль
            System.out.println(professor.getPassword());

            userService.addProfessor(professor);
        }
        return report; // Возвращаем отчет о регистрации
    }
}

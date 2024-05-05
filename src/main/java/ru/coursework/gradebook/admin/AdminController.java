package ru.coursework.gradebook.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.coursework.gradebook.auth.RegistrationService;
import ru.coursework.gradebook.professor.Professor;
import ru.coursework.gradebook.professor.ProfessorService;
import ru.coursework.gradebook.student.Student;
import ru.coursework.gradebook.student.StudentService;
import ru.coursework.gradebook.studygroup.StudyGroup;
import ru.coursework.gradebook.studygroup.StudyGroupService;
import ru.coursework.gradebook.user.User;
import ru.coursework.gradebook.user.UserService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Сервис для работы с информацией о студентах
    private final StudentService studentService;

    // Сервис для работы с информацией о группах
    private final StudyGroupService studyGroupService;

    // Сервис для регистрации пользователей
    private final RegistrationService registrationService;

    // Сервис для работы с пользователями
    private final UserService userService;

    // Сервис для работы с информацией о преподавателях
    private final ProfessorService professorService;

    // Конструктор класса с инъекцией зависимостей
    public AdminController(StudyGroupService studyGroupService,
                           RegistrationService registrationService,
                           UserService userService,
                           StudentService studentService,
                           ProfessorService professorService) {
        this.studyGroupService = studyGroupService;
        this.registrationService = registrationService;
        this.userService = userService;
        this.studentService = studentService;
        this.professorService = professorService;
    }

    // Обработчик запроса на страницу администратора
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("admin/admin");
        modelAndView.addObject("users", userService.findAll());
        return modelAndView;
    }

    // Метод для отображения формы создания студента
    @GetMapping("/create-student")
    @PreAuthorize("hasRole('ADMIN')")
    public String showStudentForm(Model model) {
        model.addAttribute("student", new Student());
        List<StudyGroup> studyGroups = studyGroupService.getAllStudyGroups();
        model.addAttribute("studyGroups", studyGroups);
        return "add-student-form";
    }

    // Метод для сохранения созданного администратором студента
    @PostMapping("/create-student")
    @PreAuthorize("hasRole('ADMIN')")
    public String createStudent(Student student, Model model,
                                BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            Map<String, String> report = registrationService.checkAndRegister(student);
            if (report.isEmpty()) {
                return "redirect:/admin/student-info/" + student.getUser_id();
            }
            for (Map.Entry<String, String> entry : report.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }
        }
        return "redirect:/admin/student-info/" + student.getUser_id();
    }

    // Метод для отображения информации о студенте
    @GetMapping("/student-info/{studentId}")
    public String showStudentInfo(@PathVariable("studentId") Long studentId, Model model) {
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student", student);
        return "student-info";
    }

    // Метод для отображения формы создания преподавателя
    @GetMapping("/create-professor")
    @PreAuthorize("hasRole('ADMIN')")
    public String showProfessorForm(Model model) {
        model.addAttribute("professor", new Professor());
        return "add-professor-form";
    }

    // Метод для сохранения созданного администратором преподавателя
    @PostMapping("/create-professor")
    @PreAuthorize("hasRole('ADMIN')")
    public String createProfessor(Professor professor, Model model,
                                  BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            Map<String, String> report = registrationService.checkAndRegister(professor);
            if (report.isEmpty()) {
                return "redirect:/admin/professor-info/" + professor.getUser_id();
            }
            for (Map.Entry<String, String> entry : report.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }
        }
        return "redirect:/admin/professor-info/" + professor.getUser_id();
    }

    // Метод для отображения информации о преподавателе
    @GetMapping("/professor-info/{professorId}")
    public String showProfessorInfo(@PathVariable("professorId") Long professorId, Model model) {
        Professor professor = professorService.getProfessorById(professorId);
        model.addAttribute("professor", professor);
        return "professor-info";
    }
}

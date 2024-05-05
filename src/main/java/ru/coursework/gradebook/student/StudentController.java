package ru.coursework.gradebook.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.coursework.gradebook.auth.RegistrationService;
import ru.coursework.gradebook.record.attendance.Attendance;
import ru.coursework.gradebook.record.attendance.AttendanceService;
import ru.coursework.gradebook.record.lesson.Lesson;
import ru.coursework.gradebook.record.lesson.LessonService;
import ru.coursework.gradebook.record.mark.Mark;
import ru.coursework.gradebook.record.mark.MarkService;
import ru.coursework.gradebook.studygroup.StudyGroup;
import ru.coursework.gradebook.studygroup.StudyGroupService;
import ru.coursework.gradebook.subject.Subject;
import ru.coursework.gradebook.subject.SubjectDetails;
import ru.coursework.gradebook.subject.SubjectDetailsService;
import ru.coursework.gradebook.subject.SubjectService;
import ru.coursework.gradebook.user.CustomUserDetails;
import ru.coursework.gradebook.user.CustomUserDetailsService;
import ru.coursework.gradebook.user.User;
import ru.coursework.gradebook.user.UserService;
import ru.coursework.gradebook.util.PasswordGeneratorService;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;
    private UserService userService;
    private LessonService lessonService;
    private MarkService markService;
    private AttendanceService attendanceService;
    private SubjectDetailsService subjectDetailsService;

    @Autowired
    public StudentController(StudentService studentService,
                             UserService userService,
                             LessonService lessonService,
                             MarkService markService,
                             AttendanceService attendanceService,
                             SubjectDetailsService subjectDetailsService) {
        this.studentService = studentService;
        this.userService = userService;
        this.lessonService = lessonService;
        this.markService = markService;
        this.attendanceService = attendanceService;
        this.subjectDetailsService = subjectDetailsService;
    }

    @GetMapping("/info")
    public String getStudentInfo(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        // Получаем имя текущего пользователя
        String username = userDetails.getUsername();
        // Получаем информацию о пользователе из сервиса пользователей
        Student student = studentService.getStudentByUsername(username);

        // Получить список предметов для данной учебной группы
        List<SubjectDetails> subjects = subjectDetailsService.getSubjectsByStudyGroupId(student.getStudyGroup().getStudy_group_id());

        // Поместить данные в модель
        model.addAttribute("subjects", subjects);


        // Передаем информацию о пользователе в модель
        model.addAttribute("student", student);

        // Возвращаем имя представления для отображения профиля пользователя
        return "student-profile";
    }

    @GetMapping("/subject/details")
    public String viewMyGrades(Model model,
                               Principal principal) {
        String username = principal.getName(); // Получаем имя текущего пользователя
        Student student_t = studentService.getStudentByUsername(username); // Получаем студента по имени пользователя
        if (student_t != null) {

            // Получаем список всех уроков и оценок студента
            List<Lesson> lessons = lessonService.getAllLessonsByGroupId(student_t.getStudyGroup().getStudy_group_id());
            List<Mark> marks = markService.getAllMarks();
            List<Attendance> attendanceList = attendanceService.getAllAttendances();

            // Список отсортированных студентов
            List<Student> sortedStudents = studentService.getAllStudentsSortedByLastName();

            // Фильтр для определённой группы
            sortedStudents = sortedStudents.stream()
                    .filter(student -> student.getStudyGroup().getStudy_group_id().equals(student_t.getStudyGroup().getStudy_group_id()))
                    .collect(Collectors.toList());

            // Передаем данные в модель
            model.addAttribute("student_t", student_t);
            model.addAttribute("lessons", lessons);
            model.addAttribute("marks", marks);
            model.addAttribute("sortedStudents", sortedStudents);
            model.addAttribute("attendanceList", attendanceList);

            return "student-grades"; // Возвращаем имя представления
        } else {
            return "error-page"; // Если студент не найден, возвращаем страницу с ошибкой
        }
    }

    @GetMapping("/id/{user_id}")
    public String getStudentInfo(@PathVariable("user_id") Long user_id, Model model) {
        Student student = studentService.getStudentById(user_id);
        model.addAttribute("student", student);
        return "student-profile";
    }



//    @GetMapping("/students")
//    public String getAll(Model model) {
//
//        return "students";
//    }


}

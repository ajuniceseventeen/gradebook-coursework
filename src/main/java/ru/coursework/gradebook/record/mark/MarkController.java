package ru.coursework.gradebook.record.mark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.coursework.gradebook.record.attendance.Attendance;
import ru.coursework.gradebook.record.attendance.AttendanceService;
import ru.coursework.gradebook.record.attendance.AttendanceStatus;
import ru.coursework.gradebook.record.lesson.Lesson;
import ru.coursework.gradebook.record.lesson.LessonService;
import ru.coursework.gradebook.record.mark.MarkService;
import ru.coursework.gradebook.student.Student;
import ru.coursework.gradebook.student.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MarkController {

    private final MarkService markService;
    private final StudentService studentService;
    private final LessonService lessonService;
    private final AttendanceService attendanceService;

    @Autowired
    public MarkController(MarkService markService,
                          StudentService studentService,
                          LessonService lessonService,
                          AttendanceService attendanceService) {
        this.markService = markService;
        this.studentService = studentService;
        this.lessonService = lessonService;
        this.attendanceService = attendanceService;
    }

    //    @GetMapping("/")
//    public String showGroupTable(Model model) {
//        List<Student> sortedStudents = markService.getAllStudentsSortedByLastName();
//        model.addAttribute("sortedStudents", sortedStudents);
//        return "groupTable";
//    }

    @PostMapping("/saveMark")
    public String saveMark(@RequestParam Long studentId,
                           @RequestParam Long lessonId,
                           @RequestParam int mark,
                           @RequestParam(required = false) AttendanceStatus attendanceStatus) {
        Mark existingMark = markService.getMarkByStudentAndLesson(studentId, lessonId);
        // Получить студента и урок по их идентификаторам
        Student student = studentService.getStudentById(studentId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        // Если оценка не найдена, создаем новую
        if (existingMark == null) {
            existingMark = new Mark();
            existingMark.setStudent(student);
            existingMark.setLesson(lesson);
        }
        // Устанавливаем новое значение оценки
        existingMark.setMark(mark);
        // Сохраняем оценку в базе данных
        markService.saveMark(existingMark);

        if (attendanceStatus != null) {
            // Добавляем или обновляем статус посещения
            Attendance attendance = attendanceService.getAttendanceByStudentAndLesson(studentId, lessonId);
            if (attendance == null) {
                attendance = new Attendance();
                attendance.setStudent(student);
                attendance.setLesson(lesson);
            }
            attendance.setStatus(attendanceStatus);
            attendanceService.saveAttendance(attendance);
        }

        // Перенаправить пользователя на страницу просмотра записей
        return "redirect:/records/view/" + lesson.getStudygroup().getStudy_group_id() +
                "?subjectId=" + lesson.getSubject().getSubject_id() +
                "&professorId=" + lesson.getProfessor().getUser_id();
    }

    @GetMapping("/editMarks/{studentId}")
    public String editMark(@PathVariable Long studentId,
                           @RequestParam("subjectId") Long subjectId,
                           @RequestParam("professorId") Long professorId,
//                           @RequestParam(value = "lessons", required = false) List<Lesson> lessons,
                           Model model) {
//        // Получить список уроков для данного предмета и профессора
        List<Lesson> lessons = lessonService.getLessonsBySubjectIdAndProfessorId(subjectId, professorId);

        List<Attendance> attendances = new ArrayList<>();

        List<Mark> currentMarks = new ArrayList<>();
        for (Lesson lesson : lessons) {
            Mark currentMark = markService.getMarkByStudentAndLesson(studentId, lesson.getLessonId());
            currentMarks.add(currentMark);

            Attendance currentAttendance = attendanceService.getAttendanceByStudentAndLesson(studentId, lesson.getLessonId());

//            System.out.println(currentAttendance.getStatus().getDisplayName());

            attendances.add(currentAttendance != null ? currentAttendance : new Attendance());
        }

        String studentFirstName = studentService.getStudentById(studentId).getFirstName();
        String studentLastName = studentService.getStudentById(studentId).getLastName();

        model.addAttribute("studentFirstName", studentFirstName);
        model.addAttribute("studentLastName", studentLastName);

        // Передать список уроков и другие необходимые данные в модель
        model.addAttribute("currentMarks", currentMarks);
        model.addAttribute("attendances", attendances);


        model.addAttribute("studentId", studentId);
        model.addAttribute("lessons", lessons);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("professorId", professorId);

        return "edit-marks-form"; // Вернуть имя представления
    }

    @PostMapping("/deleteMark")
    public String deleteMark(@RequestParam Long markId) {
        markService.deleteMark(markId);
        return "redirect:/";
    }
}

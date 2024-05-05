package ru.coursework.gradebook.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.coursework.gradebook.record.attendance.Attendance;
import ru.coursework.gradebook.record.attendance.AttendanceService;
import ru.coursework.gradebook.record.lesson.Lesson;
import ru.coursework.gradebook.record.lesson.LessonService;
import ru.coursework.gradebook.record.mark.Mark;
import ru.coursework.gradebook.record.mark.MarkService;
import ru.coursework.gradebook.student.Student;
import ru.coursework.gradebook.student.StudentService;
import ru.coursework.gradebook.studygroup.StudyGroupService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/records")
public class RecordController {
    private final LessonService lessonService;
    private final MarkService markService;
    private final StudentService studentService;
    private final StudyGroupService studyGroupService;
    private final AttendanceService attendanceService;

    @Autowired
    public RecordController(LessonService lessonService,
                            MarkService markService,
                            StudentService studentService,
                            StudyGroupService studyGroupService, AttendanceService attendanceService) {
        this.lessonService = lessonService;
        this.markService = markService;
        this.studentService = studentService;
        this.studyGroupService = studyGroupService;
        this.attendanceService = attendanceService;
    }

    @GetMapping("/view/{id}")
    public String viewRecords(@PathVariable("id") Long id,
                              @RequestParam(name = "subjectId", required = false) Long subjectId,
                              @RequestParam(name = "professorId", required = false) Long professorId,
                              Model model) {
        // Загрузить данные о занятиях, оценках, посещаемости
        List<Lesson> lessons = lessonService.getAllLessonsByGroupIdAndSubjectIdAndProfessorId(id, subjectId, professorId);
        List<Mark> marks = markService.getAllMarks();
        List<Attendance> attendanceList = attendanceService.getAllAttendances();

        // Список отсортированных студентов
        List<Student> sortedStudents = studentService.getAllStudentsSortedByLastName();

        // Фильтр для определённой группы
        sortedStudents = sortedStudents.stream()
                .filter(student -> student.getStudyGroup().getStudy_group_id().equals(id))
                .collect(Collectors.toList());

        String groupName = studyGroupService.getGroupNameById(id);
        model.addAttribute("groupName", groupName);
        // Поместить данные в модель
        model.addAttribute("sortedStudents", sortedStudents);
        model.addAttribute("lessons", lessons);
        model.addAttribute("marks", marks);
        model.addAttribute("attendanceList", attendanceList);

        // Поместить id в модель
        model.addAttribute("groupId", id);
        if (subjectId != null && professorId != null) {
            model.addAttribute("subjectId", subjectId);
            model.addAttribute("professorId", professorId);
        }

        return "records";
    }



}

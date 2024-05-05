package ru.coursework.gradebook.record.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coursework.gradebook.record.lesson.LessonService;
import ru.coursework.gradebook.student.StudentService;

import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentService studentService;
    private final LessonService lessonService;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, StudentService studentService, LessonService lessonService) {
        this.attendanceRepository = attendanceRepository;
        this.studentService = studentService;
        this.lessonService = lessonService;
    }

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceByStudentAndLesson(Long studentId, Long lessonId) {
        return attendanceRepository.findByStudentAndLesson(
                studentService.getStudentById(studentId),
                lessonService.getLessonById(lessonId)
        );
    }

    public void saveAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }
}

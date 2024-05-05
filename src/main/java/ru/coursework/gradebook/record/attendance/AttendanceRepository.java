package ru.coursework.gradebook.record.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.coursework.gradebook.record.lesson.Lesson;
import ru.coursework.gradebook.student.Student;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findByStudentAndLesson(Student student, Lesson lesson);
}

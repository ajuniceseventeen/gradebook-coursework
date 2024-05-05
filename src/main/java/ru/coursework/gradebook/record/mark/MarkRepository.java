package ru.coursework.gradebook.record.mark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.coursework.gradebook.record.lesson.Lesson;
import ru.coursework.gradebook.student.Student;

import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {


    Mark findByStudentAndLesson(Student student, Lesson lesson);

    Mark findByLesson(Lesson lesson);

    List<Mark> findByStudent(Student student);

//    getMarkByStudentAndLesson



}

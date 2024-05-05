package ru.coursework.gradebook.record.mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coursework.gradebook.record.lesson.Lesson;
import ru.coursework.gradebook.record.lesson.LessonRepository;
import ru.coursework.gradebook.record.lesson.LessonService;
import ru.coursework.gradebook.student.Student;
import ru.coursework.gradebook.student.StudentRepository;
import ru.coursework.gradebook.student.StudentService;

import java.util.List;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private LessonService lessonService;


    public void addMark(Long studentId,Long lessonId, int newMark) {
        Student student = new Student();
        student.setUser_id(studentId);

        Lesson lesson = new Lesson();
        // Предположим, что lessonId - это идентификатор урока, для которого ставится оценка.
        lesson.setLessonId(lessonId);

        Mark mark = new Mark();
        mark.setStudent(student);
        mark.setLesson(lesson);
        mark.setMark(newMark);

        markRepository.save(mark);
    }

    public void updateMark(Long markId, int newMark) {
        Mark mark = markRepository.findById(markId).orElse(null);
        if (mark != null) {
            mark.setMark(newMark);
            markRepository.save(mark);
        }
    }

    public void deleteMark(Long markId) {
        markRepository.deleteById(markId);
    }

    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }

    public void saveMark(Mark mark) {
        markRepository.save(mark);
    }



    public Mark getMarkByStudentAndLesson(Long studentId, Long lessonId) {
        return markRepository.findByStudentAndLesson(
            studentService.getStudentById(studentId),
            lessonRepository.findByLessonId(lessonId)
        );
    }

    public Mark getCurrentMarkForLesson(Long lessonId) {
        return markRepository.findByLesson(
            lessonRepository.findByLessonId(lessonId)
        );
    }

    public List<Mark> getAllMarksByStudentId(Long userId) {
        return markRepository.findByStudent(studentService.getStudentById(userId));
    }
}

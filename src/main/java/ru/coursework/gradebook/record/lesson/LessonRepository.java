package ru.coursework.gradebook.record.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.coursework.gradebook.professor.Professor;
import ru.coursework.gradebook.studygroup.StudyGroup;
import ru.coursework.gradebook.subject.Subject;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByStudygroupAndSubjectAndProfessor(StudyGroup studyGroup,
                                                           Subject subject,
                                                           Professor professor);

    List<Lesson> findAllBySubjectAndProfessor(Subject subject, Professor professor);

    Lesson findByLessonId(Long lessonId);

    List<Lesson> findAllByStudygroup(StudyGroup studyGroup);

}

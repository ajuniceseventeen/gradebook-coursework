package ru.coursework.gradebook.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.coursework.gradebook.professor.Professor;
import ru.coursework.gradebook.studygroup.StudyGroup;

import java.util.List;

@Repository
public interface SubjectDetailsRepository extends JpaRepository<SubjectDetails, Long> {
    List<SubjectDetails> findAllByProfessor(Professor professor);

    List<SubjectDetails> findAllByStudyGroup(StudyGroup studyGroup);
}

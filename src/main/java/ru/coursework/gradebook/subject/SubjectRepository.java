package ru.coursework.gradebook.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.coursework.gradebook.professor.Professor;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}

package ru.coursework.gradebook.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.coursework.gradebook.studygroup.StudyGroup;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByUsername(String username);

//    @Query(value = "SELECT s.first_name FROM students s WHERE s.study_group_id = ?1", nativeQuery = true)
    List<Student> findByStudyGroup(StudyGroup studyGroup);
    List<Student> findAllByOrderByLastNameAsc();
}

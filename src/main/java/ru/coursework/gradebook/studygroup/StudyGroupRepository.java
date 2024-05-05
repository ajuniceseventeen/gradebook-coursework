package ru.coursework.gradebook.studygroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    @Query(value= "select * from study_groups where study_group_id = ?1", nativeQuery=true)
    StudyGroup findByStudy_group_id(Long id);
}

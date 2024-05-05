package ru.coursework.gradebook.studygroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    public StudyGroupService(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    // получение всех учебных групп
    public List<StudyGroup> getAllStudyGroups() {
        return this.studyGroupRepository.findAll();
    }

    // Добавление новой учебной группы
    public void addStudyGroup(StudyGroup studyGroup) {
       this.studyGroupRepository.save(studyGroup);
    }

    public StudyGroup getStudyGroupById(long id) {
        return studyGroupRepository.findByStudy_group_id(id);
    }

    public String getGroupNameById(Long id) {
        return getStudyGroupById(id).getName();
    }
}

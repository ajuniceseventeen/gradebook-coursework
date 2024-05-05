package ru.coursework.gradebook.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coursework.gradebook.professor.Professor;
import ru.coursework.gradebook.semester.Semester;
import ru.coursework.gradebook.studygroup.StudyGroup;
import ru.coursework.gradebook.studygroup.StudyGroupService;

import java.util.List;

@Service
@Transactional
public class SubjectDetailsService {
    private final SubjectDetailsRepository subjectDetailsRepository;
    private final StudyGroupService studyGroupService;

    @Autowired
    public SubjectDetailsService(SubjectDetailsRepository subjectDetailsRepository, StudyGroupService studyGroupService) {
        this.subjectDetailsRepository = subjectDetailsRepository;
        this.studyGroupService = studyGroupService;
    }

    public List<SubjectDetails> getAllSubjectDetails() {
        return subjectDetailsRepository.findAll();
    }

    public SubjectDetails getSubjectDetailsById(Long id) {
        return subjectDetailsRepository.findById(id).orElse(null);
    }

    public void saveSubjectDetails(Long subjectId, Long professorId, Long studyGroupId, Long semesterId) {
        // Создаем новый объект SubjectDetails
        SubjectDetails subjectDetails = new SubjectDetails();

        // Создаем объекты и устанавливаем значения
        Subject subject = new Subject();
        subject.setSubject_id(subjectId);
        subjectDetails.setSubject(subject);

        Professor professor = new Professor();
        professor.setUser_id(professorId);
        subjectDetails.setProfessor(professor);

        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setStudy_group_id(studyGroupId);
        subjectDetails.setStudyGroup(studyGroup);

        Semester semester = new Semester();
        semester.setId(semesterId);
        subjectDetails.setSemester(semester);

        // Сохраняем объект SubjectDetails в базе данных
        subjectDetailsRepository.save(subjectDetails);
    }

    public void deleteSubjectDetails(Long id) {
        subjectDetailsRepository.deleteById(id);
    }

    public List<SubjectDetails> findAllByProfessor(Professor professor) {
        return subjectDetailsRepository.findAllByProfessor(professor);
    }

    public List<SubjectDetails> getSubjectsByStudyGroupId(Long studyGroupId) {
        return subjectDetailsRepository.findAllByStudyGroup(studyGroupService.getStudyGroupById(studyGroupId));
    }
}

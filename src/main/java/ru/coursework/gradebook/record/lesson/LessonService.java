package ru.coursework.gradebook.record.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coursework.gradebook.professor.ProfessorService;
import ru.coursework.gradebook.studygroup.StudyGroupRepository;
import ru.coursework.gradebook.studygroup.StudyGroupService;
import ru.coursework.gradebook.subject.SubjectService;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final StudyGroupService studyGroupService;
    private final ProfessorService professorService;
    private final SubjectService subjectService;

    @Autowired
    public LessonService(LessonRepository lessonRepository,
                         StudyGroupService studyGroupService,
                         ProfessorService professorService,
                         SubjectService subjectService) {
        this.lessonRepository = lessonRepository;
        this.studyGroupService = studyGroupService;
        this.professorService = professorService;
        this.subjectService = subjectService;
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public void deleteLessonById(Long id) {
        lessonRepository.deleteById(id);
    }

    public List<Lesson> getAllLessonsByGroupIdAndSubjectIdAndProfessorId(Long id,
                                                                         Long subjectId,
                                                                         Long professorId) {
        return lessonRepository.findAllByStudygroupAndSubjectAndProfessor(
            studyGroupService.getStudyGroupById(id),
            subjectService.getSubjectById(subjectId),
            professorService.getProfessorById(professorId)
        );
    }
    public List<Lesson> getAllLessonsByGroupId(Long id) {
        return lessonRepository.findAllByStudygroup(studyGroupService.getStudyGroupById(id));
    }


    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId).orElse(null);
    }

    public List<Lesson> getLessonsBySubjectIdAndProfessorId(Long subjectId, Long professorId) {
        return lessonRepository.findAllBySubjectAndProfessor(
                subjectService.getSubjectById(subjectId),
                professorService.getProfessorById(professorId)
        );
    }
}

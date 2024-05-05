package ru.coursework.gradebook.record.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.coursework.gradebook.professor.Professor;
import ru.coursework.gradebook.professor.ProfessorService;
import ru.coursework.gradebook.studygroup.StudyGroup;
import ru.coursework.gradebook.studygroup.StudyGroupService;
import ru.coursework.gradebook.subject.Subject;
import ru.coursework.gradebook.subject.SubjectService;

import java.time.LocalDate;

@Controller
public class LessonController {

    private final LessonService lessonService;

    private final ProfessorService professorService;

    private final SubjectService subjectService;
    private final StudyGroupService studyGroupService;

    @Autowired
    public LessonController(LessonService lessonService, ProfessorService professorService, SubjectService subjectService, StudyGroupService studyGroupService) {
        this.lessonService = lessonService;
        this.professorService = professorService;
        this.subjectService = subjectService;
        this.studyGroupService = studyGroupService;
    }

    @GetMapping("/createLesson")
    public String showCreateLessonForm(@RequestParam(name = "groupId") Long groupId,
                                       @RequestParam(name = "professorId") Long professorId,
                                       @RequestParam(name = "subjectId") Long subjectId,
                                       Model model) {
        model.addAttribute("lesson", new Lesson()); // Передаем пустой объект Lesson для заполнения формы
        model.addAttribute("groupId", groupId);
        model.addAttribute("professorId", professorId); // Добавляем ID профессора в модель
        model.addAttribute("subjectId", subjectId); // Добавляем ID предмета в модель
        return "create_lesson"; // Возвращаем имя HTML-шаблона для отображения формы создания урока
    }

    @PostMapping("/createLesson")
    public String createLesson(@RequestParam("groupId") Long groupId,
                               @RequestParam Long professorId,
                               @RequestParam Long subjectId,
                               @RequestParam String topic,
                               @RequestParam LocalDate lessonDate) {

        System.out.println("group id" + " = " + groupId);
        System.out.println("professorId" + " = " + professorId);
        System.out.println("subjectId" + " = " + subjectId);

        StudyGroup studyGroup = studyGroupService.getStudyGroupById(groupId);
        Professor professor = professorService.getProfessorById(professorId);
        Subject subject = subjectService.getSubjectById(subjectId);

        Lesson lesson = new Lesson();
        lesson.setTopic(topic);
        lesson.setLessonDate(lessonDate);
        lesson.setStudygroup(studyGroup);
        lesson.setProfessor(professor);
        lesson.setSubject(subject);

        // Сохраняем урок в базе данных
        lessonService.saveLesson(lesson);

        // Перенаправляем пользователя на страницу просмотра уроков
        return "redirect:/records/view/" + groupId + "?professorId=" + professorId + "&subjectId=" + subjectId;

    }


    /*
    * <form th:action="@{/createLesson}" method="post">
    <input type="hidden" name="groupId" th:value="${groupId}"/> <!-- Скрытое поле с идентификатором группы -->
    <input type="text" name="topic" placeholder="Lesson topic" required/>
    <input type="date" name="lessonDate" required/>
    <button type="submit">Create Lesson</button>
</form>
*/
}

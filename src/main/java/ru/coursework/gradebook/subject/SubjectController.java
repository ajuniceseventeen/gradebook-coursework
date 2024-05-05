package ru.coursework.gradebook.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.coursework.gradebook.professor.ProfessorService;
import ru.coursework.gradebook.semester.SemesterService;
import ru.coursework.gradebook.studygroup.StudyGroupService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    // Сервисы для работы с предметами, преподавателями, группами и деталями предметов
    private final SubjectService subjectService;
    private final ProfessorService professorService;
    private final StudyGroupService studyGroupService;
    private final SubjectDetailsService subjectDetailsService;

    // Сервис для работы с семестрами
    private final SemesterService semesterService;

    // Конструктор контроллера, внедрение зависимостей сервисов через конструктор
    public SubjectController(SubjectService subjectService,
                             ProfessorService professorService,
                             StudyGroupService studyGroupService,
                             SubjectDetailsService subjectDetailsService,
                             SemesterService semesterService) {
        this.subjectService = subjectService;
        this.professorService = professorService;
        this.studyGroupService = studyGroupService;
        this.subjectDetailsService = subjectDetailsService;
        this.semesterService = semesterService;
    }

    // Обработчик GET запроса для отображения формы создания предмета
    @GetMapping("/create")
    public String showCreateSubjectForm(Model model) {
        // Передача пустого объекта Subject и списков преподавателей, групп и семестров в модель
        model.addAttribute("subject", new Subject());
        model.addAttribute("professors", professorService.getAllProfessors());
        model.addAttribute("studyGroups", studyGroupService.getAllStudyGroups());
        model.addAttribute("semesters", semesterService.getAllSemesters());
        return "create-subject";
    }

    // Обработчик POST запроса для создания предмета
    @PostMapping("/create")
    public String createSubject(@ModelAttribute("subject") Subject subject,
                                @RequestParam("professor.id") Long professorId,
                                @RequestParam("studyGroup.id") Long studyGroupId,
                                @RequestParam("semesterId") Long semesterId) {
        // Создание предмета
        subjectService.createSubject(subject);
        // После создания предмета вызываем методы для создания и сохранения деталей предмета
        // Можно передать необходимые параметры, например, ID предмета, преподавателя и группы
        subjectDetailsService.saveSubjectDetails(subject.getSubject_id(), professorId, studyGroupId, semesterId);
        // Перенаправление на страницу создания предмета снова
        return "redirect:/subjects/create";
    }

    // Обработчик GET запроса для отображения формы изменения предмета
    @GetMapping("/edit/{id}")
    public String showEditSubjectForm(@PathVariable("id") Long id, Model model) {
        // Получаем предмет по его ID из сервиса
        Subject subject = subjectService.getSubjectById(id);
        if (subject == null) {
            // Если предмет не найден, перенаправляем на страницу с ошибкой
            return "error";
        }
        // Передаем предмет и списки преподавателей, групп и семестров в модель
        model.addAttribute("subject", subject);
        model.addAttribute("professors", professorService.getAllProfessors());
        model.addAttribute("studyGroups", studyGroupService.getAllStudyGroups());
        model.addAttribute("semesters", semesterService.getAllSemesters());
        return "edit-subject";
    }

    // Обработчик POST запроса для изменения предмета
    @PostMapping("/edit/{id}")
    public String editSubject(@PathVariable("id") Long id,
                              @ModelAttribute("subject") Subject subject,
                              @RequestParam("professor.id") Long professorId,
                              @RequestParam("studyGroup.id") Long studyGroupId,
                              @RequestParam("semesterId") Long semesterId) {
        // Устанавливаем ID предмета
        subject.setSubject_id(id);
        // Обновляем предмет
        subjectService.updateSubject(subject);
        // Обновляем детали предмета
        subjectDetailsService.saveSubjectDetails(id, professorId, studyGroupId, semesterId);
        // Перенаправляем на страницу со списком предметов
        return "redirect:/subjects";
    }

    // Обработчик POST запроса для удаления предмета
    @PostMapping("/delete/{id}")
    public String deleteSubject(@PathVariable("id") Long id) {
        // Удаляем предмет и его детали
        subjectService.deleteSubject(id);
        subjectDetailsService.deleteSubjectDetails(id);
        // Перенаправляем на страницу со списком предметов
        return "redirect:/subjects";
    }
}

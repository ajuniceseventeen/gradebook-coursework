package ru.coursework.gradebook.studygroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/study-groups")
public class StudyGroupController {
    private final StudyGroupService studyGroupService;

    @Autowired
    public StudyGroupController(StudyGroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }

    // получение всех учебных групп
    @GetMapping
    public String getAllStudyGroups(Model model) {
        model.addAttribute("studyGroups", studyGroupService.getAllStudyGroups());
        return "study-groups";
    }


    // добавление группы с помощью html формы
    @GetMapping("/add")
    public String showAddStudyGroupForm(Model model) {
        model.addAttribute("studyGroup", new StudyGroup());
        return "add-study-group";
    }

    @PostMapping("/add")
    public String addStudyGroup(@ModelAttribute StudyGroup studyGroup) {
        studyGroupService.addStudyGroup(studyGroup);
        return "redirect:/study-groups";
    }
}

package ru.coursework.gradebook.semester;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/semesters")
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping("/create")
    public String showCreateSemesterForm(Model model) {
        model.addAttribute("semester", new Semester());
        return "create-semester";
    }

    @PostMapping("/create")
    public String createSemester(@ModelAttribute("semester") Semester semester) {
        semesterService.createSemester(semester);
        return "redirect:/semesters/create";
    }
}

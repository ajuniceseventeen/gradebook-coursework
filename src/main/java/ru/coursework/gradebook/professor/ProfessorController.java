package ru.coursework.gradebook.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.coursework.gradebook.subject.Subject;
import ru.coursework.gradebook.subject.SubjectDetails;
import ru.coursework.gradebook.subject.SubjectService;
import ru.coursework.gradebook.subject.SubjectDetailsService;

import java.util.List;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectDetailsService subjectDetailsService;

    @GetMapping("/home")
    public String getProfessorSubjects(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        Professor professor = professorService.getProfessorByUsername(username);

        if (professor != null) {
            List<SubjectDetails> subjectDetailsList = subjectDetailsService.findAllByProfessor(professor);
            model.addAttribute("professor", professor);
            model.addAttribute("subjectDetailsList", subjectDetailsList);
            return "professor-subjects";
        } else {
            // Если профессор не найден, возвращаем страницу с ошибкой или другое представление
            return "error-page";
        }
    }
}

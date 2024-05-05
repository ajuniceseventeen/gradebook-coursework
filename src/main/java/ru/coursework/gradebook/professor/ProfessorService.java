package ru.coursework.gradebook.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    // Метод для получения преподавателя по его идентификатору
    public Professor getProfessorById(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    // Метод для получения списка всех преподавателей
    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    // Метод для получения преподавателя по его имени пользователя
    public Professor getProfessorByUsername(String username) {
        return professorRepository.findProfessorByUsername(username);
    }
}

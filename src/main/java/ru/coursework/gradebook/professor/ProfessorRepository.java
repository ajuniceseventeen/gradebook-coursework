package ru.coursework.gradebook.professor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    public Professor findProfessorByUsername(String username);
}

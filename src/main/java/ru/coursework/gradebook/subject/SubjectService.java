package ru.coursework.gradebook.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.coursework.gradebook.professor.Professor;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectDetailsService subjectDetailsService;

    public void createSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<Subject> getSubjectsByProfessor(Professor professor) {
        List<SubjectDetails> subjectDetailsList = subjectDetailsService.findAllByProfessor(professor);
        List<Subject> subjects = new ArrayList<>();
        for (SubjectDetails subjectDetails : subjectDetailsList) {
            subjects.add(subjectDetails.getSubject());
        }
        return subjects;
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    public void updateSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}

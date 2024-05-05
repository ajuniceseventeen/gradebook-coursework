package ru.coursework.gradebook.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.coursework.gradebook.studygroup.StudyGroupService;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private final StudyGroupService studyGroupService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          StudyGroupService studyGroupService,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.studyGroupService = studyGroupService;
        this.passwordEncoder = passwordEncoder;
    }

    // Метод для добавления студента
    public void addStudent(Student student) {
        // Хеширование пароля перед сохранением в базу данных
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        // Установка роли студента
        student.setRoles("ROLE_STUDENT");
        // Сохранение студента в репозитории
        studentRepository.save(student);
    }

    // Метод для получения всех студентов
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Метод для получения идентификатора студента по его имени пользователя
    public Long getStudentIdByUsername(String username) {
        return studentRepository.findStudentByUsername(username).getUser_id();
    }

    // Метод для получения студента по его идентификатору
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    // Метод для получения студента по его имени пользователя
    public Student getStudentByUsername(String username) {
        return studentRepository.findStudentByUsername(username);
    }

    public List<Student> getAllStudentsSortedByLastName() {
        return studentRepository.findAllByOrderByLastNameAsc();
    }

    public List<Student> getAllStudentsByStudyGroupId(Long studyGroupId) {

        return studentRepository.findByStudyGroup(studyGroupService.getStudyGroupById(studyGroupId));
    }
}

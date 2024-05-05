package ru.coursework.gradebook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Метод для получения пользователя по его имени пользователя
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    // Метод для добавления студента
    public void addStudent(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_STUDENT");
        userRepository.save(user);
    }

    // Метод для добавления преподавателя
    public void addProfessor(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_PROFESSOR");
        userRepository.save(user);
    }

    // Метод для добавления администратора
    public void addAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_ADMIN, ROLE_PROFESSOR, ROLE_STUDENT");
        userRepository.save(user);
    }

    // Метод для получения списка всех пользователей
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Метод для получения идентификатора пользователя по его имени пользователя
    public Long getUserIdByUsername(String username) {
        return userRepository.findUser_idByUsername(username);
    }
}

package ru.coursework.gradebook.auth;

import jakarta.servlet.Registration;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.coursework.gradebook.user.User;
import ru.coursework.gradebook.user.UserService;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;

    // Внедрение зависимости регистрации сервиса
    @Autowired
    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    // Обработка GET-запроса для страницы входа
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // Обработка GET-запроса для страницы регистрации
    @GetMapping("/signup")
    public String signup(@ModelAttribute("user") User user) {
        return "auth/signup_form";
    }

    // Обработка POST-запроса для выполнения регистрации пользователя
    @PostMapping("/signup")
    public String performRegistration(Model model, @ModelAttribute("user") User user,
                                      BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            // Проверка и регистрация пользователя
            Map<String, String> report = registrationService.checkAndRegister(user);
            if (report.isEmpty()) {
                return "redirect:auth/login";
            }
            // Если есть ошибки при регистрации, добавляем их в модель
            for (Map.Entry<String, String> entry : report.entrySet()) {
                model.addAttribute(entry.getKey(), entry.getValue());
            }
        }
        // Возвращаем страницу регистрации с ошибками
        return "auth/signup_form";
    }


}


package ru.coursework.gradebook.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.coursework.gradebook.user.User;

@Controller
public class HomeController {

    public String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                // Возвращаем имя роли (без префикса "ROLE_")
                return auth.getAuthority().substring("ROLE_".length());
            }
        }
        return null; // Если роль не найдена или пользователь не аутентифицирован
    }

    @GetMapping("/home")
    public String getHomePage() {
        String userRole = getCurrentUserRole();

        if (userRole.contains("ADMIN")) {
            System.out.println(userRole);
            return "redirect:/admin";
        } else if (userRole.contains("PROFESSOR")) {
            System.out.println(userRole);
            return "redirect:/professors/home";
        } else if (userRole.contains("STUDENT")) {
            System.out.println(userRole);
            return "index";
        }

        System.out.println(userRole);
        return "redirect:/";
    }
}

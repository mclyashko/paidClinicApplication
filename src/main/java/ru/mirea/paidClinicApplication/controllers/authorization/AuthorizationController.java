package ru.mirea.paidClinicApplication.controllers.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.services.appUser.AppUserService;

@Controller
public class AuthorizationController {

    private final AppUserService appUserService;

    @Autowired
    public AuthorizationController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") AppUser user) {
        return appUserService.save(user);
    }

    @GetMapping("/authentication_failure")
    public String getAuthenticationFailurePage() {
        return "authentication_failure";
    }
}

package ru.mirea.paidClinicApplication.controllers.home;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mirea.paidClinicApplication.entities.appUser.AppUserRole;

@Controller
public class HomePatientController {
    @GetMapping("/home_patient")
    @Secured(AppUserRole.PatientFinalStr)
    public String getHomePage() {
        return "home_patient";
    }
}

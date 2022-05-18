package ru.mirea.paidClinicApplication.controllers.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePatientController {
    @GetMapping("/home_patient")
    public String getHomePage() {
        return "home_patient";
    }
}

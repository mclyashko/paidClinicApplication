package ru.mirea.paidClinicApplication.controllers.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeDoctorController {
    @GetMapping("/home_doctor")
    public String getHomePage() {
        return "home_doctor";
    }
}

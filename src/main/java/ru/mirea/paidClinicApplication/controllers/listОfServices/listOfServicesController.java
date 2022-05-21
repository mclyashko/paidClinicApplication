package ru.mirea.paidClinicApplication.controllers.listОfServices;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mirea.paidClinicApplication.entities.appUser.AppUserRole;

@Controller
public class listOfServicesController {
    @GetMapping("/list_of_services")
    @Secured(AppUserRole.PatientFinalStr)
    public String getListOfServicesPage() {
        return "list_of_services";
    }

}

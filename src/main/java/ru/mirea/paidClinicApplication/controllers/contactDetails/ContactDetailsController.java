package ru.mirea.paidClinicApplication.controllers.contactDetails;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mirea.paidClinicApplication.entities.appUser.AppUserRole;

@Controller
public class ContactDetailsController {
    @GetMapping("/contact_details")
    @Secured(AppUserRole.PatientFinalStr)
    public String getContactDetailsPage() {
        return "contact_details";
    }
}
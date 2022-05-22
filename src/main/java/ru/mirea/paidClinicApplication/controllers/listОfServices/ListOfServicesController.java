package ru.mirea.paidClinicApplication.controllers.listОfServices;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.entities.appUser.AppUserRole;
import ru.mirea.paidClinicApplication.entities.procedure.Procedure;
import ru.mirea.paidClinicApplication.services.appUser.AppUserService;
import ru.mirea.paidClinicApplication.services.procedure.ProcedureService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ListOfServicesController {

    private final AppUserService appUserService;
    private final ProcedureService procedureService;

    @Autowired
    public ListOfServicesController(AppUserService appUserService, ProcedureService procedureService) {
        this.appUserService = appUserService;
        this.procedureService = procedureService;
    }

    @GetMapping("/list_of_services")
    @Secured(AppUserRole.PatientFinalStr)
    public String getListOfServicesPage(Model model) {
        AppUser appUser = appUserService.getCurrentUser();
        List<Procedure> procedureList = procedureService.getAllProceduresSortedByCoast();

        model.addAttribute("me",
                appUser
        );
        model.addAttribute("services",
                procedureList
        );

        return "list_of_services";
    }

    @PostMapping("/make_an_appointment")
    @Secured(AppUserRole.PatientFinalStr)
    public ModelAndView makeAnAppointment(@ModelAttribute("newAppointmentInfo") NewAppointmentInfo newAppointmentInfo) {
        System.out.println(newAppointmentInfo);
        return new ModelAndView("redirect:/list_of_services"); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    @PostMapping("fBPD")
    @Secured(AppUserRole.PatientFinalStr)
    public String filterByProcedureDescription(@RequestParam String procedureDescription, Model model) {
        AppUser appUser = appUserService.getCurrentUser();
        List<Procedure> procedureListFilteredByProcedureDescription;

        if (procedureDescription != null && !procedureDescription.isEmpty()) {
            procedureListFilteredByProcedureDescription = procedureService.
                    getAllProceduresSortedByCoastFilteredByProcedureDescription(
                            procedureDescription
                    );
        }
        else {
            procedureListFilteredByProcedureDescription = procedureService.getAllProceduresSortedByCoast();
        }

        model.addAttribute("me",
                appUser
        );
        model.addAttribute("services",
                procedureListFilteredByProcedureDescription
        );

        return "list_of_services";
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
final class NewAppointmentInfo {
    private Long clientId;

    private Long procedureId;

    //@DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "newAppointmentInfo{" +
                "clientId=" + clientId +
                ", procedureId=" + procedureId +
                ", dateTime=" + dateTime +
                '}';
    }
}

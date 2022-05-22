package ru.mirea.paidClinicApplication.controllers.home;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.mirea.paidClinicApplication.entities.record.Record;
import ru.mirea.paidClinicApplication.services.appUser.AppUserService;
import ru.mirea.paidClinicApplication.services.record.RecordService;

import java.util.List;

@SuppressWarnings("SameReturnValue")
@Controller
public class HomePatientController {

    private final AppUserService appUserService;
    private final RecordService recordService;

    @Autowired
    public HomePatientController(AppUserService appUserService, RecordService recordService) {
        this.appUserService = appUserService;
        this.recordService = recordService;
    }

    @GetMapping("/home_patient")
    @Secured(AppUserRole.PatientFinalStr)
    public String getHomePage(Model model) {
        AppUser appUser = appUserService.getCurrentUser();
        List<Record> allRecordsWithAppUserIdSortedByDate = recordService.getAllRecordsSortedByDateByPatientId(
                appUser.getId()
        );
        model.addAttribute("me",
                appUser
        );
        model.addAttribute("records",
                allRecordsWithAppUserIdSortedByDate
        );
        return "home_patient";
    }

    @PostMapping("/cancel_entry")
    @Secured(AppUserRole.PatientFinalStr)
    public ModelAndView cancelEntry(@ModelAttribute("deleteRecordInfo") DeleteRecordInfo deleteRecordInfo){
        recordService.deleteRecordById(deleteRecordInfo.getId());
        return new ModelAndView("redirect:/home_patient");
    }

    @PostMapping("filterByProcedureDescription")
    @Secured(AppUserRole.PatientFinalStr)
    public String filterByProcedureDescription(@RequestParam String procedureDescription, Model model) {
        AppUser appUser = appUserService.getCurrentUser();

        List<Record> recordsWithAppUserIdSortedByDateFilteredByProcedureDescription;

        if (procedureDescription != null && !procedureDescription.isEmpty()) {
            recordsWithAppUserIdSortedByDateFilteredByProcedureDescription = recordService.
                    getAllRecordsSortedByDateByPatientIdFilteredByProcedureDescription(
                            appUser.getId(), procedureDescription
                    );
        }
        else {
            recordsWithAppUserIdSortedByDateFilteredByProcedureDescription = recordService.
                    getAllRecordsSortedByDateByPatientId(
                            appUser.getId()
                    );
        }

        model.addAttribute("me",
                appUser
        );
        model.addAttribute("records",
                recordsWithAppUserIdSortedByDateFilteredByProcedureDescription
        );

        return "home_patient";
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
final class DeleteRecordInfo{
    private Long id;

    @Override
    public String toString() {
        return "DeleteRecordInfo{" +
                "id=" + id +
                '}';
    }
}
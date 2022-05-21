package ru.mirea.paidClinicApplication.controllers.home;

import lombok.*;
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

@SuppressWarnings("ALL")
@Controller
public class HomeDoctorController {

    private final AppUserService appUserService;
    private final RecordService recordService;

    @Autowired
    public HomeDoctorController(AppUserService appUserService, RecordService recordService) {
        this.appUserService = appUserService;
        this.recordService = recordService;
    }

    @GetMapping("/home_doctor")
    @Secured(AppUserRole.DoctorFinalStr)
    public String getHomePage(Model model) {
        AppUser appUser = appUserService.getCurrentUser();
        List<Record> notNullRecordsWithAppUserIdSortedByDate = recordService.
                getRecordsSortedByDateWithNotNoneVerdictByDoctorId(
                        appUser.getId()
                );
        model.addAttribute("me",
                appUser
        );
        model.addAttribute("records",
                notNullRecordsWithAppUserIdSortedByDate
        );
        model.addAttribute("recordData", new RecordVerdictData());
        return "home_doctor";
    }

    @PostMapping("/record_data")
    @Secured(AppUserRole.DoctorFinalStr)
    public ModelAndView addData(@ModelAttribute("recordData") RecordVerdictData recordVerdictData) {
        recordService.updateVerdictById(recordVerdictData.getId(), recordVerdictData.getVerdict());
        return new ModelAndView("redirect:/home_doctor");
    }

    @SuppressWarnings("SameReturnValue")
    @PostMapping("filterByEmail")
    @Secured(AppUserRole.DoctorFinalStr)
    public String filterRecordsByEmail(@RequestParam String email, Model model) {
        AppUser appUser = appUserService.getCurrentUser();

        List<Record> notNullRecordsWithAppUserIdAndPatientEmailSortedByDate;

        if (email != null && !email.isEmpty()) {
            notNullRecordsWithAppUserIdAndPatientEmailSortedByDate = recordService.
                    getRecordsSortedByDateWithNotNoneVerdictByDoctorIdAndPatientEmail(
                            appUser.getId(), email
                    );
        }
        else {
            notNullRecordsWithAppUserIdAndPatientEmailSortedByDate = recordService.
                    getRecordsSortedByDateWithNotNoneVerdictByDoctorId(
                            appUser.getId()
                    );
        }

        model.addAttribute("me",
                appUser
        );
        model.addAttribute("records",
                notNullRecordsWithAppUserIdAndPatientEmailSortedByDate
        );
        model.addAttribute("recordData", new RecordVerdictData());
        return "home_doctor";
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
final class RecordVerdictData{
    private Long id;
    private String verdict;

    @Override
    public String toString() {
        return "RecordVerdictData{" +
                "id=" + id +
                ", verdict='" + verdict + '\'' +
                '}';
    }
}

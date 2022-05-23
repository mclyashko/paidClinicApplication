package ru.mirea.paidClinicApplication.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.entities.appUser.AppUserGender;
import ru.mirea.paidClinicApplication.entities.appUser.AppUserRole;
import ru.mirea.paidClinicApplication.repositories.appUser.AppUserRepository;
import ru.mirea.paidClinicApplication.services.appUser.AppUserService;

import java.time.LocalDate;
import java.time.ZoneId;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        AppUserService.class
})
public class AppUserServiceTests {

    @MockBean
    private AppUserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserService userService;

    @Test
    public void create() {
        // given
        AppUser user = getAppUser();
        String need = "login";
        Mockito.when(passwordEncoder.encode(user.getPassword())).
                thenReturn("$2a$10$A4e78XI7wzwzBF1cEH.wYOFvyq1zDUP5neMY1VOi9Ep6dQHNHRj.e");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        // when
        String getS = userService.save(user);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assertions.assertEquals(need, getS);
    }

    private AppUser getAppUser() {
        AppUser appUser = new AppUser();

        appUser.setId(1L);
        appUser.setName("1");
        appUser.setSurname("1");
        appUser.setPatronymic("1");
        appUser.setGender(AppUserGender.MALE);
        appUser.setDateOfBirth(
                java.util.Date.from(
                        LocalDate.of(2000,1,1).
                                atStartOfDay(ZoneId.of("Europe/Moscow")).toInstant()
                )
        );
        appUser.setPhoneNumber("11111111111");
        appUser.setEmail("1@1.ru");
        appUser.setAppUserRole(AppUserRole.PATIENT);
        appUser.setPassword("1111111111");

        return appUser;
    }
}
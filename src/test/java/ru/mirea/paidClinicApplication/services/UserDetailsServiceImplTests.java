package ru.mirea.paidClinicApplication.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.entities.appUser.AppUserGender;
import ru.mirea.paidClinicApplication.entities.appUser.AppUserRole;
import ru.mirea.paidClinicApplication.repositories.appUser.AppUserRepository;
import ru.mirea.paidClinicApplication.services.appUser.UserDetailsServiceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        UserDetailsServiceImpl.class
})
public class UserDetailsServiceImplTests {

    @MockBean
    private AppUserRepository appUserRepository;

    @Test
    public void loadUserByUsername() {
        // given
        AppUser appUser = getAppUser();
        org.springframework.security.core.userdetails.User userS =
                new org.springframework.security.core.userdetails.User(
                        appUser.getEmail(),
                        appUser.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority(appUser.getAppUserRole().name()))
                );
        Mockito.when(appUserRepository.findByEmail(userS.getUsername())).thenReturn(java.util.Optional.of(appUser));

        // when
        Optional<AppUser> get = appUserRepository.findByEmail(userS.getUsername());

        // then
        Mockito.verify(appUserRepository, Mockito.times(1)).findByEmail(userS.getUsername());
        get.ifPresent(value -> assertThat(value).isEqualTo(appUser));
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
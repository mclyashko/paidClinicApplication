package ru.mirea.paidClinicApplication.services.appUser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.entities.appUser.AppUserRole;
import ru.mirea.paidClinicApplication.repositories.appUser.AppUserRepository;

import java.util.Optional;

@SuppressWarnings({"unused"})
@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String save(AppUser appUser) {
        boolean isUserAlreadyExists = (appUserRepository.findByEmail(appUser.getEmail()).isPresent() ||
                appUserRepository.findByPhoneNumber(appUser.getPhoneNumber()).isPresent()
        );

        if (isUserAlreadyExists) {
            return "user_already_exists";
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setAppUserRole(AppUserRole.PATIENT);

        appUserRepository.save(appUser);

        return "login";
    }

    public AppUser findByUsername(String email) {
        Optional<AppUser> appUser = appUserRepository.findByEmail(email);
        if (appUser.isEmpty()) {
            return null;
        }
        return appUser.get();
    }

    public AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User principal = (User) authentication.getPrincipal();

        return appUserRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }
}

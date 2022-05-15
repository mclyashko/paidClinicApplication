package ru.mirea.paidClinicApplication.services.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.repositories.appUser.AppUserRepository;

import java.util.Optional;

@SuppressWarnings({"ClassCanBeRecord", "unused"})
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser save(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
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

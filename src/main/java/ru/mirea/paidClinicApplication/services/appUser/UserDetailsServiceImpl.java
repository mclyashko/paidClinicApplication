package ru.mirea.paidClinicApplication.services.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.repositories.appUser.AppUserRepository;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new User(
                appUser.getEmail(),
                appUser.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + appUser.getAppUserRole().name()))
        );
    }
}

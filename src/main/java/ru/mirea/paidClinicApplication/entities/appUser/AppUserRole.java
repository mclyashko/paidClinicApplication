package ru.mirea.paidClinicApplication.entities.appUser;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
    DOCTOR,
    PATIENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
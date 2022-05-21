package ru.mirea.paidClinicApplication.entities.appUser;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
    DOCTOR,
    PATIENT;

    public static final String DoctorFinalStr = "DOCTOR";
    public static final String PatientFinalStr = "PATIENT";

    @Override
    public String getAuthority() {
        return name();
    }
}
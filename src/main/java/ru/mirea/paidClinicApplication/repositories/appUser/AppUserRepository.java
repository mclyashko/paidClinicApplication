package ru.mirea.paidClinicApplication.repositories.appUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email); // email = login
}

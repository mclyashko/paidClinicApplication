package ru.mirea.paidClinicApplication.repositories.record;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.paidClinicApplication.entities.record.Record;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findByClient_Email(String email);
    Optional<Record> findByClient_PhoneNumber(String phoneNumber);
    List<Record> findAllByClient_NameAndClient_SurnameAndClient_PatronymicAndClient_DateOfBirth(
            String client_name, String client_surname, String client_patronymic, Date client_dateOfBirth
    );
}

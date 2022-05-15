package ru.mirea.paidClinicApplication.repositories.procedure;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.paidClinicApplication.entities.procedure.Procedure;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {}

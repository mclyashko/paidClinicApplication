package ru.mirea.paidClinicApplication.services.procedure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.paidClinicApplication.entities.procedure.Procedure;
import ru.mirea.paidClinicApplication.repositories.procedure.ProcedureRepository;
import ru.mirea.paidClinicApplication.repositories.record.RecordRepository;
import ru.mirea.paidClinicApplication.services.record.RecordService;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProcedureService {
    private final ProcedureRepository procedureRepository;

    @Autowired
    public ProcedureService(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    public List<Procedure> getAllProceduresSortedByCoast() {
        return procedureRepository.findAll().stream().sorted(new ProcedureComparator()).collect(Collectors.toList());
    }

    public List<Procedure> getAllProceduresSortedByCoastFilteredByProcedureDescription(String procedureDescription) {
        return procedureRepository.findAll().stream().filter(e -> e.getDescription().toUpperCase(Locale.ROOT).
                contains(procedureDescription.toUpperCase())).
                sorted(new ProcedureComparator()).collect(Collectors.toList());
    }
}

final class ProcedureComparator implements Comparator<Procedure> {

    @Override
    public int compare(Procedure o1, Procedure o2) {
        return Double.compare(o1.getCost(), o2.getCost());
    }
}

package ru.mirea.paidClinicApplication.services.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.paidClinicApplication.entities.record.Record;
import ru.mirea.paidClinicApplication.repositories.record.RecordRepository;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecordService {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository){
        this.recordRepository = recordRepository;
    }

    public void updateVerdictById(Long id, String verdict) {
        recordRepository.updateRecordVerdictById(verdict, id);
    }

    public List<Record> getRecordsSortedByDateWithNotNoneVerdictByDoctorId(Long id) {
        return recordRepository.findAllByProcedure_ArtistInfo_Artist_id(
                id
                ).stream().filter(e -> e.getVerdict().equals("None")).
                sorted(new RecordComparator()).collect(Collectors.toList());
    }

    public List<Record> getRecordsSortedByDateWithNotNoneVerdictByDoctorIdAndPatientEmail(Long id, String email) {
        return recordRepository.findAllByProcedure_ArtistInfo_Artist_idAndClient_Email(
                        id, email
                ).stream().filter(e -> e.getVerdict().equals("None")).
                sorted(new RecordComparator()).collect(Collectors.toList());
    }

    public List<Record> getAllRecordsSortedByDateByPatientId(Long id) {
        return recordRepository.findAllByClient_Id(
                id
        ).stream().sorted(new RecordComparator()).collect(Collectors.toList());
    }

    public List<Record> getAllRecordsSortedByDateByPatientIdFilteredByProcedureDescription(Long id, String description){
        return recordRepository.findAllByClient_Id(
                id
        ).stream().filter(e -> e.getProcedure().getDescription().toUpperCase(Locale.ROOT).
                        contains(description.toUpperCase())).
                sorted(new RecordComparator()).collect(Collectors.toList());
    }

    public void deleteRecordById(Long id){
        recordRepository.deleteById(id);
    }
}

final class RecordComparator implements Comparator<Record> {

    @Override
    public int compare(Record o1, Record o2) {
        return o1.getDateTime().compareTo(o2.getDateTime());
    }
}

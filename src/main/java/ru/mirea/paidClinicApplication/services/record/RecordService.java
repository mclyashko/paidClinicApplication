package ru.mirea.paidClinicApplication.services.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.paidClinicApplication.entities.record.Record;
import ru.mirea.paidClinicApplication.repositories.record.RecordRepository;

import javax.transaction.Transactional;
import java.util.List;
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

    public List<Record> getRecordsWithNotNoneVerdictByDoctorId(Long id) {
        return recordRepository.findAllByProcedure_ArtistInfo_Artist_id(
                id
        ).stream().filter(e -> e.getVerdict().equals("None")).collect(Collectors.toList());
    }
}

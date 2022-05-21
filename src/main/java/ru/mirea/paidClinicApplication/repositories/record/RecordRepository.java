package ru.mirea.paidClinicApplication.repositories.record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.paidClinicApplication.entities.record.Record;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByProcedure_ArtistInfo_Artist_id(Long artistId);

    List<Record> findAllByProcedure_ArtistInfo_Artist_idAndClient_Email(Long artistId, String email);

    @Modifying
    @Query("update Record r set r.verdict = ?1 where r.id = ?2")
    void updateRecordVerdictById(String verdict, Long recordId);
}

package ru.mirea.paidClinicApplication.repositories.artistInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.paidClinicApplication.entities.artistInfo.ArtistInfo;

public interface ArtistInfoRepository extends JpaRepository<ArtistInfo, Long> {}

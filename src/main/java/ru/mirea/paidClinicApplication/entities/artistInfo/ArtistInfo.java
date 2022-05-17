package ru.mirea.paidClinicApplication.entities.artistInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.entities.procedure.Procedure;

import javax.persistence.*;

@SuppressWarnings("ALL")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistInfo {
    @Id
    @SequenceGenerator(name = "artist_info_sequence", sequenceName = "artist_info_sequence", allocationSize = 1)
    @GeneratedValue(generator = "artist_info_sequence", strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    private Long id;

//    @Column(nullable = false, unique = true)
//    private Long artistId; // to AppUser !!!

    @Column(nullable = false, unique = true)
    private Long cabinet;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedure_id", insertable = false, updatable = false)
    @JsonIgnore
    private Procedure procedure;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "artistInfo")
    private AppUser artist;
}

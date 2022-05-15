package ru.mirea.paidClinicApplication.entities.procedure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.paidClinicApplication.entities.artistInfo.ArtistInfo;
import ru.mirea.paidClinicApplication.entities.record.Record;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Procedure {
    @Id
    @SequenceGenerator(name = "procedure_sequence", sequenceName = "procedure_sequence", allocationSize = 1)
    @GeneratedValue(generator = "procedure_sequence", strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double cost;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", insertable = false, updatable = false)
    @JsonIgnore
    private Record record;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "procedure")
    private ArtistInfo artistInfo;
}

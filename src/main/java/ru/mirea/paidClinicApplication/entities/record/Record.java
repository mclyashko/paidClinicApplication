package ru.mirea.paidClinicApplication.entities.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.mirea.paidClinicApplication.entities.appUser.AppUser;
import ru.mirea.paidClinicApplication.entities.procedure.Procedure;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    @Id
    @SequenceGenerator(name = "record_sequence", sequenceName = "record_sequence", allocationSize = 1)
    @GeneratedValue(generator = "record_sequence", strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String verdict;

    @Column(nullable = false)
    @DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @JsonIgnore
    private AppUser client;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "record")
    private Procedure procedure;
}

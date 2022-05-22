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
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String verdict;

    @Column(nullable = false)
    //@DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @JsonIgnore
    private AppUser client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedure_id", insertable = false, updatable = false)
    @JsonIgnore
    private Procedure procedure;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", verdict='" + verdict + '\'' +
                ", dateTime=" + dateTime +
                ", client=" + client +
                ", procedure=" + procedure +
                '}';
    }
}

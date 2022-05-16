package ru.mirea.paidClinicApplication.entities.appUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.mirea.paidClinicApplication.entities.artistInfo.ArtistInfo;
import ru.mirea.paidClinicApplication.entities.record.Record;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @SequenceGenerator(name = "app_users_sequence", sequenceName = "app_users_sequence", allocationSize = 1)
    @GeneratedValue(generator = "app_users_sequence", strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String patronymic;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppUserGender gender;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Column(nullable = false, unique = true)
    @Size(min = 11, max = 11, message = "Num should be exact 11 char")
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    @Email
    private String email; // email = login

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Record> records = new ArrayList<>();

    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_info_id", insertable = false, updatable = false)
    @JsonIgnore
    private ArtistInfo artistInfo;
}

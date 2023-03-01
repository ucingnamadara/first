package id.kawahedukasi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "peserta")
public class Peserta extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "pesertaSequence", sequenceName = "peserta_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "pesertaSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "email")
    public String email;

    @Column(name = "phone_number")
    public String phoneNumber;
}

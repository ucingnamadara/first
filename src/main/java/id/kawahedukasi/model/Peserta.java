package id.kawahedukasi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "peserta", indexes = {
        @Index(name = "idx_peserta_name_email_phone_number", columnList = "name,email,phone_number")
})
public class Peserta extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "pesertaSequence", sequenceName = "peserta_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "pesertaSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(name = "name", columnDefinition = "default 'dummy'")
    public String name = "dummy";

    @Column(name = "email")
    public String email;

    @Column(name = "phone_number")
    public String phoneNumber;

    @CreationTimestamp
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
}

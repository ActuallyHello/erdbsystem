package com.ustu.erdb.modules.models.store.models;

import com.ustu.erdb.base.store.models.EnumerationValue;
import com.ustu.erdb.modules.persons.store.models.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"person"})
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String label;
    @Column(nullable = false)
    private String topic;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(updatable = false)
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
    @Builder.Default
    @Column(nullable = false)
    private Boolean isTaskResult = false;
    @Column(nullable = true)
    private String schemaPath;

    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private Person person;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return id != null && id.equals(model.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

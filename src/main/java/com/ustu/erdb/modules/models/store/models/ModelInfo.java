package com.ustu.erdb.modules.models.store.models;

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
@ToString(exclude = {"model"})
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="modelinfo")
public class ModelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer tableCount;
    @Column(nullable = false)
    private Integer relationCount;
    @Column(nullable = false)
    private String archetype;
    @Column(nullable = false)
    private Integer normalizationLevel;

    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private Model model;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelInfo model = (ModelInfo) o;
        return id != null && id.equals(model.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package com.ustu.erdb.modules.persons.store.models;

import com.ustu.erdb.base.store.models.EnumerationValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"type", "group", "user"})
@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column
    private String middleName;

    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private EnumerationValue type;

    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    private EnumerationValue group;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE,
            optional = false
    )
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id != null && id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

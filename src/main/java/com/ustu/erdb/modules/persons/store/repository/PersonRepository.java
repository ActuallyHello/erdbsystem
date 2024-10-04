package com.ustu.erdb.modules.persons.store.repository;

import com.happyfxmas.erdb.base.store.models.EnumerationValue;
import com.happyfxmas.erdb.persons.store.models.Person;
import com.happyfxmas.erdb.persons.store.models.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("""
            select person from Person person
                inner join fetch person.type type
                inner join fetch person.group group
                inner join fetch person.user user
            where type = :type
            """)
    List<Person> findAllByType(EnumerationValue type);

    @Query("""
            select person from Person person
                inner join fetch person.type type
                inner join fetch person.group group
                inner join fetch person.user user
            where group = :group
            """)
    List<Person> findAllByGroup(EnumerationValue group);

    @Query("""
            select person from Person person
                inner join fetch person.type type
                inner join fetch person.group group
                inner join fetch person.user user
            where user = :user
            """)
    @NonNull Optional<Person> findByUser(@NonNull User user);

    @Query("""
            select person from Person person
                inner join fetch person.type type
                inner join fetch person.group group
                inner join fetch person.user user
            where person.id = :id
            """)
    @NonNull Optional<Person> findById(@NonNull Long id);
}

package com.ustu.erdb.base.store.repository;

import com.ustu.erdb.base.store.models.Enumeration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnumerationRepository extends JpaRepository<Enumeration, Long> {

    @Query("""
            select enumeration from Enumeration enumeration
            where enumeration.code = :code
            """)
    Optional<Enumeration> findByCode(String code);

}

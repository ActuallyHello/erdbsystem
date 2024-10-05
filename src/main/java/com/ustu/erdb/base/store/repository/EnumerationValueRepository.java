package com.ustu.erdb.base.store.repository;

import com.ustu.erdb.base.store.models.EnumerationValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnumerationValueRepository extends JpaRepository<EnumerationValue, Long> {

    @Query("""
            select enumerationValue from EnumerationValue enumerationValue
                inner join fetch enumerationValue.enumeration
            where enumerationValue.code = :code
            """)
    Optional<EnumerationValue> findByCode(String code);

    @Query("""
            select enumerationValue from EnumerationValue enumerationValue
            where enumerationValue.enumeration.id = :enumerationId
            """)
    List<EnumerationValue> findAllByEnumeration(Long enumerationId);
}

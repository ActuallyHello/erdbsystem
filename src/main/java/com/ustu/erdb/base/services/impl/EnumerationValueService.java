package com.ustu.erdb.base.services.impl;

import com.happyfxmas.erdb.base.exceptions.LogicalException;
import com.happyfxmas.erdb.base.services.ERDBService;
import com.happyfxmas.erdb.base.store.models.Enumeration;
import com.happyfxmas.erdb.base.store.models.EnumerationValue;
import com.happyfxmas.erdb.base.store.repository.EnumerationValueRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EnumerationValueService implements ERDBService {

    @Autowired
    private EnumerationValueRepository enumerationValueRepository;

    public EnumerationValue getById(@NonNull Long id) {
        return enumerationValueRepository
                .findById(id)
                .orElseThrow(() -> new LogicalException("No such enumerationValue with id " + id));
    }

    public EnumerationValue getByCode(@NonNull String code) {
        return enumerationValueRepository
                .findByCode(code)
                .orElseThrow(() -> new LogicalException("No such enumerationValue with code " + code));
    }

    public List<EnumerationValue> getByEnumeration(@NonNull Enumeration enumeration) {
        return enumerationValueRepository
                .findAllByEnumeration(enumeration.getId());
    }

    @Transactional
    public EnumerationValue create(@NonNull EnumerationValue enumerationValue) {
        return enumerationValueRepository.save(enumerationValue);
    }
}

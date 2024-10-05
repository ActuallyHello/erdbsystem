package com.ustu.erdb.base.services.impl;

import com.ustu.erdb.base.exceptions.LogicalException;
import com.ustu.erdb.base.services.ERDBService;
import com.ustu.erdb.base.store.models.Enumeration;
import com.ustu.erdb.base.store.repository.EnumerationRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EnumerationService implements ERDBService {

    @Autowired
    private EnumerationRepository enumerationRepository;

    public Enumeration getById(@NonNull Long id) {
        return enumerationRepository
                .findById(id)
                .orElseThrow(() -> new LogicalException("No such enumeration with id " + id));
    }

    public Enumeration getByCode(@NonNull String code) {
        return enumerationRepository
                .findByCode(code)
                .orElseThrow(() -> new LogicalException("No such enumeration with code " + code));
    }

    @Transactional
    public Enumeration create(@NonNull Enumeration enumeration) {
        return enumerationRepository.save(enumeration);
    }

}

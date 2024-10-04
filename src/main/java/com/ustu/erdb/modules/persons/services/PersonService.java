package com.ustu.erdb.modules.persons.services;

import com.happyfxmas.erdb.base.exceptions.LogicalException;
import com.happyfxmas.erdb.base.services.ERDBService;
import com.happyfxmas.erdb.base.store.models.EnumerationValue;
import com.happyfxmas.erdb.persons.store.models.Person;
import com.happyfxmas.erdb.persons.store.models.User;
import com.happyfxmas.erdb.persons.store.repository.PersonRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService implements ERDBService {

    @Autowired
    private PersonRepository personRepository;

    public Person getById(@NonNull Long id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new LogicalException("No such person with id " + id));
    }

    public List<Person> getAll(@NonNull PageRequest pageRequest) {
        return personRepository
                .findAll(pageRequest)
                .toList();
    }

    public List<Person> getAllByType(@NonNull EnumerationValue type) {
        return personRepository
                .findAllByType(type);
    }

    public List<Person> getAllByGroup(@NonNull EnumerationValue group) {
        return personRepository
                .findAllByGroup(group);
    }

    public Person getByUser(@NonNull User user) {
        return personRepository
                .findByUser(user)
                .orElseThrow(() -> new LogicalException("No such person with user_id " + user.getId()));
    }

    @Transactional
    public Person create(@NonNull Person person) {
        return personRepository.save(person);
    }
}

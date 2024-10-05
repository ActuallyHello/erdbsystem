package com.ustu.erdb.modules.persons.services;

import com.ustu.erdb.base.exceptions.LogicalException;
import com.ustu.erdb.base.services.ERDBService;
import com.ustu.erdb.modules.persons.store.models.User;
import com.ustu.erdb.modules.persons.store.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService implements ERDBService {

    @Autowired
    private UserRepository userRepository;

    public User getById(@NonNull Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new LogicalException("No such user with id " + id));
    }

    public List<User> getAll(@NonNull PageRequest pageRequest) {
        return userRepository
                .findAll(pageRequest)
                .toList();
    }

    public User getByLoginAndPassword(@NonNull String login, @NonNull String password) {
        return userRepository
                .findByLoginAndPassword(login, password)
                .orElseThrow(() -> new LogicalException("Incorrect credentials for + " ));
    }

    @Transactional
    public User create(@NonNull User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new LogicalException("User with login  already exists!");
        }
        return userRepository.save(user);
    }
}

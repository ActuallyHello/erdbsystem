package com.ustu.erdb.modules.persons.store.repository;

import com.ustu.erdb.modules.persons.store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select CASE WHEN COUNT(user) > 0 THEN true ELSE false END from User user
            where user.login = :login
            """)
    boolean existsByLogin(String login);

//    @Query("""
//            select CASE WHEN COUNT(user) > 0 THEN true ELSE false END from User user
//            where user.email = :email
//            """)
//    boolean existsByEmail(String email);

    @Query("""
            select user from User user
            where user.login = :login and user.password = :password
            """)
    Optional<User> findByLoginAndPassword(String login, String password);
}

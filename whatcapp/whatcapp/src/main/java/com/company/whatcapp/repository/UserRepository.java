package com.company.whatcapp.repository;

import com.company.whatcapp.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.full_name LIKE %:query% or u.email LIKE %:query%")
    List<User> searchUser(@Param("query") String query);
}

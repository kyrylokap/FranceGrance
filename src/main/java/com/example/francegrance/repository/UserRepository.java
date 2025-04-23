package com.example.francegrance.repository;

import com.example.francegrance.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);


    @Transactional
    @Modifying
    @Query("UPDATE User u set u.phone = :phone, u.email = :email where u.id = :id")
    void updateUserById(@Param("id") Long id,@Param("phone") String phone,@Param("email") String email);
}

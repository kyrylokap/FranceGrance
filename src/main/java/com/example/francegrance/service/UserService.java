package com.example.francegrance.service;

import com.example.francegrance.models.User;
import com.example.francegrance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }
    public void save(String username,String password){
        repo.save(User.builder().username(username).password(password).role("USER").build());
    }

    public List<User> findAll(){
        return repo.findAll();
    }

    public User findByUsername(String username){
        return repo.findByUsername(username);
    }
}

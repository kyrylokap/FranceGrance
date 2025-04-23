package com.example.francegrance.service;

import com.example.francegrance.models.Fragrance;
import com.example.francegrance.models.User;
import com.example.francegrance.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class UserService{
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
    }
    public void register(String username,String password){
        repo.save(User.builder().username(username).password(passwordEncoder.encode(password)).role("USER").build());
    }

    public List<User> getAll(){
        return repo.findAll();
    }

    public User findByUsername(String username){
        return repo.findByUsername(username);
    }

    public String validateRegister(String password,
                                   String username,
                                   String confirmedPassword,
                                   AtomicReference<String> err) {
        if (!password.equals(confirmedPassword)){
            err.set("Passwords do not match.");
            return "redirect:/register";
        }

        if (findByUsername(username) != null) {
            err.set("Username already exists.");
            return "redirect:/register";
        }

        if (password.length() < 8) {
            err.set("Password must be at least 8 characters.");
            return "redirect:/register";
        }
        register(username,password);
        return "redirect:/login";
    }
    public void deleteUser(Long userId){
        repo.deleteById(userId);
    }

    @Transactional
    public void addEmailAndPhone(String username,String phone,String email){
        if(username.isEmpty()){
            repo.updateUserById(repo.findByUsername(username).getId(),phone,email);
        }
    }

}

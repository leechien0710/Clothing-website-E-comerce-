package com.sda.java3.ecommerce.services.user;

import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
    public boolean checkEmailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
    public User checkEmailAndPass(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if( user.getPassword().equals(password))
            	return user;
        }
        return null;
    }

    
}


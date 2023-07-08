package com.example.demo.data;

import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataService {
    @Autowired
    UserRepository repository;
    public Optional<User> findByName(String name){
        return repository.findByUsername(name);
    }
}

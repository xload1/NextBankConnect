package com.example.nextbank.services;

import com.example.nextbank.model.Users;
import com.example.nextbank.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UsersRepository usersRepository;
    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public void saveUser(Users user) {
        usersRepository.save(user);
    }
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}

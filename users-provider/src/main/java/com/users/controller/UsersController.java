package com.users.controller;


import com.users.model.Users;
import com.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class UsersController {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/get-all-users")
    List<Users> getAllUsers() throws IOException {
        return usersRepository.getUsers();
    }
}

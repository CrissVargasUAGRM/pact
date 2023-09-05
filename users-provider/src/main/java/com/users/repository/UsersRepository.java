package com.users.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.model.Users;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class UsersRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Users> getUsers() throws IOException {
        URL resource = getClass().getClassLoader().getResource("users.json");
        return objectMapper.readValue(resource, new TypeReference<>() {
        });
    }
}

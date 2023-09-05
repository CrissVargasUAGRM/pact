package com.users.client;

import com.users.model.User;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class UsersApiClient {
    private final UsersService usersService;

    public UsersApiClient(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        usersService = retrofit.create(UsersService.class);
    }

    public List<User> fetchUsers() throws IOException {
        Response<List<User>> response = usersService.getUsers().execute();
        return response.body();
    }
}

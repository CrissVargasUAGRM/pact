package com.users.client;

import com.users.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface UsersService {
    @GET("/get-all-users")
    Call<List<User>> getUsers();
}

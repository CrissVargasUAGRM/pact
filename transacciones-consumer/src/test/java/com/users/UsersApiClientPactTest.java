package com.users;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.users.client.UsersApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.Map;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.*;

@ExtendWith(PactConsumerTestExt.class)
public class UsersApiClientPactTest {
    @Pact(provider = "users_provider", consumer = "users_consumer")
    public V4Pact UsersListPact(PactDslWithProvider builder) {
        return builder
                .given("there are users")
                .uponReceiving("a request for users")
                .path("/get-all-users")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(newJsonArrayMinLike(1, a -> a.object(o -> {
                    o.id("id");
                    o.eachLike("items", i -> {
                        i.stringType("id");
                        i.stringType("userName");
                        i.numberType("email");
                        i.numberType("secretPass");
                        i.numberType("accountType");
                        i.numberType("personId");

                    });
                })).build())
                .toPact(V4Pact.class);
    }

    @Pact(provider = "users_provider", consumer = "users_consumer")
    public V4Pact noUsersPact(PactDslWithProvider builder) {
        return builder
                .given("there are no users")
                .uponReceiving("a request for users")
                .path("/get-all-users")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body("[]")
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "noUsersPact")
    void getEmptyListOfUSers(MockServer mockServer) throws IOException {
        var client = new UsersApiClient(mockServer.getUrl());
        var users = client.fetchUsers();
        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.isEmpty());
    }
}

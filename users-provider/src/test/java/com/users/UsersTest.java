package com.users;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.users.model.Users;
import com.users.repository.UsersRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("users_provider")
@PactBroker
public class UsersTest {
    @MockBean
    private UsersRepository usersRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("there are users")
    public void thereAreUsers() throws IOException {
        Mockito.when(usersRepository.getUsers()).thenReturn(getUsersFromFile("users.json"));
    }

    @State("there are no users")
    public void thereAreNoUsers() throws IOException {
        Mockito.when(usersRepository.getUsers()).thenReturn(getUsersFromFile("no_users.json"));
    }

    private List<Users> getUsersFromFile(String filename) throws IOException {
        URL resource = getClass().getClassLoader().getResource(filename);
        return objectMapper.readValue(resource, new TypeReference<>() {
        });
    }
}

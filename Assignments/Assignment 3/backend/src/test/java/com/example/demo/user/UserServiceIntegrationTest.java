package com.example.demo.user;

import com.example.demo.consultation.ConsultationRepository;
import com.example.demo.user.model.User;
import com.example.demo.user.dto.UserListDTO;
import com.example.demo.user.dto.UserMinimalDTO;
import com.example.demo.user.UserService;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @BeforeEach
    public void setup() {
        consultationRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    void findAll() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .name("name" + i)
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        for (int i = 0; i < nrUsers; i++) {
            //assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getUsername());
        }
    }

    @Test
    void create() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .build();
            users.add(user);
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getUsername());
        }
    }

    @Test
    void update() {
        int nrUsers = 10;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nrUsers; i++) {
            User user = User.builder()
                    .username("User " + i)
                    .password(UUID.randomUUID().toString())
                    .email("user" + i + "@gmail.com")
                    .name("name" + i)
                    .build();
            users.add(user);

            user.setEmail("new_user" + i +"@gmail.com");
            userRepository.save(user);
        }

        List<UserMinimalDTO> userMinimalDTOS = userService.allUsersMinimal();

        for (int i = 0; i < nrUsers; i++) {
            assertEquals(users.get(i).getId(), userMinimalDTOS.get(i).getId());
            assertEquals(users.get(i).getUsername(), userMinimalDTOS.get(i).getUsername());
        }
    }

    @Test
    void deleteAll() {
        userRepository.deleteAll();

        List<UserMinimalDTO> all = userService.allUsersMinimal();

        Assertions.assertEquals(0, all.size());
    }
}

package com.example.demo.user;
import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;


import com.example.demo.user.controller.UserController;
import com.example.demo.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.UrlMapping.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest extends BaseControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOS = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOS);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOS));
    }

    @Test
    void createUser() throws Exception {
        UserListDTO userDTO = UserListDTO.builder()
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .name(randomString())
                .build();
        when(userService.create(userDTO)).thenReturn(userDTO);
        ResultActions result = performPostWithRequestBody(USERS, userDTO);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTO));
    }

    @Test
    void updateUser() throws Exception {
        UserListDTO userDTO = UserListDTO.builder()
                .id(randomLong())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .name(randomString())
                .build();
        when(userService.update(userDTO.getId(),userDTO)).thenReturn(userDTO);
        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS + ENTITY, userDTO, userDTO.getId());
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTO));
    }

    @Test
    void deleteAll() throws Exception {
        doNothing().when(userService).deleteAll();

        ResultActions result = performDeleteAll(USERS);

        result.andExpect(status().isOk());
        verify(userService, times(1)).deleteAll();
    }

    @Test
    void deleteUser() throws Exception {
        long id = 1L;

        doNothing().when(userService).deleteById(id);

        ResultActions result = performDeleteWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk());
    }
}

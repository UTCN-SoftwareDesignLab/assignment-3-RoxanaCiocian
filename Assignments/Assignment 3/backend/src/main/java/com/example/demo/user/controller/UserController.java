package com.example.demo.user.controller;

import com.example.demo.user.UserService;
import com.example.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.ENTITY;
import static com.example.demo.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PostMapping
    public UserListDTO createUser(@RequestBody UserListDTO userListDTO){
        return userService.create(userListDTO);
    }

    @PutMapping(ENTITY)
    public UserListDTO updateUser(@PathVariable Long id, @RequestBody UserListDTO userListDTO){
        return userService.update(id, userListDTO);
    }

    @DeleteMapping
    public void deleteAll(){
        userService.deleteAll();
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }




}

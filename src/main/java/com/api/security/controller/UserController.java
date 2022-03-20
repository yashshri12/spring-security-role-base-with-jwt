package com.api.security.controller;

import com.api.security.entities.User;
import com.api.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/find")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> findAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAnyAuthority('STUDENT','ADMIN')")
    public User findUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }
}

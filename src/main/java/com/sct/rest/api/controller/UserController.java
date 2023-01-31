package com.sct.rest.api.controller;

import com.sct.rest.api.service.UserService;
import com.sct.rest.api.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/current")
    public UserDto getCurrent(){
        return userService.getCurrent();
    }
}
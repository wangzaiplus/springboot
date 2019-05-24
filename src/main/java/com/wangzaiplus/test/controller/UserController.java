package com.wangzaiplus.test.controller;

import com.wangzaiplus.test.pojo.User;
import com.wangzaiplus.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public String getAll() {
        List<User> users = userService.getAll();
        return users.toString();
    }

    @GetMapping("{id}")
    public String getOne(@PathVariable Integer id) {
        User user = userService.getOne(id);
        return user + "";
    }

    @PostMapping
    public String add(User user) {
        userService.add(user);
        return "nice";
    }

    @PutMapping
    public String update(User user) {
        userService.update(user);
        return "nice";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id) {
        userService.delete(id);
        return "nice";
    }

    @GetMapping("users/username_password")
    public String getByUsernameAndPassword(String username, String password) {
        List<User> users = userService.getByUsernameAndPassword(username, password);
        return users.toString();
    }

}

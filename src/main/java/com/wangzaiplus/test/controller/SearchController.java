package com.wangzaiplus.test.controller;

import com.google.common.collect.Lists;
import com.wangzaiplus.test.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping("users")
    public List<User> users() {
        User user = new User(1, "beigua", "beigua888");
        User user2 = new User(2, "beigua2", "beigua888");
        User user3 = new User(3, "beigua3", "beigua888");

        return Lists.newArrayList(user, user2, user3);
    }

}

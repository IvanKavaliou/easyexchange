package net.ivan.kavaliou.EasyExchange.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.EasyExchange.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @PostMapping("")
    public User registration(@RequestBody User user){
        return user;
    }

}

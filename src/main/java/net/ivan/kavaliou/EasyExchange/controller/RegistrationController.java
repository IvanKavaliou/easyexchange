package net.ivan.kavaliou.EasyExchange.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.EasyExchange.exceptions.ExsistException;
import net.ivan.kavaliou.EasyExchange.model.User;
import net.ivan.kavaliou.EasyExchange.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    @Autowired
    UsersRepository usersRepository;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public User registration(@RequestBody @Valid User user){
        if (usersRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ExsistException("error.user.exist");
        }
        return usersRepository.save(user);
    }

}

package net.ivan.kavaliou.EasyExchange.controller;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.EasyExchange.exceptions.ExsistException;
import net.ivan.kavaliou.EasyExchange.exceptions.NotFoundException;
import net.ivan.kavaliou.EasyExchange.exceptions.WrongPasswordException;
import net.ivan.kavaliou.EasyExchange.model.User;
import net.ivan.kavaliou.EasyExchange.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class RegistrationController {

    @Autowired
    UsersRepository usersRepository;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public User registration(@RequestBody @Valid User user){
        if (usersRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ExsistException("User with this email alredy exsist!");
        }
        return usersRepository.save(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User login(@RequestBody @Valid User user){
        Optional<User> logUser = usersRepository.findByEmail(user.getEmail());
        if (logUser.isPresent()){
            if (user.getPassword().equals(logUser.get().getPassword())){
                return logUser.get();
            }
            throw new WrongPasswordException("Wrong password!");
        }
        throw new NotFoundException("User with this email NOT exsist!");
    }

}

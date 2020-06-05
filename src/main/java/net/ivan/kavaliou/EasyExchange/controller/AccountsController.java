package net.ivan.kavaliou.EasyExchange.controller;

import net.ivan.kavaliou.EasyExchange.model.Account;
import net.ivan.kavaliou.EasyExchange.repository.AccountsRepository;
import net.ivan.kavaliou.EasyExchange.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountsController {

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    UsersService usersService;

    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){
        return accountsRepository.findAllByUser(usersService.getAuthUser());
    }

}

package net.ivan.kavaliou.EasyExchange.controller;

import net.ivan.kavaliou.EasyExchange.exceptions.ExsistException;
import net.ivan.kavaliou.EasyExchange.exceptions.NotFoundException;
import net.ivan.kavaliou.EasyExchange.model.Account;
import net.ivan.kavaliou.EasyExchange.repository.AccountsRepository;
import net.ivan.kavaliou.EasyExchange.service.UsersService;
import net.ivan.kavaliou.EasyExchange.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/accounts/add/{currency}")
    public Account getAllAccounts(@PathVariable CurrencyType currency){
        if(accountsRepository.findByUserAndCurrency(usersService.getAuthUser(), currency).isPresent()){
            throw new ExsistException("Account with this currency allredy exsist!");
        }
        Account account = Account.builder()
                                 .currency(currency)
                                 .user(usersService.getAuthUser())
                                 .value(BigDecimal.ZERO)
                                 .build();
        return accountsRepository.save(account);
    }

    @GetMapping("/accounts/{id}")
    public Account getOne(@PathVariable Integer id){
        Optional<Account> account = accountsRepository.findByUserAndId(usersService.getAuthUser(),id);
        if(account.isPresent()){
            return account.get();
        }
        throw new NotFoundException("Account not found!");
    }

    @DeleteMapping("/accounts/{currency}")
    void delete(@PathVariable CurrencyType currency) {
        Optional<Account> account = accountsRepository.findByUserAndCurrency(usersService.getAuthUser(), currency);
        if (account.isPresent()){
            accountsRepository.delete(account.get());
            return;
        }
        throw new NotFoundException("Account not found!");
    }

}

package net.ivan.kavaliou.EasyExchange.controller;

import net.ivan.kavaliou.EasyExchange.exceptions.NotFoundException;
import net.ivan.kavaliou.EasyExchange.model.Account;
import net.ivan.kavaliou.EasyExchange.model.Transaction;
import net.ivan.kavaliou.EasyExchange.model.dto.TopUp;
import net.ivan.kavaliou.EasyExchange.repository.AccountsRepository;
import net.ivan.kavaliou.EasyExchange.repository.TransactionsRepository;
import net.ivan.kavaliou.EasyExchange.service.UsersService;
import net.ivan.kavaliou.EasyExchange.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TransactionsController {

    @Autowired
    UsersService usersService;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @PostMapping("/topup")
    public Transaction topUp(@RequestBody @Valid TopUp topUp){
        Optional<Account> account = accountsRepository.findByUserAndCurrency(usersService.getAuthUser(), topUp.getCurrency());
        if (account.isPresent()){
            account.get().setValue(account.get().getValue().add(topUp.getValue()));
            Transaction transaction = Transaction.builder()
                                                 .account(account.get())
                                                 .transaction(TransactionType.TOP_UP)
                                                 .value(topUp.getValue())
                                                 .date(new Date())
                                                 .build();
            accountsRepository.save(account.get());
            return transactionsRepository.save(transaction);
        }
        throw new NotFoundException("Account not found!");
    }

    @GetMapping("/transactions")
    public List<Transaction> getAll(){
        return transactionsRepository.findAllByAccount_User(usersService.getAuthUser());
    }
}

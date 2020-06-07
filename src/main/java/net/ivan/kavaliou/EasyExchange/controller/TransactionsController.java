package net.ivan.kavaliou.EasyExchange.controller;

import net.ivan.kavaliou.EasyExchange.exceptions.ApiException;
import net.ivan.kavaliou.EasyExchange.exceptions.NotBeNegativeException;
import net.ivan.kavaliou.EasyExchange.exceptions.NotFoundException;
import net.ivan.kavaliou.EasyExchange.model.Account;
import net.ivan.kavaliou.EasyExchange.model.Transaction;
import net.ivan.kavaliou.EasyExchange.model.User;
import net.ivan.kavaliou.EasyExchange.model.dto.BuyDTO;
import net.ivan.kavaliou.EasyExchange.model.dto.TopUp;
import net.ivan.kavaliou.EasyExchange.repository.AccountsRepository;
import net.ivan.kavaliou.EasyExchange.repository.TransactionsRepository;
import net.ivan.kavaliou.EasyExchange.repository.UsersRepository;
import net.ivan.kavaliou.EasyExchange.service.UsersService;
import net.ivan.kavaliou.EasyExchange.utils.enums.CurrencyType;
import net.ivan.kavaliou.EasyExchange.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
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

    @Autowired
    UsersRepository usersRepository;

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

    @GetMapping("/balance")
    public BigDecimal getBalance(){
        return usersService.getAuthUser().getBalance();
    }

    @GetMapping("/balance/{value}")
    public BigDecimal getBalanceTopUp(@PathVariable BigDecimal value){
        User user = usersService.getAuthUser();
        user.setBalance(user.getBalance().add(value));
        return usersRepository.save(user).getBalance();
    }

    @PostMapping("/buy")
    @Transactional
    public Transaction topUp(@RequestBody @Valid BuyDTO buyDTO){
        User user = usersService.getAuthUser();
        if (buyDTO.getValue().signum() == 1){
            Optional<Account> account = accountsRepository.findByUserAndCurrency(user, buyDTO.getCurrency());
            if (account.isPresent()){
                BigDecimal buyValue = buyDTO.getValue().multiply(getAsk(buyDTO.getCurrency()));
                if (user.getBalance().compareTo(buyValue) == 0 || user.getBalance().compareTo(buyValue) == 1 ){
                    user.setBalance(user.getBalance().subtract(buyValue));
                    usersRepository.save(user);
                    account.get().setValue(account.get().getValue().add(buyValue));
                    accountsRepository.save(account.get());
                    return transactionsRepository.save(
                            Transaction.builder()
                            .account(account.get())
                            .transaction(TransactionType.BUY)
                            .date(new Date())
                            .value(buyDTO.getValue())
                                    .build());
                }
                throw new NotBeNegativeException("Sorry you can top up balance!");
            }
            throw  new NotFoundException("Account not found!");
        }
        throw new NotBeNegativeException("Must be > 0!");
    }

    public BigDecimal getBid(CurrencyType currencyType){
        return BigDecimal.valueOf(3.83);
    }

    public BigDecimal getAsk(CurrencyType currencyType){
        return BigDecimal.valueOf(3.93);
    }
}

package net.ivan.kavaliou.EasyExchange.controller;

import net.ivan.kavaliou.EasyExchange.exceptions.NotBeNegativeException;
import net.ivan.kavaliou.EasyExchange.exceptions.NotFoundException;
import net.ivan.kavaliou.EasyExchange.model.Account;
import net.ivan.kavaliou.EasyExchange.model.Transaction;
import net.ivan.kavaliou.EasyExchange.model.User;
import net.ivan.kavaliou.EasyExchange.model.dto.OperationDTO;
import net.ivan.kavaliou.EasyExchange.model.dto.TopUp;
import net.ivan.kavaliou.EasyExchange.repository.AccountsRepository;
import net.ivan.kavaliou.EasyExchange.repository.TransactionsRepository;
import net.ivan.kavaliou.EasyExchange.repository.UsersRepository;
import net.ivan.kavaliou.EasyExchange.service.RestService;
import net.ivan.kavaliou.EasyExchange.service.UsersService;
import net.ivan.kavaliou.EasyExchange.utils.enums.CurrencyType;
import net.ivan.kavaliou.EasyExchange.utils.enums.CurrencyValueType;
import net.ivan.kavaliou.EasyExchange.utils.enums.TransactionType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    RestService restService;

    private static final String URL_NBP_GET_RATE = "https://api.nbp.pl/api/exchangerates/rates/C/";


    @PostMapping("/topup")
    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TopUp topUp(@RequestBody @Valid TopUp topUp){
        if (topUp.getValue().compareTo(BigDecimal.ZERO) == 1){
            User user = usersService.getAuthUser();
            Transaction transaction = Transaction.builder()
                    .account(null)
                    .transaction(TransactionType.TOP_UP)
                    .value(topUp.getValue())
                    .user(user.getId())
                    .date(new Date())
                    .build();
            user.setBalance(user.getBalance().add(topUp.getValue()));
            usersRepository.save(user);
            transactionsRepository.save(transaction);
            topUp.setValue(user.getBalance());
            return topUp;
        }
        throw new NotBeNegativeException("Not be negative!!!");
    }

    @GetMapping("/transactions")
    public List<Transaction> getAll(){
        return transactionsRepository.findAllByUser(usersService.getAuthUser().getId())
                .stream()
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());
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


    @PostMapping("/sell")
    @Transactional
    public Transaction sell(@RequestBody @Valid OperationDTO sell){
        User user = usersService.getAuthUser();
        if (sell.getValue().signum() == 1){
            Optional<Account> account = accountsRepository.findByUserAndCurrency(user, sell.getCurrency());
            if (account.isPresent()){
                BigDecimal sellValue = sell.getValue().multiply(getBidAsk(sell.getCurrency(), CurrencyValueType.BID));
                if (account.get().getValue().compareTo(sell.getValue()) == 1 || account.get().getValue().compareTo(sell.getValue()) == 0){
                    account.get().setValue(account.get().getValue().subtract(sell.getValue()));
                    accountsRepository.save(account.get());
                    user.setBalance(user.getBalance().add(sellValue));
                    return transactionsRepository.save(
                            Transaction.builder()
                                    .account(account.get())
                                    .transaction(TransactionType.SELL)
                                    .date(new Date())
                                    .value(sell.getValue())
                                    .build());
                }
                throw new NotBeNegativeException("Sorry balance must be positive after operation!");
            }
            throw  new NotFoundException("Account not found!");
        }
        throw new NotBeNegativeException("Must be > 0!");
    }


    @PostMapping("/buy")
    @Transactional
    public Transaction topUp(@RequestBody @Valid OperationDTO buyDTO){
        User user = usersService.getAuthUser();
        if (buyDTO.getValue().signum() == 1){
            Optional<Account> account = accountsRepository.findByUserAndCurrency(user, buyDTO.getCurrency());
            if (account.isPresent()){
                BigDecimal buyValue = buyDTO.getValue().multiply(getBidAsk(buyDTO.getCurrency(), CurrencyValueType.ASK));
                if (user.getBalance().compareTo(buyValue) == 0 || user.getBalance().compareTo(buyValue) == 1 ){
                    user.setBalance(user.getBalance().subtract(buyValue));
                    usersRepository.save(user);
                    account.get().setValue(account.get().getValue().add(buyDTO.getValue()));
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

    public BigDecimal getBidAsk(CurrencyType currencyType, CurrencyValueType type)  {
        try {
            JSONObject jsonObject = new JSONObject(restService.getJSON(URL_NBP_GET_RATE+currencyType.name()));
            JSONArray arr = jsonObject.getJSONArray("rates");
            String result ="";
            for (int i = 0; i < arr.length(); i++)
            {
                result = arr.getJSONObject(i).getString(type.name().toLowerCase());
            }
            return BigDecimal.valueOf(Float.valueOf(result));
        } catch (JSONException e){
            throw new NotFoundException("Bid for "+currencyType.name()+" not found!");
        }
    }

}

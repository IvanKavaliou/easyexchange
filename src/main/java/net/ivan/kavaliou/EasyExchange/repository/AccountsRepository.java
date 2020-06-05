package net.ivan.kavaliou.EasyExchange.repository;

import net.ivan.kavaliou.EasyExchange.model.Account;
import net.ivan.kavaliou.EasyExchange.model.User;
import net.ivan.kavaliou.EasyExchange.utils.enums.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {
    public Optional<Account> findByUserAndId(User user,Integer id);
    public Optional<Account> findByUserAndCurrency(User user, CurrencyType currency);
    public List<Account> findAllByUser(User user);
}

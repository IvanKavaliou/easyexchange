package net.ivan.kavaliou.EasyExchange.repository;

import net.ivan.kavaliou.EasyExchange.model.Account;
import net.ivan.kavaliou.EasyExchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {
    public Optional<Account> findById(Integer id);
    public List<Account> findAllByUser(User user);
}

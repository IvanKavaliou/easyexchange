package net.ivan.kavaliou.EasyExchange.repository;

import net.ivan.kavaliou.EasyExchange.model.Transaction;
import net.ivan.kavaliou.EasyExchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {
    public Optional<Transaction> findById(Integer id);
    public List<Transaction> findAllByAccount_User(User user);
}

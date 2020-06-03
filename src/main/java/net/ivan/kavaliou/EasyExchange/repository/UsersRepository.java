package net.ivan.kavaliou.EasyExchange.repository;

import net.ivan.kavaliou.EasyExchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findById(Integer id);
}

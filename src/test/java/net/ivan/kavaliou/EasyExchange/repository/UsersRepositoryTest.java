package net.ivan.kavaliou.EasyExchange.repository;

import lombok.extern.slf4j.Slf4j;
import net.ivan.kavaliou.EasyExchange.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Sql(scripts = "classpath:db/init.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UsersRepositoryTest {

    @Autowired
    UsersRepository repository;

    private static final Integer ID_USER = 100000;

    @Test
    public void findByEmailTest(){
        Optional<User> user = repository.findByEmail("user@user.com");
        if (user.isPresent()){
           System.out.println("Get test user: " + user.get().toString());
        }
        assertTrue(user.isPresent());
    }

    @Test
    void findById() {
        assertTrue(repository.findById(ID_USER).isPresent());
    }
}
package net.ivan.kavaliou.EasyExchange.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountsRepositoryTest {

    @Autowired
    private AccountsRepository repository;

    private static final Integer ACCOUNT_ID = 100002;

    @Test
    void findById() {
        assertTrue(repository.findById(ACCOUNT_ID).isPresent());
    }
}
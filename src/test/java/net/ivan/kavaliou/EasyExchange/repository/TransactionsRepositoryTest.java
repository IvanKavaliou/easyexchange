package net.ivan.kavaliou.EasyExchange.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionsRepositoryTest {

    @Autowired
    TransactionsRepository repository;

    private static final Integer TRANSACTION_ID = 100005;

    @Test
    void findById() {
        assertTrue(repository.findById(TRANSACTION_ID).isPresent());
    }
}
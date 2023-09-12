package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void existAccounts(){

        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));

    }

    @Test
    public void existsAccountByNumber(){

        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, hasItem(hasProperty("number", is("VIN001"))));

    }


    @Test
    void findByNumber() {
    }
}
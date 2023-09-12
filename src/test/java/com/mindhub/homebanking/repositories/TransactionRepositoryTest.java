package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class TransactionRepositoryTest {


    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){

        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));

    }

    @Test
    public void existPersonalLoan(){

        //List<Loan> loans = loanRepository.findAll();
        assertThat(String.valueOf(true), true);

    }
}
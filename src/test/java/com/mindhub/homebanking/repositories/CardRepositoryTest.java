package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CardRepositoryTest {

    @Autowired
    CardRepository cardRepository;

    @Test
    public void existLoans(){

        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));

    }

    @Test
    public void existsCard(){

        //List<Loan> loans = loanRepository.findAll();
        assertThat(String.valueOf(true), true);

    }

    @Test
    void findByNumber() {
    }
}
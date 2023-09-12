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

        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("number", is("2234-6745-5582-0699"))));

    }

    @Test
    void findByNumber() {

    }
}
package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientRepositoryTest {
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void existClients(){

        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));

    }
    @Test
    void findByEmail() {
    }

    @Test
    public void existsAccountByNumber(){

        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("email", is("melba@mindhub.com"))));

    }

}
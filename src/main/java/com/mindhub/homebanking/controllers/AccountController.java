package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    private Random random = new Random();

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccountDTO(id);
    }

    @PostMapping("/clients/current/accounts/delete")
    public ResponseEntity<Object> changeAccountState (@RequestParam String number,
                                                     Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());
        Account accountToChange = accountService.findByNumber(number);

        if(client.getAccounts().stream().noneMatch( account -> account.getNumber().equals(accountToChange.getNumber()))){
            return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
        }

        Set<Transaction> accountTransactions = accountToChange.getTransactions();
        for (Transaction transaction: accountTransactions) {
            transaction.changeState();
        }

        accountToChange.changeState();

        transactionService.saveAllTransactionsSet(accountTransactions);
        accountService.saveAccount(accountToChange);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);


    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());

        if (client.getAccounts().size() < 3){
            String number;
            Optional<Account> accountRecovered;

            do{
                number = "VIN" + random.nextInt(100000000);
                accountRecovered = Optional.ofNullable(accountService.findByNumber(number));
            }while(accountRecovered.isPresent());


            double balance = 0.0;
            Account account = new Account(number, LocalDate.now(), balance);
            client.addAccount(account);
            accountService.saveAccount(account);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());

        return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }


}

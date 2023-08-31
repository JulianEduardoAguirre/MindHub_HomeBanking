package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping("/loans")
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(@RequestBody int loanId,
                                             @RequestBody int payments,
                                             @RequestBody String toAccountNumber,
                                             @RequestBody int amount,
                                             Authentication auth) {

        if(payments == 0 || toAccountNumber.isEmpty() || amount == 0 ) {
            return new ResponseEntity<>("Invalid data", HttpStatus.FORBIDDEN);
        }

        List<Loan> listOfLoans = loanRepository.findAll();

        if (listOfLoans.stream().noneMatch(loan -> loan.getId() == loanId)) {
            return new ResponseEntity<>("Loan type doesn't exist", HttpStatus.FORBIDDEN);
        }

        Loan loanSelected = loanRepository.getById(loanId);
        if ( amount > loanSelected.getMaxAmount()){
            return new ResponseEntity<>("Cannot exceed loan max amount", HttpStatus.FORBIDDEN);
        }

        if (loanSelected.getPayments().stream().noneMatch( loanPayments -> loanPayments == payments)) {
            return new ResponseEntity<>("Must select a valid number of payments", HttpStatus.FORBIDDEN);
        }

        //Account existing and being property of user, yet to validate
        Account destinyAccount = accountRepository.findByNumber(toAccountNumber);
        //Apart from that...

        ClientLoan requestedLoan = new ClientLoan((int)(amount * 1.2), payments);
        Transaction creditLoanTransaction = new Transaction(TransactionType.CREDIT, amount, "" + loanSelected.getName() + "- loan approved", LocalDateTime.now());
        destinyAccount.addAmount(amount);

        Client actualClient = clientRepository.findByEmail(auth.getName());
        actualClient.addClientLoan(requestedLoan);
        loanSelected.addClientLoan(requestedLoan);
        clientLoanRepository.save(requestedLoan);
        transactionRepository.save(creditLoanTransaction);
        loanRepository.save(loanSelected);
        clientRepository.save(actualClient);



        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

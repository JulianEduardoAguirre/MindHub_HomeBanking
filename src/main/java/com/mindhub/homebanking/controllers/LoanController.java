package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationoDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
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
import java.util.stream.Collectors;

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
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationoDTO loanApplicationoDTO,
                                             Authentication auth) {

        if(loanApplicationoDTO.getPayments() == 0 || loanApplicationoDTO.getToAccountNumber().isEmpty() || loanApplicationoDTO.getAmount() == 0 ) {
            return new ResponseEntity<>("Invalid data", HttpStatus.FORBIDDEN);
        }

        List<Loan> listOfLoans = loanRepository.findAll();

        if (listOfLoans.stream().noneMatch(loan -> loan.getId() == loanApplicationoDTO.getLoanId())) {
            return new ResponseEntity<>("Loan type doesn't exist", HttpStatus.FORBIDDEN);
        }

        Loan loanSelected = loanRepository.getById(loanApplicationoDTO.getLoanId());
        if ( loanApplicationoDTO.getAmount() > loanSelected.getMaxAmount()){
            return new ResponseEntity<>("Cannot exceed loan max amount", HttpStatus.FORBIDDEN);
        }

        if (loanSelected.getPayments().stream().noneMatch( loanPayments -> loanPayments == loanApplicationoDTO.getPayments())) {
            return new ResponseEntity<>("Must select a valid number of payments", HttpStatus.FORBIDDEN);
        }

        //Account existing and being property of user, yet to validate
        Account destinyAccount = accountRepository.findByNumber(loanApplicationoDTO.getToAccountNumber());
        //Apart from that...

        ClientLoan requestedLoan = new ClientLoan((int)(loanApplicationoDTO.getAmount() * 1.2), loanApplicationoDTO.getPayments());
        Transaction creditLoanTransaction = new Transaction(TransactionType.CREDIT, loanApplicationoDTO.getAmount(), "" + loanSelected.getName() + " - loan approved", LocalDateTime.now());
        destinyAccount.addAmount(loanApplicationoDTO.getAmount());
        destinyAccount.addTransaction(creditLoanTransaction);

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

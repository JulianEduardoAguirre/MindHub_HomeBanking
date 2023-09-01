package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationoDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.implement.*;
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
    private LoanServiceImplement loanService;

    @Autowired
    private ClientLoanServiceImplement clientLoanService;

    @Autowired
    private AccountServiceImplement accountService;

    @Autowired
    private TransactionServiceImplement transactionService;

    @Autowired
    private ClientServiceImplement clientService;


    @RequestMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoansDTO();
    }

    @Transactional
    @RequestMapping(path = "/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationoDTO loanApplicationoDTO,
                                             Authentication auth) {

        if(loanApplicationoDTO.getPayments() == 0 || loanApplicationoDTO.getToAccountNumber().isEmpty() || loanApplicationoDTO.getAmount() == 0 ) {
            return new ResponseEntity<>("Invalid data", HttpStatus.FORBIDDEN);
        }

        List<Loan> listOfLoans = loanService.getLoans();

        if (listOfLoans.stream().noneMatch(loan -> loan.getId() == loanApplicationoDTO.getLoanId())) {
            return new ResponseEntity<>("Loan type doesn't exist", HttpStatus.FORBIDDEN);
        }

        Loan loanSelected = loanService.findById(loanApplicationoDTO.getLoanId());
        if ( loanApplicationoDTO.getAmount() > loanSelected.getMaxAmount()){
            return new ResponseEntity<>("Cannot exceed loan max amount", HttpStatus.FORBIDDEN);
        }

        if (loanSelected.getPayments().stream().noneMatch( loanPayments -> loanPayments == loanApplicationoDTO.getPayments())) {
            return new ResponseEntity<>("Must select a valid number of payments", HttpStatus.FORBIDDEN);
        }

        //Account existing and being property of user, yet to validate
        Account destinyAccount = accountService.findByNumber(loanApplicationoDTO.getToAccountNumber());
        //Apart from that...

        ClientLoan requestedLoan = new ClientLoan((int)(loanApplicationoDTO.getAmount() * 1.2), loanApplicationoDTO.getPayments());
        Transaction creditLoanTransaction = new Transaction(TransactionType.CREDIT, loanApplicationoDTO.getAmount(), "" + loanSelected.getName() + " - loan approved", LocalDateTime.now());
        destinyAccount.addAmount(loanApplicationoDTO.getAmount());
        destinyAccount.addTransaction(creditLoanTransaction);

        Client actualClient = clientService.findByEmail(auth.getName());
        actualClient.addClientLoan(requestedLoan);
        loanSelected.addClientLoan(requestedLoan);
        clientLoanService.saveClientLoan(requestedLoan);
        transactionService.saveTransaction(creditLoanTransaction);
        loanService.saveLoan(loanSelected);
        clientService.saveClient(actualClient);



        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

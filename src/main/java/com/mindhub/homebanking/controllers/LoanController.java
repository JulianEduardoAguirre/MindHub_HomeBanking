package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private LoanRepository loanRepository;


    @RequestMapping("/loans")
    public List<Loan> getLoans(){
        return loanRepository.findAll();
    }
}

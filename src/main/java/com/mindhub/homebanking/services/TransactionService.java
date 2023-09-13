package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;
import java.util.Set;

public interface TransactionService {

    void saveTransaction(Transaction transaction);

    List<TransactionDTO> getTransactionsDTO();

    List<Transaction> getTransactions();

    void saveAllTransactions(List<Transaction> transactions);
    void saveAllTransactionsSet(Set<Transaction> transactions);
}

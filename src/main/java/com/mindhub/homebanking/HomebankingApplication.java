package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) -> {
			//Creating a couple of transactions
			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 750, "Credit from Paul", LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.DEBIT, -500, "Rent payment", LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 100, "Freelance payment", LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.DEBIT, -150, "Bank loan", LocalDateTime.now());
			Transaction transaction5 = new Transaction(TransactionType.CREDIT, 250, "Xmas present from parents", LocalDateTime.now());

			// Create a couple of accounts
			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			Account account3 = new Account("VIN003", LocalDate.now().plusDays(10), 10000);
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(5), 15000);

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Martín", "Palermo", "martin@pescador.com");
			Client client3 = new Client("Alberto", "Einstenio", "albert_einstein_1879@lifebeforeinternet.com");


			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			account1.addTransaction(transaction1);
			account2.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account3.addTransaction(transaction4);
			account4.addTransaction(transaction5);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);



		};
	}
}

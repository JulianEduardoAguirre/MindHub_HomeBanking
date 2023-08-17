package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository){
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

			//Creating clients (Melba, MP9 & our Albert Einstein)
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", "melba123");
			Client client2 = new Client("Martín", "Palermo", "martin@pescador.com", "martin123");
			Client client3 = new Client("Alberto", "Einstenio", "albert_einstein_1879@lifebeforeinternet.com", "alberto123");

			//Creating three types for Loan
			Loan loan1 = new Loan("Hipotecario", 500000, List.of(12, 24, 36, 48, 60));
			Loan loan2 = new Loan("Personal", 100000, List.of(6, 12, 24));
			Loan loan3 = new Loan("Automotriz", 300000, List.of(6, 12, 24, 36));

			//Creating 2 loans for Melba and 2 for MP9
			ClientLoan clientLoan1 = new ClientLoan(400000, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000, 12, client1, loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000, 24, client2, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000, 36, client2, loan3);

			//Creating a couple of Cards for Melba
			Card card1 = new Card("3325-6745-7876-4445", 990, LocalDateTime.now(), LocalDateTime.now().plusYears(5), CardType.DEBIT, CardColor.GOLD);
			Card card2 = new Card("2234-6745-552-7888", 750, LocalDateTime.now(), LocalDateTime.now().plusYears(5), CardType.CREDIT, CardColor.TITANIUM);
			//Creating two cards for Martín
			Card card3 = new Card("2234-6745-558-0699", 189, LocalDateTime.now(), LocalDateTime.now().plusYears(4), CardType.CREDIT, CardColor.TITANIUM);
			Card card4 = new Card("3325-6745-0159-6548", 639, LocalDateTime.now(), LocalDateTime.now().plusYears(4), CardType.DEBIT, CardColor.SILVER);


			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);


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

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);
			client2.addCard(card4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			cardRepository.save(card4);



		};
	}
}

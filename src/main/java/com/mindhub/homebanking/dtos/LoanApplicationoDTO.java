package com.mindhub.homebanking.dtos;

public class LoanApplicationoDTO {

    private int loanId;
    private int amount;
    private int payments;
    private String toAccountNumber;

    public LoanApplicationoDTO() {
    }

    public LoanApplicationoDTO(int loanId, int amount, int payments, String toAccountNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    public int getLoanId() {
        return loanId;
    }

    public int getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}

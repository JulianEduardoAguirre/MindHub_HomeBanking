package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CardDTO {
    private long id;
    private String number;
    private int cvv;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;
    private CardType type;
    private CardColor color;

    private boolean state;  //Enabled or disabled by client

    private boolean notExpired;    //If it is expired

    private String cardHolder;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.type = card.getType();
        this.color = card.getColor();
        this.state = card.getState();
        this.notExpired = LocalDateTime.now().isBefore(this.thruDate);
        this.cardHolder = card.getCardholder().getFirstName() + " " + card.getCardholder().getLastName();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public boolean getState() { return state; }
    public boolean notExpired() {return notExpired; }
    public String getCardHolder() {
        return cardHolder;
    }
}

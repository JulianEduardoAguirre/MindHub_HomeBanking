package com.mindhub.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardUtilsTest {

    @Test
    public void cardNumberIsCreated() {

        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber, is(not(emptyOrNullString())));

    }

    @Test
    public void cardNumberIsValid() {
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber.length(), is(equalTo(19)));
    }

    @Test
    void cvvIsCreated() {
        int cvv = CardUtils.getCVV();
        assertThat(cvv, is(lessThan(1000)));
        assertThat(cvv, is(greaterThan(0)));
    }
}
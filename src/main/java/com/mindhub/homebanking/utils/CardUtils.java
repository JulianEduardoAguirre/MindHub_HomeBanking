package com.mindhub.homebanking.utils;

import java.util.Random;

public final class CardUtils {

    private static final Random random = new Random();
    private CardUtils(){}

    public static String getCardNumber() {
        return  random.nextInt(10000) + "-" +
                random.nextInt(10000) + "-" +
                random.nextInt(10000) + "-" +
                random.nextInt(10000);
    }

    public static int getCVV () {
        return random.nextInt(1000);
    }

}

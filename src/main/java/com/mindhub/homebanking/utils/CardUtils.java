package com.mindhub.homebanking.utils;

import java.util.Random;

public final class CardUtils {

    private static final Random random = new Random();
    private CardUtils(){}

    private static String generateFixedLenghtNumber(int lenght) {
        return String.format("%0"+ lenght+ "d", random.nextInt((int) Math.pow(10, lenght)));
    }


    public static String getCardNumber() {

        return generateFixedLenghtNumber(4) + "-" +
                generateFixedLenghtNumber(4) + "-" +
                generateFixedLenghtNumber(4) + "-" +
                generateFixedLenghtNumber(4);

    }

    public static int getCVV () {

        return Integer.parseInt(generateFixedLenghtNumber(3));

    }

}

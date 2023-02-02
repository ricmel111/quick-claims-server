package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.data.GeneratedNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UniqueNumberGenerator {
    private static final String PREFIX = "011";
    private static final int LENGTH = 10;

    @Autowired
    private GeneratedNumberRepository generatedNumberRepository;

    public String generateUniqueNumber() {
        String uniqueNumber = PREFIX + generateRandomNumber();
        // check if the generated number already exists in the database
        // and repeat the process until a unique number is found
        // you can use a repository class to perform database operations
        while (generatedNumberRepository.existsByNumber(uniqueNumber)) {
            uniqueNumber = PREFIX + generateRandomNumber();
        }
        return uniqueNumber;
    }

    private String generateRandomNumber() {
        Random random = new Random();
        return String.format("%0" + (LENGTH - PREFIX.length()) + "d", random.nextInt((int) Math.pow(10, LENGTH - PREFIX.length())));
    }

    private boolean numberExistsInDatabase(String number) {
        // check if the number exists in the database
        // you can use a repository class to perform database operations
        return false;
    }
}
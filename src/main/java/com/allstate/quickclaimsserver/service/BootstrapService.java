package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class BootstrapService {

    @Autowired //find an instance of this repository - Dependency Injection
    private ClaimRepository claimRepository;


    @PostConstruct //run this method on start up
    public void setUpInitialData() {
        int numberOfClaims = claimRepository.findAll().size();
        if (numberOfClaims == 0) {
            Claim newClaim = new Claim("O","1111111111", "222222222", "Property", "", "", "", "", "", "John", "Doe", "date", Date.valueOf("2023-01-01"), 123.45, "a long reason", "a long description", LocalDate.now(), "further details here");
            Claim savedClaim = claimRepository.save(newClaim);
            System.out.println("Claim saved with an id of " + savedClaim.getId());
        }
    }
}

package com.allstate.quickclaimsserver.control;

import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import com.allstate.quickclaimsserver.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/claim")
@CrossOrigin
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @GetMapping()
    public List<Claim> getAllClaims(@RequestParam(value="policy-type", required = false) String policyType,
                                    @RequestParam(value="claim-status", required = false) String claimStatus) {
        if (policyType != null) {
            return claimService.getByPolicyType(policyType);
        }
        else if (claimStatus != null) {
            return claimService.getByClaimStatus(claimStatus);
        }
        else {
            return claimService.getAllClaims();
        }
    }

    @GetMapping("/{claimId}")
    public Claim findById(@PathVariable("claimId") Integer id) throws ClaimNotFoundException {
        return claimService.getById(id);
    }

    @GetMapping("/nbr/{claimNumber}")
    public Claim getByClaimNumber(@PathVariable("claimNumber") String claimNumber)  throws ClaimNotFoundException {
        return claimService.getByClaimNumber(claimNumber);
    }

    @GetMapping("/init")
    public String setUpData() {
        Claim claim1 = new Claim("O","1234", "1234", "Property", "", "", "", "", "", "Phil", "Foden", "date", Date.valueOf("2023-01-01"), 123.45,"a long reason","a long description", LocalDate.now(),"further details here");
        Claim claim2 = new Claim("P","4567", "1234", "Pet", "", "", "", "", "", "John", "Wick", "date", Date.valueOf("2023-01-01"), 123.45,"a long reason","a long description", LocalDate.now(),"further details here");
        Claim claim3 = new Claim("C","8901", "1234", "Auto", "", "", "", "", "", "David", "Wilson", "date", Date.valueOf("2023-01-01"), 123.45,"a long reason","a long description", LocalDate.now(),"further details here");
        claimService.saveClaim(claim1);
        claimService.saveClaim(claim2);
        claimService.saveClaim(claim3);
        return "ok";
    }
}

package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Override
    public void saveClaim(Claim claim) {
        claimRepository.save(claim);
    }

    @Override
    public List<Claim> getAllClaims() {
        List<Claim> claims = claimRepository.findAll();
        System.out.println("Total claims found = " + claims.size());
        return claims;
    }

    @Override
    public Claim getById(Integer id) throws ClaimNotFoundException {
        Optional<Claim> optionalClaim = claimRepository.findById(id);
        if (optionalClaim.isPresent()) {
            return optionalClaim.get();
        }
        else {
            throw new ClaimNotFoundException("There is no claim with id " + id);
        }
    }

    @Override
    public List<Claim> getByPolicyType(String policyType) {
        List<Claim> claims = claimRepository.findAllByPolicyType(policyType);
        System.out.println("Total " + policyType + " claims found = " + claims.size());
        return claims;
    }

    @Override
    public List<Claim> getByClaimStatus(String claimStatus) {
        List<Claim> claims = claimRepository.findAllByClaimStatus(claimStatus);
        System.out.println("Total claims found of status '" + claimStatus + "' = " + claims.size());
        return claims;
    }

    @Override
    public Claim getByClaimNumber(String claimNumber) throws ClaimNotFoundException {
        Optional<Claim> optionalClaim = Optional.ofNullable(claimRepository.getByClaimNumber(claimNumber));
        if (optionalClaim.isPresent()) {
            System.out.println("Found claim number " + claimNumber);
            return optionalClaim.get();
        }
        else {
            throw new ClaimNotFoundException("There is no claim number " + claimNumber);
        }
    }
}

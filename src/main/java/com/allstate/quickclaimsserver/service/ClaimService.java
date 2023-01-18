package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;

import java.util.List;

public interface ClaimService {

    public void saveClaim(Claim claim);

    public List<Claim> getAllClaims();

    public Claim getById(Integer id) throws ClaimNotFoundException;

    public List<Claim> getByPolicyType(String policyType);

    public List<Claim> getByClaimStatus(String claimStatus);

    public Claim getByClaimNumber(String claimNumber) throws ClaimNotFoundException;
}

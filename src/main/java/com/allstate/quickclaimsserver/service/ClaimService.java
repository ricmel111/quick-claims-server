package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.domain.Task;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import com.allstate.quickclaimsserver.exceptions.MissingFieldException;

import java.util.List;
import java.util.Map;

public interface ClaimService {

    public List<Claim> getAllClaims();

    public Claim getById(Integer id) throws ClaimNotFoundException;

    public List<Claim> getByPolicyType(String policyType);

    public List<Claim> getByClaimNumber(String claimNumber);

    public List<Claim> getByPolicyNumber(String policyNumber);

    public List<Claim> getByLastName(String lastName);

    public List<Claim> getByClaimStatus(List<String> claimStatus);

    public Claim saveClaim(Claim claim) throws MissingFieldException;

    public Task saveTask(Task task) throws ClaimNotFoundException;

    public Claim updateClaim(Integer id, Map<String, Object> fields) throws ClaimNotFoundException;

    //public Claim getByClaimNumber(String claimNumber) throws ClaimNotFoundException;
}

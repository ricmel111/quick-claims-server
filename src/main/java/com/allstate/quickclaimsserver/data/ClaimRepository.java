package com.allstate.quickclaimsserver.data;

import com.allstate.quickclaimsserver.domain.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // tells spring 1. it's responsible for creating instance of class 2. spring will instantiate the class and create the implementation
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    public List<Claim> findAllByPolicyType(String policyType);

    public List<Claim> findAllByClaimStatus(String claimStatus);

    public Claim getByClaimNumber(String claimNumber);

}

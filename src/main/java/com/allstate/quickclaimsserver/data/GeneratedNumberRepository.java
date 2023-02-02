package com.allstate.quickclaimsserver.data;

import com.allstate.quickclaimsserver.domain.GeneratedNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneratedNumberRepository extends JpaRepository<GeneratedNumber, String> {
    boolean existsByNumber(String number);
}

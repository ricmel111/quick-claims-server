package com.allstate.quickclaimsserver;

import com.allstate.quickclaimsserver.domain.Claim;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DomainClassTests {

    @Test
    public void test2ClaimsWithTheSameIdAreEqual() {
        Claim claim1 = new Claim();
        Claim claim2 = new Claim();
        claim1.setId(123);
        claim2.setId(123);
        assertEquals(claim1, claim2);
    }

    @Test
    public void testClaimsWithDifferentIdsAreNotEqual() {
        Claim claim1 = new Claim();
        Claim claim2 = new Claim();
        claim1.setId(123);
        claim2.setId(456);
        assertNotEquals(claim1, claim2);
    }


}

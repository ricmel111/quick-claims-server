package com.allstate.quickclaimsserver;
import static org.junit.jupiter.api.Assertions.*;

import com.allstate.quickclaimsserver.domain.Claim;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuickClaimsServerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testAValid9DigitPolicyNumber() {
		Claim claim = new Claim();
	}

}

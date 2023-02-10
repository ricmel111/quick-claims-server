package com.allstate.quickclaimsserver;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.data.TaskRepository;
import com.allstate.quickclaimsserver.data.UserRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.exceptions.MissingFieldException;
import com.allstate.quickclaimsserver.service.BootstrapService;
import com.allstate.quickclaimsserver.service.ClaimService;
import com.allstate.quickclaimsserver.domain.Note;
import com.allstate.quickclaimsserver.domain.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ClaimServiceTests {

    //valid case, invalid case, edge case, exception
    @Autowired
    private ClaimService claimService;
    @MockBean
    private ClaimRepository claimRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private NoteRepository noteRepository;

    @Test
    public void testGetAllClaims() {
        List<Claim> claims = new ArrayList<>();
        claims.add(new Claim(null,"A","7890", "Pet", "", "", "", "", "Rabbit", "Mini Lop", "Emily", "Brown", Date.valueOf("2023-01-01"), 167.43,"Behavioral issues, such as aggression or separation anxiety.","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>()));
        claims.add(new Claim(null,"A","2345", "Property", "567 Main Street, Chicago, IL, 20982", "", "", "", "", "", "Katie", "Smith", Date.valueOf("2023-01-01"), 456.00,"Burglary or theft","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>()));
        Mockito.when(claimRepository.findAll()).thenReturn(claims);
        assertEquals(2, claims.size());
    }

    @Test
    public void testSaveClaim() throws MissingFieldException {
        Claim claim = new Claim();
        claim.setPolicyNumber("123456");
        claim.setPolicyType("Property");
        claim.setPropertyAddress("123 Main St");
        claim.setFirstName("John");
        claim.setLastName("Doe");
        claim.setEstimatedAmount(1000.0);
        claim.setClaimReason("Accident");
        claim.setIncidentDescription("Some description");

        Claim savedClaim = new Claim();
        savedClaim.setId(1);
        savedClaim.setPolicyNumber("123456");
        savedClaim.setPolicyType("Property");
        savedClaim.setPropertyAddress("123 Main St");
        savedClaim.setFirstName("John");
        savedClaim.setLastName("Doe");
        savedClaim.setEstimatedAmount(1000.0);
        savedClaim.setClaimReason("Accident");
        savedClaim.setIncidentDescription("Some description");

        Mockito.when(claimRepository.save(claim)).thenReturn(savedClaim);

        Claim result = claimService.saveClaim(claim);

        assertNotNull(result);
        assertEquals("123456", result.getPolicyNumber());
    }

    @Test
    public void testSavePolicyNumberWithLessThan9Digits() throws MissingFieldException {
        Claim claim = new Claim();
        claim.setPolicyNumber("123456");

        Claim savedClaim = new Claim();
        savedClaim.setId(1);
        savedClaim.setPolicyNumber("123456");

        Mockito.when(claimRepository.save(claim)).thenReturn(savedClaim);

        Claim result = claimService.saveClaim(claim);

        assertNotNull(result);
        assertEquals("123456", result.getPolicyNumber());
    }
}

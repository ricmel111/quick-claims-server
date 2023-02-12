package com.allstate.quickclaimsserver;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.data.TaskRepository;
import com.allstate.quickclaimsserver.data.UserRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.exceptions.ArchivedException;
import com.allstate.quickclaimsserver.exceptions.InvalidFieldException;
import com.allstate.quickclaimsserver.exceptions.MissingFieldException;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void testSaveClaimSuccess() throws MissingFieldException, InvalidFieldException {
        Claim claim = new Claim(null,"A","123456789", "Pet", "", "", "", "", "Rabbit", "Mini Lop", "Emily", "Brown", Date.valueOf("2023-01-01"), 167.43,"Behavioral issues, such as aggression or separation anxiety.","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>());
        Mockito.when(claimRepository.save(claim)).thenReturn(claim);
        Claim savedClaim = claimService.saveClaim(claim);
        assertEquals(claim, savedClaim);
    }

    @Test
    public void testValid9DigitPolicyNumber() throws MissingFieldException, InvalidFieldException {
        Claim claim = new Claim(null,"A","123456789", "Pet", "", "", "", "", "Rabbit", "Mini Lop", "Emily", "Brown", Date.valueOf("2023-01-01"), 167.43,"Behavioral issues, such as aggression or separation anxiety.","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>());
        Mockito.when(claimRepository.save(claim)).thenReturn(claim);
        Claim savedClaim = claimService.saveClaim(claim);
        assertEquals(claim.getPolicyNumber(), savedClaim.getPolicyNumber());
    }

    @Test
    public void test9DigitPolicyNumbersAreNotAllowed() {
        assertThrows(InvalidFieldException.class, () -> {
            Claim claim = new Claim(null, "A", "12345678", "Pet", "", "", "", "", "Rabbit", "Mini Lop", "Emily", "Brown", Date.valueOf("2023-01-01"), 167.43, "Behavioral issues, such as aggression or separation anxiety.", "a long description", Date.valueOf("2023-01-01"), "further details here", 0.00, new ArrayList<Task>(), new ArrayList<Note>());
            claimService.saveClaim(claim);
        });
    }

    @Test
    public void testEmptyRequiredFieldsAreNotAllowed() {
        assertThrows(MissingFieldException.class, () -> {
            Claim claim = new Claim(null, "", "123456789", "Pet", "", "", "", "", "Rabbit", "Mini Lop", "Emily", "Brown", Date.valueOf("2023-01-01"), 167.43, "Behavioral issues, such as aggression or separation anxiety.", "a long description", Date.valueOf("2023-01-01"), "further details here", 0.00, new ArrayList<Task>(), new ArrayList<Note>());
            claimService.saveClaim(claim);
        });
    }

    @Test
    public void testGetAllClaims() {
        List<Claim> claims = new ArrayList<>();
        claims.add(new Claim(null,"A","7890", "Pet", "", "", "", "", "Rabbit", "Mini Lop", "Emily", "Brown", Date.valueOf("2023-01-01"), 167.43,"Behavioral issues, such as aggression or separation anxiety.","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>()));
        claims.add(new Claim(null,"A","2345", "Property", "567 Main Street, Chicago, IL, 20982", "", "", "", "", "", "Katie", "Smith", Date.valueOf("2023-01-01"), 456.00,"Burglary or theft","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>()));
        Mockito.when(claimRepository.findAll()).thenReturn(claims);
        assertEquals(2, claims.size());
    }

    @Test
    public void testValidPropertyAddressWhenPolicyTypeIsProperty() throws MissingFieldException, InvalidFieldException {
        Claim claim = new Claim(null,"A","123456789", "Property", "123 Main Street, Chicago", "", "", "", "", "", "Emily", "Brown", Date.valueOf("2023-01-01"), 167.43,"Behavioral issues, such as aggression or separation anxiety.","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>());
        Mockito.when(claimRepository.save(claim)).thenReturn(claim);
        Claim savedClaim = claimService.saveClaim(claim);
        assertEquals(claim, savedClaim);
    }

    @Test
    public void testMissingPropertyAddressWhenPolicyTypeIsProperty() throws MissingFieldException, InvalidFieldException {
        assertThrows(MissingFieldException.class, () -> {
            Claim claim = new Claim(null, "", "123456789", "Property", "", "", "", "", "", "", "Emily", "Brown", Date.valueOf("2023-01-01"), 167.43, "Behavioral issues, such as aggression or separation anxiety.", "a long description", Date.valueOf("2023-01-01"), "further details here", 0.00, new ArrayList<Task>(), new ArrayList<Note>());
            claimService.saveClaim(claim);
        });
    }

}

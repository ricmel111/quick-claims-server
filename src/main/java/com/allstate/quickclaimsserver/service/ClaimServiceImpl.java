package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.data.TaskRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.domain.Note;
import com.allstate.quickclaimsserver.domain.Task;
import com.allstate.quickclaimsserver.exceptions.ArchivedException;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import com.allstate.quickclaimsserver.exceptions.InvalidFieldException;
import com.allstate.quickclaimsserver.exceptions.MissingFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Claim saveClaim(Claim claim) throws MissingFieldException {

        if (claim.getPolicyNumber() == null || claim.getPolicyNumber().isEmpty()) {
            System.out.println(claim.getPolicyNumber());
            throw new MissingFieldException("Policy Number cannot be empty");
        }
        if ("Property".equals(claim.getPolicyType())) {
            claim.setTypeOfAnimal("");
            claim.setBreedOfAnimal("");
            claim.setVehicleMake("");
            claim.setVehicleModel("");
            claim.setManufactureYear("");
            if (claim.getPropertyAddress() == null || claim.getPropertyAddress().isEmpty()) {
                throw new MissingFieldException("Property Address cannot be empty");
            }
        }
        if ("Motor".equals(claim.getPolicyType())) {
            claim.setPropertyAddress("");
            claim.setTypeOfAnimal("");
            claim.setBreedOfAnimal("");
            if (claim.getVehicleMake() == null || claim.getVehicleMake().isEmpty()) {
                throw new MissingFieldException("Vehicle Make cannot be empty");
            }
            if (claim.getVehicleModel() == null || claim.getVehicleModel().isEmpty()) {
                throw new MissingFieldException("Vehicle Model cannot be empty");
            }
            if (claim.getManufactureYear() == null || claim.getManufactureYear().isEmpty()) {
                throw new MissingFieldException("Manufacture year cannot be empty");
            }
        }
        if ("Pet".equals(claim.getPolicyType())) {
            claim.setPropertyAddress("");
            claim.setVehicleMake("");
            claim.setVehicleModel("");
            claim.setManufactureYear("");
            if (claim.getTypeOfAnimal() == null || claim.getTypeOfAnimal().isEmpty()) {
                throw new MissingFieldException("Type of animal cannot be empty");
            }
            if (claim.getBreedOfAnimal() == null || claim.getBreedOfAnimal().isEmpty()) {
                throw new MissingFieldException("Breed of animal cannot be empty");
            }
        }
        if (claim.getFirstName() == null || claim.getFirstName().isEmpty()) {
            throw new MissingFieldException("First Name cannot be empty");
        }
        if (claim.getLastName() == null || claim.getLastName().isEmpty()) {
            throw new MissingFieldException("Last Name cannot be empty");
        }
        if (claim.getEstimatedAmount() == null) {
            throw new MissingFieldException("Estimated Amount cannot be empty");
        }
        if (claim.getClaimReason() == null || claim.getClaimReason().isEmpty()) {
            throw new MissingFieldException("Reason for claim cannot be empty");
        }
        if (claim.getIncidentDescription() == null || claim.getIncidentDescription().isEmpty()) {
            throw new MissingFieldException("Incident Description cannot be empty");
        }

        return claimRepository.save(claim);
    }

    @Override
    public Task saveTask(Task task) throws ClaimNotFoundException {
        return taskRepository.save(task);
    }

    @Override
    public Note saveNote(Note note) throws ClaimNotFoundException {
        note.setNoteDate(LocalDateTime.now());
        return noteRepository.save(note);
    }

    @Override
    public Claim updateClaim(Integer id, Map<String, Object> fields) throws ClaimNotFoundException, ArchivedException, InvalidFieldException {
//        load existing payment
        Optional<Claim> optionalClaim = claimRepository.findById(id);
        if (!optionalClaim.isPresent()) {
            throw new ClaimNotFoundException("There is no claim with id " + id);
        }
        Claim claim = optionalClaim.get();

        if (claim.getClaimStatus().equals("R") || claim.getClaimStatus().equals("C")) {
            throw new ArchivedException("The claim cannot be updated because it is archived.");
        }
        if (!Arrays.asList("O", "H", "A", "R", "C", "P").contains(fields.get("claimStatus").toString())) {
            throw new InvalidFieldException("Invalid claim status, allowed values are O, H, A, R, C, and P");
        } else if (!fields.get("claimStatus").toString().equals(claim.getClaimStatus())) {
            claim.setClaimStatus(fields.get("claimStatus").toString());
            System.out.println("claimStatus updated");
        }

        String policyNumber = fields.get("policyNumber").toString();
        if (policyNumber.length() != 9) {
            throw new InvalidFieldException("Invalid policy number, must be 9 characters long");
        } else if (!policyNumber.equals(claim.getPolicyNumber())) {
            claim.setPolicyNumber(policyNumber);
            System.out.println("policyNumber updated");
        }

        if (!fields.get("policyType").toString().equals(claim.getPolicyType())) {
            claim.setPolicyType(fields.get("policyType").toString());
            System.out.println("policyType updated");
        }
        if (!fields.get("propertyAddress").toString().equals(claim.getPropertyAddress())) {
            claim.setPropertyAddress(fields.get("propertyAddress").toString());
            System.out.println("propertyAddress updated");
        }
        if (!fields.get("vehicleMake").toString().equals(claim.getVehicleMake())) {
            claim.setVehicleMake(fields.get("vehicleMake").toString());
            System.out.println("vehicleMake updated");
        }
        if (!fields.get("vehicleModel").toString().equals(claim.getVehicleModel())) {
            claim.setVehicleModel(fields.get("vehicleModel").toString());
            System.out.println("vehicleModel updated");
        }
        if (!fields.get("manufactureYear").toString().equals(claim.getManufactureYear())) {
            claim.setManufactureYear(fields.get("manufactureYear").toString());
            System.out.println("manufactureYear updated");
        }
        if (!fields.get("typeOfAnimal").toString().equals(claim.getTypeOfAnimal())) {
            claim.setTypeOfAnimal(fields.get("typeOfAnimal").toString());
            System.out.println("typeOfAnimal updated");
        }
        if (!fields.get("breedOfAnimal").toString().equals(claim.getBreedOfAnimal())) {
            claim.setBreedOfAnimal(fields.get("breedOfAnimal").toString());
            System.out.println("breedOfAnimal updated");
        }
        if (!fields.get("firstName").toString().equals(claim.getFirstName())) {
            claim.setFirstName(fields.get("firstName").toString());
            System.out.println("firstName updated");
        }
        if (!fields.get("lastName").toString().equals(claim.getLastName())) {
            claim.setLastName(fields.get("lastName").toString());
            System.out.println("lastName updated");
        }
        if (!fields.get("lastName").toString().equals(claim.getLastName())) {
            claim.setLastName(fields.get("lastName").toString());
            System.out.println("lastName updated");
        }
        if (fields.get("claimStartDate") != null && !fields.get("claimStartDate").toString().equals(claim.getClaimStartDate().toString())) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date newUtilDate = dateFormat.parse(fields.get("claimStartDate").toString());
                java.sql.Date newSqlDate = new java.sql.Date(newUtilDate.getTime());
                claim.setClaimStartDate(newSqlDate);
                System.out.println("claimStartDate updated");
            } catch (ParseException e) {
                System.out.println("Unable to parse date: " + fields.get("claimStartDate").toString());
            }
        }
        if (fields.get("estimatedAmount") != null && Double.parseDouble(fields.get("estimatedAmount").toString()) != claim.getEstimatedAmount()) {
            claim.setEstimatedAmount(Double.parseDouble(fields.get("estimatedAmount").toString()));
            System.out.println("estimatedAmount updated");
        }
        if (fields.get("paymentAmount") != null) {
            String paymentAmountString = fields.get("paymentAmount").toString();
            if (!paymentAmountString.isEmpty()) {
                claim.setPaymentAmount(Double.parseDouble(paymentAmountString));
                System.out.println("paymentAmount updated");
            }
        }
        if (!fields.get("claimReason").toString().equals(claim.getClaimReason())) {
            claim.setClaimReason(fields.get("claimReason").toString());
            System.out.println("claimReason updated");
        }
        if (fields.get("incidentDate") != null && !fields.get("incidentDate").toString().equals(claim.getIncidentDate().toString())) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date newUtilDate = dateFormat.parse(fields.get("incidentDate").toString());
                java.sql.Date newSqlDate = new java.sql.Date(newUtilDate.getTime());
                claim.setIncidentDate(newSqlDate);
                System.out.println("incidentDate updated");
            } catch (ParseException e) {
                System.out.println("Unable to parse date: " + fields.get("incidentDate").toString());
            }
        }
        if (!fields.get("incidentDescription").toString().equals(claim.getIncidentDescription())) {
            claim.setIncidentDescription(fields.get("incidentDescription").toString());
            System.out.println("incidentDescription updated");
        }
        if (!fields.get("furtherDetails").toString().equals(claim.getFurtherDetails())) {
            claim.setFurtherDetails(fields.get("furtherDetails").toString());
            System.out.println("furtherDetails updated");
        }

        //save and return the payment
        return claimRepository.save(claim);
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
    public List<Claim> getByPolicyNumber(String policyNumber) {
        List<Claim> claims = claimRepository.findAllByPolicyNumber(policyNumber);
        System.out.println("Total " + policyNumber + " claims found = " + claims.size());
        return claims;
    }

    @Override
    public List<Claim> getByLastName(String lastName) {
        List<Claim> claims = claimRepository.findAllByLastName(lastName);
        System.out.println("Total " + lastName + " claims found = " + claims.size());
        return claims;
    }

    @Override
    public List<Claim> getByClaimStatus(List<String> claimStatus) {
        List<Claim> claims = claimRepository.findAllByClaimStatusIn(claimStatus);
        System.out.println("Total claims found of status '" + claimStatus + "' = " + claims.size());
        return claims;
    }

}

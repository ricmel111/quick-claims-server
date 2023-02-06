package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.data.TaskRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.domain.Note;
import com.allstate.quickclaimsserver.domain.Task;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import com.allstate.quickclaimsserver.exceptions.MissingFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return noteRepository.save(note);
    }

    @Override
    public Claim updateClaim(Integer id, Map<String, Object> fields) throws ClaimNotFoundException {
        //load existing payment
        Claim claim = claimRepository.findById(id).get(); //should check it is there + throw exception
        if (fields.containsKey("breedOfAnimal") && !fields.get("breedOfAnimal").toString().equals(claim.getBreedOfAnimal())) {
            claim.setBreedOfAnimal(fields.get("breedOfAnimal").toString());
            System.out.println("Breed updated");
        }
        if (fields.containsKey("claimReason") && !fields.get("claimReason").toString().equals(claim.getClaimReason())) {
            claim.setClaimReason(fields.get("claimReason").toString());
            System.out.println("claimReason updated");
        }
        claim.setPaymentAmount(Double.parseDouble(fields.get("paymentAmount").toString()));
        claim.setClaimStatus(fields.get("claimStatus").toString());
        claim.setEstimatedAmount(Double.parseDouble(fields.get("estimatedAmount").toString()));
        claim.setFirstName(fields.get("firstName").toString());
        claim.setFurtherDetails(fields.get("furtherDetails").toString());
        claim.setIncidentDescription(fields.get("incidentDescription").toString());
        claim.setLastName(fields.get("lastName").toString());
        claim.setPolicyNumber(fields.get("policyNumber").toString());
        claim.setPolicyType(fields.get("policyType").toString());
        claim.setPropertyAddress(fields.get("propertyAddress").toString());
        claim.setTypeOfAnimal(fields.get("typeOfAnimal").toString());
        claim.setVehicleMake(fields.get("vehicleMake").toString());
        claim.setManufactureYear(fields.get("manufactureYear").toString());
        claim.setVehicleModel(fields.get("vehicleModel").toString());
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

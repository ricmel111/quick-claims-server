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
import java.text.DateFormat;
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
    private TaskService taskService;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Claim saveClaim(Claim claim) throws MissingFieldException, InvalidFieldException {

        if (claim.getPolicyNumber() == null || claim.getPolicyNumber().isEmpty()) {
            System.out.println(claim.getPolicyNumber());
            throw new MissingFieldException("Policy Number cannot be empty");
        }

        if (claim.getPolicyNumber().length() != 9) {
            System.out.println(claim.getPolicyNumber());
            throw new InvalidFieldException("Policy Number must be 9 characters");
        }

        if (claim.getClaimStatus() == null || claim.getClaimStatus().isEmpty()) {
            System.out.println(claim.getClaimStatus());
            throw new MissingFieldException("Claim Status cannot be empty");
        }

        if (claim.getPolicyType() == null || claim.getPolicyType().isEmpty()) {
            System.out.println(claim.getPolicyType());
            throw new MissingFieldException("Policy Type cannot be empty");
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
        if (claim.getClaimStartDate() == null) {
            throw new MissingFieldException("Claim start date cannot be empty");
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
    public Claim updateClaim(Integer id, Map<String, Object> fields) throws ClaimNotFoundException, ArchivedException, InvalidFieldException, MissingFieldException {
        System.out.println("TEST "+id+fields);
        Optional<Claim> optionalClaim = claimRepository.findById(id);
        if (!optionalClaim.isPresent()) {
            throw new ClaimNotFoundException("There is no claim with id " + id);
        }
        Claim claim = optionalClaim.get();

        if (claim.getClaimStatus().equals("R") || claim.getClaimStatus().equals("C")) {
            throw new ArchivedException("The claim cannot be updated because it is archived.");
        }

        if (fields.containsKey("policyNumber")) {
            String policyNumber = fields.get("policyNumber").toString();
            if (policyNumber.length() != 9) {
                throw new InvalidFieldException("Invalid policy number, must be 9 characters long");
            } else if (!policyNumber.equals(claim.getPolicyNumber())) {
                claim.setPolicyNumber(policyNumber);
                System.out.println("policyNumber updated");
            }

            claim.setPolicyNumber(fields.get("policyNumber").toString());
        }

        if (fields.containsKey("claimStatus")) {
            if (!Arrays.asList("O", "H", "A", "R", "C", "P").contains(fields.get("claimStatus").toString())) {
                throw new InvalidFieldException("Invalid claim status, allowed values are O, H, A, R, C, and P");
            } else if (claim.getClaimStatus().equals("O") && fields.get("claimStatus").toString().equals("C")) {
                throw new InvalidFieldException("Cannot change claim status from O to C");
            } else if (fields.get("claimStatus").toString().equals("A") || fields.get("claimStatus").toString().equals("C")) {
                List<Task> tasks = taskService.getByClaimId(id);
                for (Task task : tasks) {
                    if (task.getTaskStatus().equals("O")) {
                        throw new InvalidFieldException("Cannot change claim status to A or C, as there are open tasks associated with this claim");
                    }
                }
            } else if (!fields.get("claimStatus").toString().equals(claim.getClaimStatus())) {
                claim.setClaimStatus(fields.get("claimStatus").toString());
                System.out.println("claimStatus updated");
            }
        }

        if (fields.containsKey("policyType")) {
            if (fields.get("policyType") == null || fields.get("policyType").toString().isEmpty()) {
                throw new MissingFieldException("Policy Type cannot be empty");
            } else if (fields.get("policyType").toString().equals("Property") && !claim.getPolicyType().equals("Property")) {
                if (!fields.containsKey("propertyAddress")) {
                    throw new MissingFieldException("Property Address must be included for Policy Type Property");
                } else {
                    claim.setPolicyType(fields.get("policyType").toString());
                    System.out.println("policyType updated to Property");
                    claim.setVehicleMake("");
                    claim.setVehicleModel("");
                    claim.setManufactureYear("");
                    claim.setTypeOfAnimal("");
                    claim.setBreedOfAnimal("");
                }
            } else if (fields.get("policyType").toString().equals("Motor") && !claim.getPolicyType().equals("Motor")) {
                if (!fields.containsKey("vehicleMake") && !fields.containsKey("vehicleMake") && !fields.containsKey("manufactureYear")) {
                    throw new MissingFieldException("Vehicle make, model and manufacture year must be included for Policy Type Motor");
                } else {
                    claim.setPolicyType(fields.get("policyType").toString());
                    System.out.println("policyType updated to Motor");
                    claim.setPropertyAddress("");
                    claim.setTypeOfAnimal("");
                    claim.setBreedOfAnimal("");
                }
            } else if (fields.get("policyType").toString().equals("Pet") && !claim.getPolicyType().equals("Pet")) {
                if (!fields.containsKey("typeOfAnimal") && !fields.containsKey("breedOfAnimal")) {
                    throw new MissingFieldException("Type and breed of animal must be included for Policy Type Pet");
                } else {
                claim.setPolicyType(fields.get("policyType").toString());
                System.out.println("policyType updated to Pet");
                claim.setPropertyAddress("");
                claim.setVehicleMake("");
                claim.setVehicleModel("");
                claim.setManufactureYear("");
            }
            }
        }

        if (fields.containsKey("propertyAddress")) {
            if (!fields.get("propertyAddress").toString().equals(claim.getPropertyAddress())) {
                claim.setPropertyAddress(fields.get("propertyAddress").toString());
                System.out.println("propertyAddress updated");
            }
        }

        if (fields.containsKey("vehicleMake")) {
            if (!fields.get("vehicleMake").toString().equals(claim.getVehicleMake())) {
                claim.setVehicleMake(fields.get("vehicleMake").toString());
                System.out.println("vehicleMake updated");
            }
        }

        if (fields.containsKey("vehicleModel")) {
            if (!fields.get("vehicleModel").toString().equals(claim.getVehicleModel())) {
                claim.setVehicleModel(fields.get("vehicleModel").toString());
                System.out.println("vehicleModel updated");
            }
        }

        if (fields.containsKey("manufactureYear")) {
            if (!fields.get("manufactureYear").toString().equals(claim.getManufactureYear())) {
                claim.setManufactureYear(fields.get("manufactureYear").toString());
                System.out.println("manufactureYear updated");
            }
        }

        if (fields.containsKey("typeOfAnimal")) {
            if (!fields.get("typeOfAnimal").toString().equals(claim.getTypeOfAnimal())) {
                claim.setTypeOfAnimal(fields.get("typeOfAnimal").toString());
                System.out.println("typeOfAnimal updated");
            }
        }

        if (fields.containsKey("breedOfAnimal")) {
            if (!fields.get("breedOfAnimal").toString().equals(claim.getBreedOfAnimal())) {
                claim.setBreedOfAnimal(fields.get("breedOfAnimal").toString());
                System.out.println("breedOfAnimal updated");
            }
        }

        if (fields.containsKey("firstName")) {
            if (!fields.get("firstName").toString().equals(claim.getFirstName())) {
                claim.setFirstName(fields.get("firstName").toString());
                System.out.println("firstName updated");
            }
        }

        if (fields.containsKey("lastName")) {
            if (!fields.get("lastName").toString().equals(claim.getLastName())) {
                claim.setLastName(fields.get("lastName").toString());
                System.out.println("lastName updated");
            }
        }

        if (fields.containsKey("estimatedAmount")) {
            if (fields.get("estimatedAmount") != null && Double.parseDouble(fields.get("estimatedAmount").toString()) != claim.getEstimatedAmount()) {
                claim.setEstimatedAmount(Double.parseDouble(fields.get("estimatedAmount").toString()));
                System.out.println("estimatedAmount updated");
            }
        }

        if (fields.containsKey("claimReason")) {
            if (!fields.get("claimReason").toString().equals(claim.getClaimReason())) {
                claim.setClaimReason(fields.get("claimReason").toString());
                System.out.println("claimReason updated");
            }
        }

        if (fields.containsKey("incidentDescription")) {
            if (!fields.get("incidentDescription").toString().equals(claim.getIncidentDescription())) {
                claim.setIncidentDescription(fields.get("incidentDescription").toString());
                System.out.println("incidentDescription updated");
            }
        }

        if (fields.containsKey("incidentDate")) {
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date incidentDate = formatter.parse(fields.get("incidentDate").toString());
                java.sql.Date sqlIncidentDate = new java.sql.Date(incidentDate.getTime());
                claim.setIncidentDate(sqlIncidentDate);
            } catch (ParseException e) {
                throw new InvalidFieldException("Incident date must be in the format yyyy-MM-dd");
            }
        }

        if (fields.containsKey("furtherDetails")) {
            if (!fields.get("furtherDetails").toString().equals(claim.getFurtherDetails())) {
                claim.setFurtherDetails(fields.get("furtherDetails").toString());
                System.out.println("furtherDetails updated");
            }
        }

        if (fields.containsKey("paymentAmount")) {
            if (fields.get("paymentAmount") != null && Double.parseDouble(fields.get("paymentAmount").toString()) != claim.getPaymentAmount()) {
                claim.setPaymentAmount(Double.parseDouble(fields.get("paymentAmount").toString()));
                System.out.println("paymentAmount updated");
            }
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

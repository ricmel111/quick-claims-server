package com.allstate.quickclaimsserver.control;

import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.data.TaskRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.domain.Note;
import com.allstate.quickclaimsserver.domain.Task;
import com.allstate.quickclaimsserver.exceptions.ArchivedException;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import com.allstate.quickclaimsserver.exceptions.InvalidFieldException;
import com.allstate.quickclaimsserver.exceptions.MissingFieldException;
import com.allstate.quickclaimsserver.service.ClaimService;
import com.allstate.quickclaimsserver.service.NoteService;
import com.allstate.quickclaimsserver.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/claim")
@CrossOrigin
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private TaskService taskService;

    @GetMapping()
    public List<Claim> getAllClaims(@RequestParam(value="policy-type", required = false) String policyType,
                                    @RequestParam(value="policynumber", required = false) String policyNumber,
                                    @RequestParam(value="lastname", required = false) String lastName,
                                    @RequestParam(value="claim-status", required = false) List<String> claimStatus) {
        if (policyType != null) {
            return claimService.getByPolicyType(policyType);
        }
        else if (policyNumber != null) {
            return claimService.getByPolicyNumber(policyNumber);
        }
        else if (lastName != null) {
            return claimService.getByLastName(lastName);
        }
        else if (claimStatus != null) {
            return claimService.getByClaimStatus(claimStatus);
        }
        else {
            return claimService.getAllClaims();
        }
    }

    @GetMapping("/{claimId}")
    public Claim findById(@PathVariable("claimId") Integer id) throws ClaimNotFoundException {
        return claimService.getById(id);
    }

    @PostMapping
    public Claim saveNewClaim(@RequestBody Claim claim) throws MissingFieldException, InvalidFieldException {
        System.out.println(claim);
        return claimService.saveClaim(claim);
    }

    @PostMapping("/{claimId}/task")
    public Task saveNewTask(@PathVariable("claimId") Integer claimId, @RequestBody Task task) throws ClaimNotFoundException {
        System.out.println(task);
        Claim claim = claimService.getById(claimId);
        task.setClaim(claim);
        claim.getTasks().add(task);
        return claimService.saveTask(task);
    }

    @PostMapping("/{claimId}/note")
    public Note saveNewNote(@PathVariable("claimId") Integer claimId, @RequestBody Note note) throws ClaimNotFoundException {
        System.out.println(note);
        Claim claim = claimService.getById(claimId);
        note.setClaim(claim);
        claim.getNotes().add(note);
        return claimService.saveNote(note);
    }

    @PutMapping("/{id}")
    public Claim updateClaim(@PathVariable Integer id, @RequestBody HashMap<String, Object> fields) throws ClaimNotFoundException, ArchivedException, InvalidFieldException, MissingFieldException {
        return claimService.updateClaim(id, fields);
    }

    @GetMapping("/init")
    public String setUpData() throws MissingFieldException, InvalidFieldException {
        Task newTask1 = new Task("O", "this is task 1", Date.valueOf("2023-01-01"));
        Task newTask2 = new Task("O", "this is task 2", Date.valueOf("2023-01-01"));
        Note newNote1 = new Note("this is note 1", LocalDateTime.of(2023, 1, 1, 0, 0));
        Note newNote2 = new Note("this is note 2", LocalDateTime.of(2023, 1, 1, 0, 0));
        List<Task> tasks = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        tasks.add(newTask1);
        tasks.add(newTask2);
        notes.add(newNote1);
        notes.add(newNote2);
        Claim claim1 = new Claim(null, "O","1234", "Property", "123 Main Street, Chicago, IL, 20982", "", "", "", "", "", "Phil", "Foden", Date.valueOf("2023-01-01"), 123.45,"Fire damage","a long description", Date.valueOf("2023-01-01"),"further details here", 0.00, tasks, notes);
        Claim claim2 = new Claim(null,"O","4567", "Pet", "", "", "", "", "Dog", "Labrador", "John", "Wick", Date.valueOf("2023-01-01"), 34.98,"Genetic conditions or congenital defects.","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>());
        Claim claim3 = new Claim(null,"P","8901", "Motor", "", "Ford", "Galaxy", "2016", "", "", "David", "Wilson", Date.valueOf("2023-01-01"), 567.45,"Collision with another vehicle or object","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>());
        Claim claim4 = new Claim(null,"A","2345", "Property", "567 Main Street, Chicago, IL, 20982", "", "", "", "", "", "Katie", "Smith", Date.valueOf("2023-01-01"), 456.00,"Burglary or theft","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>());
        Claim claim5 = new Claim(null,"R","3456", "Pet", "", "", "", "", "Cat", "Siamese", "Emily", "Johnson", Date.valueOf("2023-01-01"), 233.89,"Emergency care, such as treatment for a severe injury or illness.","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>());
        Claim claim6 = new Claim(null,"C","5678", "Motor", "", "Volkswagon", "Golf", "2020", "", "", "Jacob", "Williams", Date.valueOf("2023-01-01"), 122.12,"Mechanical breakdown or failure","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>());
        Claim claim7 = new Claim(null,"H","6789", "Motor", "", "Audi", "A4", "2021", "", "", "Michael", "Jones", Date.valueOf("2023-01-01"), 1123.45,"Vandalism or malicious damage","a long description", Date.valueOf("2023-01-01"),"further details here",0.00, new ArrayList<Task>(), new ArrayList<Note>());
        Claim claim8 = new Claim(null,"A","7890", "Pet", "", "", "", "", "Rabbit", "Mini Lop", "Emily", "Brown", Date.valueOf("2023-01-01"), 167.43,"Behavioral issues, such as aggression or separation anxiety.","a long description", Date.valueOf("2023-01-01"),"further details here",null, new ArrayList<Task>(), new ArrayList<Note>());
        newTask1.setClaim(claim1);
        newTask2.setClaim(claim1);
        newNote1.setClaim(claim1);
        newNote2.setClaim(claim1);
        claimService.saveClaim(claim1);
        claimService.saveClaim(claim2);
        claimService.saveClaim(claim3);
        claimService.saveClaim(claim4);
        claimService.saveClaim(claim5);
        claimService.saveClaim(claim6);
        claimService.saveClaim(claim7);
        claimService.saveClaim(claim8);
        return "ok";
    }
}

package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.data.TaskRepository;
import com.allstate.quickclaimsserver.data.UserRepository;
import com.allstate.quickclaimsserver.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service //inversion of control. Spring will instantiate the class. Class level
public class BootstrapService {

    @Autowired //find an instance of this repository - Dependency Injection. Spring will find a method for us. Set properties within a class. Property level
    private ClaimRepository claimRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NoteRepository noteRepository;

    @PostConstruct //run this method on start up
    public void setUpInitialData() {

        List<Task> allTasks = taskRepository.findAll();
        if (allTasks != null && !allTasks.isEmpty()) {
            int numberOfTasks = allTasks.size();
            if (numberOfTasks == 0) {
                Task newTask = new Task("O", "this is some text for a task", Date.valueOf("2023-01-01"));
                Task savedTask = taskRepository.save(newTask);
                System.out.println("Task saved with id " + savedTask.getId());
            }
        }
        List<Task> allNotes = taskRepository.findAll();
        if (allNotes != null && !allNotes.isEmpty()) {
            int numberOfNotes = allNotes.size();
            if (numberOfNotes == 0) {
                Note newNote = new Note("this is some text for a note", LocalDateTime.of(2023, 1, 1, 0, 0));
                Note savedNote = noteRepository.save(newNote);
                System.out.println("Note saved with id " + savedNote.getId());
            }
        }
        if(claimRepository.count() == 0) {
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
            Claim claim1 = new Claim(null, "O","123456781", "Property", "123 Main Street, Chicago, IL, 20982", "", "", "", "", "", "Phil", "Foden", Date.valueOf("2023-01-01"), 123.45,"Fire damage","a long description", Date.valueOf("2023-01-01"),"further details here", null, tasks, notes);
            newTask1.setClaim(claim1);
            newTask2.setClaim(claim1);
            newNote1.setClaim(claim1);
            newNote2.setClaim(claim1);
            claimRepository.save(claim1);
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void createInitialUsers() {
        //if no users, add in the following:
        if (userRepository.count() == 0) {
            User user1 = new User("ric", "Ric", "123", UserRole.USER);
            User user2 = new User("vik", "Vikki", "123", UserRole.MANAGER);
            userService.save(user1);
            userService.save(user2);
        }
    }
}

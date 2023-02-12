package com.allstate.quickclaimsserver;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.data.TaskRepository;
import com.allstate.quickclaimsserver.data.UserRepository;
import com.allstate.quickclaimsserver.domain.Note;
import com.allstate.quickclaimsserver.domain.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TaskServiceTests {

    @MockBean
    private ClaimRepository claimRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private NoteRepository noteRepository;

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("O", "this is task 1", Date.valueOf("2023-01-01")));
        tasks.add(new Task("O", "this is task 2", Date.valueOf("2023-01-01")));
        Mockito.when(taskRepository.findAll()).thenReturn(tasks);
        assertEquals(2, tasks.size());
    }

    @Test
    public void testGetAllTasksByClaimId() {
        Integer claimId = 1;
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("O", "this is task 1", Date.valueOf("2023-01-01")));
        tasks.add(new Task("O", "this is task 2", Date.valueOf("2023-01-01")));
        Mockito.when(taskRepository.findAllTasksByClaimId(claimId)).thenReturn(tasks);
        List<Task> result = taskRepository.findAllTasksByClaimId(claimId);
        assertEquals(tasks, result);
    }

    @Test
    public void testGetAllTasksByInvalidClaimId() {
        Integer claimId = -1;
        List<Task> result = taskRepository.findAllTasksByClaimId(claimId);
        assertEquals(0, result.size());
    }
}

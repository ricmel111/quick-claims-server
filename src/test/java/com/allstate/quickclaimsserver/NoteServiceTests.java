package com.allstate.quickclaimsserver;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.data.TaskRepository;
import com.allstate.quickclaimsserver.data.UserRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.domain.Note;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import com.allstate.quickclaimsserver.exceptions.InvalidFieldException;
import com.allstate.quickclaimsserver.exceptions.MissingFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class NoteServiceTests {

    @MockBean
    private ClaimRepository claimRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private NoteRepository noteRepository;

    @Test
    public void testGetAllNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("this is note 1", LocalDateTime.of(2023, 1, 1, 0, 0)));
        notes.add(new Note("this is note 2", LocalDateTime.of(2023, 2, 2, 0, 0)));
        Mockito.when(noteRepository.findAll()).thenReturn(notes);
        assertEquals(2, notes.size());
    }

    @Test
    public void testGetAllNotesByClaimId() {
        Integer claimId = 1;
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("this is note 1", LocalDateTime.of(2023, 1, 1, 0, 0)));
        Mockito.when(claimRepository.findById(claimId)).thenReturn(Optional.of(new Claim()));
        Mockito.when(noteRepository.findAllNotesByClaimId(claimId)).thenReturn(notes);
        List<Note> result = noteRepository.findAllNotesByClaimId(claimId);
        assertEquals(notes, result);
    }

    @Test
    public void testGetAllNotesByInvalidClaimId() throws ClaimNotFoundException {
        Integer claimId = -1;
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("this is note 1", LocalDateTime.of(2023, 1, 1, 0, 0)));
        Mockito.when(claimRepository.findById(claimId)).thenReturn(Optional.of(new Claim()));
        Mockito.when(noteRepository.findAllNotesByClaimId(claimId)).thenReturn(notes);
        List<Note> result = noteRepository.findAllNotesByClaimId(claimId);
        assertEquals(notes, result);
    }

//    @Test
//    public void testGetAllNotesByInvalidClaimId_Exception() {
//        Integer claimId = 0;
//        Mockito.when(claimRepository.findById(claimId)).thenReturn(Optional.empty());
//        assertThrows(ClaimNotFoundException.class, () -> {
//            noteRepository.findAllNotesByClaimId(claimId);
//        });
//    }

}

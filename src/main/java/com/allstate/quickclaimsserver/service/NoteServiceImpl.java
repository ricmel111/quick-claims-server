package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private NoteRepository noteRepository;


    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes;
    }

    @Override
    public List<Note> getByClaimId(Integer claimId) {
        List<Note> notes = noteRepository.findAllNotesByClaimId(claimId);
        return notes;
    }
}
package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.data.NoteRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.domain.Note;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes;
    }

    @Override
    public List<Note> getByClaimId(Integer claimId) throws ClaimNotFoundException {
        Optional<Claim> optionalClaim = claimRepository.findById(claimId);
        if (!optionalClaim.isPresent()) {
            throw new ClaimNotFoundException("There is no claim with id " + claimId);
        }
        List<Note> notes = noteRepository.findAllNotesByClaimId(claimId);
        return notes;
    }
}
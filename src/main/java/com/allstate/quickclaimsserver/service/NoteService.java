package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.domain.Note;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;

import java.util.List;

public interface NoteService {
    public List<Note> getAllNotes();

    public List<Note> getByClaimId(Integer claimId) throws ClaimNotFoundException;
}

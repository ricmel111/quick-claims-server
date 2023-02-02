package com.allstate.quickclaimsserver.data;

import com.allstate.quickclaimsserver.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    public List<Note> findAllNotesByClaimId(Integer claimId);
}

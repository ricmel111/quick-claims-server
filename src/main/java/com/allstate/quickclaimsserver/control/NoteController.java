package com.allstate.quickclaimsserver.control;

import com.allstate.quickclaimsserver.domain.Note;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import com.allstate.quickclaimsserver.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
@CrossOrigin
public class NoteController {

        @Autowired
        private NoteService noteService;

        @GetMapping()
        public List<Note> getAllNotes(@RequestParam(value="claimId", required = false) Integer claimId)  throws ClaimNotFoundException {
            if (claimId != null) {
                return noteService.getByClaimId(claimId);
            }
            else {
                return noteService.getAllNotes();
            }
        }

}

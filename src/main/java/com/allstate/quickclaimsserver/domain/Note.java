package com.allstate.quickclaimsserver.domain;

import com.allstate.quickclaimsserver.domain.Claim;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Claim claim;
    private String noteText;
    private Date noteDate;

    public Note(String noteText, Date noteDate) {
        this.noteText = noteText;
        this.noteDate = noteDate;
    }

    public Note() {
    }

    @JsonIgnore
    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(claim, note.claim) && Objects.equals(noteText, note.noteText) && Objects.equals(noteDate, note.noteDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, claim, noteText, noteDate);
    }

    @Override
    public String toString() {
        return "NoteRepository{" +
                "id=" + id +
                ", claim=" + claim +
                ", noteText='" + noteText + '\'' +
                ", noteDate='" + noteDate + '\'' +
                '}';
    }
}

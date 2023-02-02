package com.allstate.quickclaimsserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Claim claim;
    private String taskStatus;
    private String taskText;
    private Date taskDate;

    public Task(String taskStatus, String taskText, Date taskDate) {
        this.taskStatus = taskStatus;
        this.taskText = taskText;
        this.taskDate = taskDate;
    }

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(claim, task.claim) && Objects.equals(taskStatus, task.taskStatus) && Objects.equals(taskText, task.taskText) && Objects.equals(taskDate, task.taskDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, claim, taskStatus, taskText, taskDate);
    }

    @Override
    public String toString() {
        return "TaskRepository{" +
                "id=" + id +
                ", claim=" + claim +
                ", taskStatus='" + taskStatus + '\'' +
                ", taskText='" + taskText + '\'' +
                ", taskDate=" + taskDate +
                '}';
    }
}
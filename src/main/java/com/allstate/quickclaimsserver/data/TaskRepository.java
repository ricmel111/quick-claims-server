package com.allstate.quickclaimsserver.data;

import com.allstate.quickclaimsserver.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    public List<Task> findAllTasksByClaimId(Integer claimId);
}

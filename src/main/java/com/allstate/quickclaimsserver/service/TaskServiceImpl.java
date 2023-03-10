package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.data.ClaimRepository;
import com.allstate.quickclaimsserver.data.TaskRepository;
import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.domain.Task;
import com.allstate.quickclaimsserver.exceptions.ClaimNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks;
    }

    @Override
    public List<Task> getByClaimId(Integer claimId) throws ClaimNotFoundException {
        Optional<Claim> optionalClaim = claimRepository.findById(claimId);
        if (!optionalClaim.isPresent()) {
            throw new ClaimNotFoundException("There is no claim with id " + claimId);
        }
        List<Task> tasks = taskRepository.findAllTasksByClaimId(claimId);
        return tasks;
    }

    @Override
    public Task updateTask(Integer id, Map<String, Object> fields) {
        Task task = taskRepository.findById(id).get();
        task.setTaskStatus(fields.get("taskStatus").toString());
        return taskRepository.save(task);
    }
}

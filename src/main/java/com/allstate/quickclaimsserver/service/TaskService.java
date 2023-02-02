package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.domain.Claim;
import com.allstate.quickclaimsserver.domain.Task;
import com.allstate.quickclaimsserver.exceptions.MissingFieldException;

import java.util.List;
import java.util.Map;

public interface TaskService {

    public List<Task> getAllTasks();

    public List<Task> getByClaimId(Integer claimId);

    public Task updateTask(Integer id, Map<String, Object> fields);
}

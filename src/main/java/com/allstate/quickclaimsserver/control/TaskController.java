package com.allstate.quickclaimsserver.control;

import com.allstate.quickclaimsserver.domain.Task;
import com.allstate.quickclaimsserver.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping()
    public List<Task> getAllTasks(@RequestParam(value="claimId", required = false) Integer claimId) {
        if (claimId != null) {
            return taskService.getByClaimId(claimId);
        }
        else {
            return taskService.getAllTasks();
        }
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody HashMap<String, Object> fields) {
        return taskService.updateTask(id, fields);
    }

}

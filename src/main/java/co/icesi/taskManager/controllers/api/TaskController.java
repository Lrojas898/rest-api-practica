package co.icesi.taskManager.controllers.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import co.icesi.taskManager.dtos.TaskDto;

@RequestMapping("/tasks")
public interface TaskController {

    @GetMapping
    ResponseEntity<List<TaskDto>> findAllTask();

    @PostMapping
    ResponseEntity<TaskDto> addTask(@RequestBody TaskDto dto);

    @PutMapping
    ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable long id);

    @GetMapping("/{id}")
    ResponseEntity<TaskDto> findById(@PathVariable long id);
}

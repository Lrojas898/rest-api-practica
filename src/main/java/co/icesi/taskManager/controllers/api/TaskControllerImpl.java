package co.icesi.taskManager.controllers.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icesi.taskManager.dtos.TaskDto;
import co.icesi.taskManager.mappers.TaskMapper;
import co.icesi.taskManager.model.Task;
import co.icesi.taskManager.services.interfaces.TaskService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;
    private final TaskMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<List<TaskDto>> findAllTask() {

        List<TaskDto> response = taskService.getAllTask().stream().map(mapper::taskToTaskDto).toList();
        return ResponseEntity.ok(response);

    }

    @PostMapping
    @Override
    @PreAuthorize("hasAuthority('CREATE_TASK')")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto dto) {

        Task newTask = mapper.taskDtoToTask(dto);
        taskService.createTask(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.taskToTaskDto(newTask));

    }

    @PutMapping
    @Override
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto dto) {

        Task updateTask = mapper.taskDtoToTask(dto);
        taskService.updateTask(updateTask);

        return ResponseEntity.ok(mapper.taskToTaskDto(updateTask));

    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteTask(@PathVariable long id) {

        try {
            taskService.deleteTask(id);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<TaskDto> findById(@PathVariable long id) {
        try {
            TaskDto response = mapper.taskToTaskDto(taskService.getTaskById(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

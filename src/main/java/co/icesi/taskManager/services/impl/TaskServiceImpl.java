package co.icesi.taskManager.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import co.icesi.taskManager.model.Task;
import co.icesi.taskManager.model.User;
import co.icesi.taskManager.repositories.TaskRepository;
import co.icesi.taskManager.repositories.UserRepository;
import co.icesi.taskManager.services.interfaces.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repo;
    private final UserRepository userRepo;

    @Override
    public Task createTask(Task task) {
        if (task.getId() != null && repo.findById(task.getId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una tarea con el ID: " + task.getId());
        }
        return repo.save(task);
    }

    @Override
    public Task updateTask(Task task) {

        if (task.getId() != 0 && !repo.findById(task.getId()).isPresent()) {
            throw new IllegalArgumentException("NO existe una tarea con ese ID " + task.getId());
        }

        return repo.save(task);

    }

    @Override
    public void deleteTask(long taskId) {

        Task taskToDelete = repo.getById(taskId);
        repo.delete(taskToDelete);

    }

    @Override
    public void assignTask(long taskId, long userId) {

        Task task = repo.getById(taskId);
        User user = userRepo.getById(userId);

        user.getTasks().add(task);

        task.getAssignedUsers().add(user);

    }

    @Override
    public void unassignTask(long taskId, long userId) {

        Task task = repo.getById(taskId);
        User user = userRepo.getById(userId);

        user.getTasks().remove(task);

        task.getAssignedUsers().remove(user);

    }

    @Override
    public Task getTaskById(long taskId) {

        if (taskId != 0 && !repo.findById(taskId).isPresent()) {
            throw new IllegalArgumentException("NO existe una tarea con ese ID " + taskId);
        }

        return repo.getById(taskId);

    }

    @Override
    public List<Task> getAllTask() {

        return repo.findAll();

    }

}

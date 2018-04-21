package co.edu.ucc.todoapp.data.entidades.mapper;

import java.util.ArrayList;
import java.util.List;

import co.edu.ucc.todoapp.data.entidades.TaskEntity;
import co.edu.ucc.todoapp.domain.model.Task;

public class TaskEntityMapper {

    public TaskEntity reverseMap(Task task) {
        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setDescription(task.getName());
        taskEntity.setDone(task.isDone());

        return taskEntity;
    }

    public List<TaskEntity> reverseMap(List<Task> lstTask) {

        List<TaskEntity> lstTaskEntity = new ArrayList<>();

        for (Task task : lstTask) {
            lstTaskEntity.add(reverseMap(task));
        }

        return lstTaskEntity;

    }

    public Task map(TaskEntity taskEntity) {
        Task task = new Task();
        task.setName(taskEntity.getDescription());
        task.setDone(taskEntity.isDone());
        return task;
    }

    public List<Task> map(List<TaskEntity> lstTaskEntity) {

        List<Task> lstTask = new ArrayList<>();

        for (TaskEntity taskEntity : lstTaskEntity) {
            lstTask.add(map(taskEntity));
        }

        return lstTask;

    }

}

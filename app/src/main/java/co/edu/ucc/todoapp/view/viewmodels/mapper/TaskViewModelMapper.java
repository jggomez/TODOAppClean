package co.edu.ucc.todoapp.view.viewmodels.mapper;

import java.util.ArrayList;
import java.util.List;

import co.edu.ucc.todoapp.domain.model.Task;
import co.edu.ucc.todoapp.view.viewmodels.TaskViewModel;

/**
 * Created by jggomez on 23-Mar-18.
 */

public class TaskViewModelMapper {

    public Task reverseMap(TaskViewModel taskViewModel) {
        Task task = new Task();
        task.setName(taskViewModel.getName());
        task.setUriImage(taskViewModel.getUriImage());
        task.setDone(taskViewModel.isDone());
        return task;
    }

    public List<Task> reverseMap(List<TaskViewModel> lstTaskViewModel) {

        List<Task> lstTask = new ArrayList<>();

        for (TaskViewModel taskViewModel : lstTaskViewModel) {
            lstTask.add(reverseMap(taskViewModel));
        }

        return lstTask;

    }

    public TaskViewModel map(Task task) {
        TaskViewModel taskViewModel = new TaskViewModel();
        taskViewModel.setName(task.getName());
        taskViewModel.setUriImage(task.getUriImage());
        taskViewModel.setDone(task.isDone());
        return taskViewModel;
    }

    public List<TaskViewModel> map(List<Task> lstTask) {

        List<TaskViewModel> lstTaskViewModel = new ArrayList<>();

        for (Task task : lstTask) {
            lstTaskViewModel.add(map(task));
        }

        return lstTaskViewModel;

    }


}


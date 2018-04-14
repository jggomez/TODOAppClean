package co.edu.ucc.todoapp.domain.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.ucc.todoapp.domain.model.Task;

/**
 * Created by jggomez on 23-Mar-18.
 */

public class InteractorTask implements IInteractorTask {

    private static List<Task> lstTask = new ArrayList<>();

    @Override
    public void add(Task task) {
        UUID uuid = UUID.randomUUID();
        task.setId(uuid.toString());
        lstTask.add(task);
    }

    @Override
    public List<Task> getAll() {
        return lstTask;
    }

}

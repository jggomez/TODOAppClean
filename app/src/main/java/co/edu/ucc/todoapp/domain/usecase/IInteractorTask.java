package co.edu.ucc.todoapp.domain.usecase;


import java.util.List;

import co.edu.ucc.todoapp.domain.model.Task;

/**
 * Created by jggomez on 23-Mar-18.
 */

public interface IInteractorTask {

    void add(Task task);

    List<Task> getAll();

}

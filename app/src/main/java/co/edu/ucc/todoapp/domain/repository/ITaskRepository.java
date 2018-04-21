package co.edu.ucc.todoapp.domain.repository;


import java.util.List;

import co.edu.ucc.todoapp.domain.model.Task;
import io.reactivex.Observable;

public interface ITaskRepository {

    Observable<Boolean> addTask(Task task);

    Observable<List<Task>> getAll();

}

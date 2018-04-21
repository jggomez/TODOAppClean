package co.edu.ucc.todoapp.data.repository.task;


import java.util.List;

import co.edu.ucc.todoapp.data.entidades.TaskEntity;
import io.reactivex.Observable;

public interface ITaskDataSource {

    Observable<Boolean> addTask(TaskEntity taskEntity);

    Observable<List<TaskEntity>> getAll();


}

package co.edu.ucc.todoapp.data.repository.task;

import java.util.List;

import co.edu.ucc.todoapp.data.entidades.TaskEntity;
import co.edu.ucc.todoapp.data.firebase.TaskFirestoreDAO;
import io.reactivex.Observable;

public class TaskFirebaseDataSource implements ITaskDataSource {

    private TaskFirestoreDAO taskFirestoreDAO;

    public TaskFirebaseDataSource() {
        taskFirestoreDAO = new TaskFirestoreDAO();
    }

    @Override
    public Observable<Boolean> addTask(TaskEntity taskEntity) {
        return taskFirestoreDAO.addTask(taskEntity);
    }

    @Override
    public Observable<List<TaskEntity>> getAll() {
        return taskFirestoreDAO.getAll();
    }
}

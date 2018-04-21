package co.edu.ucc.todoapp.data.repository.task;

import java.util.List;

import co.edu.ucc.todoapp.data.entidades.mapper.TaskEntityMapper;
import co.edu.ucc.todoapp.domain.model.Task;
import co.edu.ucc.todoapp.domain.repository.ITaskRepository;
import io.reactivex.Observable;

public class TaskRepository implements ITaskRepository {

    private final TaskEntityMapper mapper;
    private final TaskDataSourceFactory factory;

    public TaskRepository(TaskEntityMapper mapper,
                          TaskDataSourceFactory factory) {
        this.mapper = mapper;
        this.factory = factory;
    }

    @Override
    public Observable<Boolean> addTask(Task task) {
        ITaskDataSource taskDataSource = factory.createFirebaseDataSource();
        return taskDataSource.addTask(mapper.reverseMap(task));
    }

    @Override
    public Observable<List<Task>> getAll() {
        ITaskDataSource taskDataSource = factory.createFirebaseDataSource();
        return taskDataSource
                .getAll()
                .map(mapper::map);
    }
}

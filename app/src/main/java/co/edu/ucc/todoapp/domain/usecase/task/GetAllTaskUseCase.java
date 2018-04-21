package co.edu.ucc.todoapp.domain.usecase.task;

import java.util.List;

import co.edu.ucc.todoapp.domain.model.Task;
import co.edu.ucc.todoapp.domain.repository.ITaskRepository;
import co.edu.ucc.todoapp.domain.usecase.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetAllTaskUseCase extends UseCase<List<Task>> {

    private final ITaskRepository taskRepository;

    public GetAllTaskUseCase(Scheduler executorThread,
                             Scheduler uiThread,
                             ITaskRepository taskRepository) {
        super(executorThread, uiThread);
        this.taskRepository = taskRepository;
    }

    @Override
    public Observable<List<Task>> createObservable() {
        return taskRepository.getAll();
    }
}

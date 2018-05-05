package co.edu.ucc.todoapp.domain.usecase.task;

import java.util.List;

import co.edu.ucc.todoapp.domain.model.Task;
import co.edu.ucc.todoapp.domain.repository.ITaskRepository;
import co.edu.ucc.todoapp.domain.usecase.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetAllTaskLocalUseCase extends UseCase<List<Task>> {

    private ITaskRepository repository;

    public GetAllTaskLocalUseCase(Scheduler executorThread,
                                  Scheduler uiThread,
                                  ITaskRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    @Override
    public Observable<List<Task>> createObservable() {
        return repository.getAllLocal();
    }
}

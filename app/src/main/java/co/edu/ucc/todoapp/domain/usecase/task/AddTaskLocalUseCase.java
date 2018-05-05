package co.edu.ucc.todoapp.domain.usecase.task;

import co.edu.ucc.todoapp.domain.model.Task;
import co.edu.ucc.todoapp.domain.repository.ITaskRepository;
import co.edu.ucc.todoapp.domain.usecase.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class AddTaskLocalUseCase extends UseCase<Boolean> {

    private ITaskRepository repository;
    private Task task;

    public AddTaskLocalUseCase(Scheduler executorThread,
                               Scheduler uiThread,
                               ITaskRepository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return repository.addTaskLocal(task);
    }
}

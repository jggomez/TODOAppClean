package co.edu.ucc.todoapp.domain.usecase.task;

import co.edu.ucc.todoapp.domain.model.Task;
import co.edu.ucc.todoapp.domain.repository.ITaskRepository;
import co.edu.ucc.todoapp.domain.usecase.UseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class AddTaskUseCase extends UseCase<Boolean> {

    private final ITaskRepository taskRepository;
    private Task task;

    public AddTaskUseCase(Scheduler executorThread,
                          Scheduler uiThread,
                          ITaskRepository taskRepository) {
        super(executorThread, uiThread);
        this.taskRepository = taskRepository;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public Observable<Boolean> createObservable() {
        return taskRepository.addTask(task);
    }
}

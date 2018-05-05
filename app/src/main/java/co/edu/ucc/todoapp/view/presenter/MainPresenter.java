package co.edu.ucc.todoapp.view.presenter;

import java.util.List;

import co.edu.ucc.todoapp.domain.model.Task;
import co.edu.ucc.todoapp.domain.usecase.task.AddTaskLocalUseCase;
import co.edu.ucc.todoapp.domain.usecase.task.AddTaskUseCase;
import co.edu.ucc.todoapp.domain.usecase.task.GetAllTaskLocalUseCase;
import co.edu.ucc.todoapp.domain.usecase.task.GetAllTaskUseCase;
import co.edu.ucc.todoapp.view.activities.IMainView;
import co.edu.ucc.todoapp.view.viewmodels.mapper.TaskViewModelMapper;
import io.reactivex.observers.DisposableObserver;

public class MainPresenter implements IMainPresenter {

    private boolean local;
    private IMainView view;
    private final AddTaskUseCase addTaskUseCase;
    private final GetAllTaskUseCase getAllTaskUseCase;
    private final AddTaskLocalUseCase addTaskLocalUseCase;
    private final GetAllTaskLocalUseCase getAllTaskLocalUseCase;
    private TaskViewModelMapper mapper;

    public MainPresenter(IMainView view,
                         AddTaskUseCase addTaskUseCase,
                         GetAllTaskUseCase getAllTaskUseCase,
                         AddTaskLocalUseCase addTaskLocalUseCase,
                         GetAllTaskLocalUseCase getAllTaskLocalUseCase,
                         TaskViewModelMapper mapper) {
        this.view = view;
        this.addTaskUseCase = addTaskUseCase;
        this.getAllTaskUseCase = getAllTaskUseCase;
        this.mapper = mapper;
        this.addTaskLocalUseCase = addTaskLocalUseCase;
        this.getAllTaskLocalUseCase = getAllTaskLocalUseCase;
    }

    @Override
    public void addTask(String name, boolean local) {
        try {
            this.local = local;

            Task task = new Task();
            task.setName(name);
            task.setDone(false);

            if (local) {
                addTaskLocalUseCase.setTask(task);
                addTaskLocalUseCase.execute(new TaskObserver());
            } else {
                addTaskUseCase.setTask(task);
                addTaskUseCase.execute(new TaskObserver());
            }

        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private class TaskObserver extends DisposableObserver<Boolean> {

        @Override
        public void onNext(Boolean aBoolean) {
            view.showSuccessful();
            if (local) {
                getAllTaskLocalUseCase.execute(new TaskGetObserver());
            } else {
                getAllTaskUseCase.execute(new TaskGetObserver());
            }
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    }

    private class TaskGetObserver extends DisposableObserver<List<Task>> {


        @Override
        public void onNext(List<Task> tasks) {
            view.showTasks(mapper.map(tasks));
        }

        @Override
        public void onError(Throwable e) {
            view.showError(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    }

}

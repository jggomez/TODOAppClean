package co.edu.ucc.todoapp.view.presenter;

import java.util.List;

import co.edu.ucc.todoapp.domain.model.Task;
import co.edu.ucc.todoapp.domain.usecase.IInteractorTask;
import co.edu.ucc.todoapp.view.activities.IMainView;
import co.edu.ucc.todoapp.view.viewmodels.mapper.TaskViewModelMapper;

public class MainPresenter implements IMainPresenter {

    private IMainView view;
    private final IInteractorTask interactorTask;
    private TaskViewModelMapper mapper;

    public MainPresenter(IMainView view,
                         IInteractorTask interactorTask,
                         TaskViewModelMapper mapper) {
        this.view = view;
        this.interactorTask = interactorTask;
        this.mapper = mapper;
    }

    @Override
    public void addTask(String name) {
        try {
            Task task = new Task();
            task.setName(name);
            interactorTask.add(task);
            List<Task> tasks = interactorTask.getAll();
            view.showTasks(mapper.map(tasks));
            view.showSuccessful();
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    @Override
    public void getAllTask() {
        try {
            List<Task> tasks = interactorTask.getAll();
            view.showTasks(mapper.map(tasks));
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

}

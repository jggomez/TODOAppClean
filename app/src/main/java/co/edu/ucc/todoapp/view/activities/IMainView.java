package co.edu.ucc.todoapp.view.activities;

import java.util.List;

import co.edu.ucc.todoapp.view.viewmodels.TaskViewModel;

public interface IMainView {

    void showError(String error);

    void showSuccessful();

    void addTask();

    void showTasks(List<TaskViewModel> taskViewModels);

}

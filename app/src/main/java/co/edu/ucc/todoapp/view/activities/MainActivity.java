package co.edu.ucc.todoapp.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.edu.ucc.todoapp.R;
import co.edu.ucc.todoapp.data.entidades.mapper.TaskEntityMapper;
import co.edu.ucc.todoapp.data.repository.task.TaskDataSourceFactory;
import co.edu.ucc.todoapp.data.repository.task.TaskRepository;
import co.edu.ucc.todoapp.domain.usecase.task.AddTaskLocalUseCase;
import co.edu.ucc.todoapp.domain.usecase.task.AddTaskUseCase;
import co.edu.ucc.todoapp.domain.usecase.task.GetAllTaskLocalUseCase;
import co.edu.ucc.todoapp.domain.usecase.task.GetAllTaskUseCase;
import co.edu.ucc.todoapp.view.adapters.TaskAdapter;
import co.edu.ucc.todoapp.view.presenter.IMainPresenter;
import co.edu.ucc.todoapp.view.presenter.MainPresenter;
import co.edu.ucc.todoapp.view.viewmodels.TaskViewModel;
import co.edu.ucc.todoapp.view.viewmodels.mapper.TaskViewModelMapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.txtDescTask)
    EditText txtDescTask;

    @BindView(R.id.rvTask)
    RecyclerView rvTask;

    private IMainPresenter presenter;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter = new MainPresenter(
                this,
                new AddTaskUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new TaskRepository(
                                new TaskEntityMapper(),
                                new TaskDataSourceFactory(getApplicationContext())
                        )
                ),
                new GetAllTaskUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new TaskRepository(
                                new TaskEntityMapper(),
                                new TaskDataSourceFactory(getApplicationContext())
                        )
                ),
                new AddTaskLocalUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new TaskRepository(
                                new TaskEntityMapper(),
                                new TaskDataSourceFactory(getApplicationContext())
                        )
                ),
                new GetAllTaskLocalUseCase(
                        Schedulers.io(),
                        AndroidSchedulers.mainThread(),
                        new TaskRepository(
                                new TaskEntityMapper(),
                                new TaskDataSourceFactory(getApplicationContext())
                        )
                ),
                new TaskViewModelMapper()
        );

        initAdapter();
        initRecyclerView();
    }

    private void initAdapter() {
        taskAdapter = new TaskAdapter();
    }

    private void initRecyclerView() {
        rvTask.setLayoutManager(new LinearLayoutManager(this));
        rvTask.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rvTask.setAdapter(taskAdapter);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessful() {
        Toast.makeText(this, R.string.msg_successful, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fbAddTask)
    @Override
    public void addTask() {
        String descTask = txtDescTask.getText().toString();
        presenter.addTask(descTask, false);
    }

    @Override
    public void showTasks(List<TaskViewModel> taskViewModels) {
        taskAdapter.addLstTask(taskViewModels);
        taskAdapter.notifyDataSetChanged();
    }
}

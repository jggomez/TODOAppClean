package co.edu.ucc.todoapp.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

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

public class MainActivity extends AppCompatActivity implements IMainView,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.txtDescTask)
    EditText txtDescTask;

    @BindView(R.id.rvTask)
    RecyclerView rvTask;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    private IMainPresenter presenter;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
        }

        nav_view.setNavigationItemSelectedListener(this);

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
        /*String descTask = txtDescTask.getText().toString();
        presenter.addTask(descTask, false);*/

        boolean wrapInScrollView = true;
        new MaterialDialog.Builder(this)
                .title(R.string.title)
                .customView(R.layout.popu_task, wrapInScrollView)
                .positiveText(R.string.positive)
                .show();
    }

    @Override
    public void showTasks(List<TaskViewModel> taskViewModels) {
        taskAdapter.addLstTask(taskViewModels);
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Toast.makeText(this, "Click en Settings", Toast.LENGTH_SHORT).show();
        }

        if (id == android.R.id.home) {
            drawer_layout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.contactar) {
            Toast.makeText(this, "Click en Contactar del menu", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.acerca) {
            Toast.makeText(this, "Click en Acerca del menu", Toast.LENGTH_SHORT).show();
        }

        drawer_layout.closeDrawer(GravityCompat.START);

        return true;
    }
}

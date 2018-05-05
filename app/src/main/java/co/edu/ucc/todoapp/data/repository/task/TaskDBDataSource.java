package co.edu.ucc.todoapp.data.repository.task;

import android.content.Context;

import java.util.List;

import co.edu.ucc.todoapp.data.db.AppDatabase;
import co.edu.ucc.todoapp.data.db.dao.ITaskEntityDAO;
import co.edu.ucc.todoapp.data.entidades.TaskEntity;
import io.reactivex.Observable;

public class TaskDBDataSource implements ITaskDataSource {

    private ITaskEntityDAO taskEntityDAO;
    private Context context;

    public TaskDBDataSource(Context context) {
        this.context = context;
        taskEntityDAO =
                AppDatabase.getInstance(context).taskEntityDAO();
    }

    @Override
    public Observable<Boolean> addTask(TaskEntity taskEntity) {
        return
                Observable.create(emitter -> {
                    try {
                        taskEntityDAO.insert(taskEntity);
                        emitter.onNext(true);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }

                });
    }

    @Override
    public Observable<List<TaskEntity>> getAll() {
        return
                Observable.create(emitter -> {
                    try {
                        emitter.onNext(taskEntityDAO.getAll());
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                });
    }
}

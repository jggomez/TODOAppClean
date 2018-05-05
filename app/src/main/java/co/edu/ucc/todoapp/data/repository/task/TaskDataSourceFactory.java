package co.edu.ucc.todoapp.data.repository.task;

import android.content.Context;

public class TaskDataSourceFactory {

    private Context context;

    public TaskDataSourceFactory(Context context) {
        this.context = context;

    }

    public ITaskDataSource createFirebaseDataSource() {
        return new TaskFirebaseDataSource();
    }

    public ITaskDataSource createDBDataSource() {
        return new TaskDBDataSource(context);
    }
}

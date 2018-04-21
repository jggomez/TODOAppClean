package co.edu.ucc.todoapp.data.repository.task;

public class TaskDataSourceFactory {

    public ITaskDataSource createFirebaseDataSource() {
        return new TaskFirebaseDataSource();
    }
}

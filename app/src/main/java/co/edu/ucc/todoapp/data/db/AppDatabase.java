package co.edu.ucc.todoapp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import co.edu.ucc.todoapp.data.db.dao.ITaskEntityDAO;
import co.edu.ucc.todoapp.data.entidades.TaskEntity;

@Database(entities = {TaskEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance = null;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "db-tasks")
                    .build();
        }

        return instance;
    }

    public abstract ITaskEntityDAO taskEntityDAO();

}

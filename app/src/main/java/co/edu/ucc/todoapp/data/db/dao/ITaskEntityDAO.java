package co.edu.ucc.todoapp.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import co.edu.ucc.todoapp.data.entidades.TaskEntity;

@Dao
public interface ITaskEntityDAO {

    @Insert
    void insert(TaskEntity... taskEntity);

    @Update
    void update(TaskEntity... taskEntity);

    @Delete
    void delete(TaskEntity... taskEntity);

    @Query("Select * From taskEntity")
    List<TaskEntity> getAll();

    @Query("Select * From taskEntity Where id = :id")
    TaskEntity getById(int id);


}

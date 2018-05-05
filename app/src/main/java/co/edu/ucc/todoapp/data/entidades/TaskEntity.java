package co.edu.ucc.todoapp.data.entidades;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

@Entity(tableName = "taskEntity")
public class TaskEntity {

    @Exclude
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "done")
    private boolean done;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

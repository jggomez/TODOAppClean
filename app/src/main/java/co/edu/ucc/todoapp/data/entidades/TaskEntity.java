package co.edu.ucc.todoapp.data.entidades;

public class TaskEntity {

    private String description;
    private boolean done;

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

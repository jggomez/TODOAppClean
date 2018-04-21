package co.edu.ucc.todoapp.data.firebase;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import co.edu.ucc.todoapp.data.entidades.TaskEntity;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class TaskFirestoreDAO {

    private FirebaseFirestore db;

    public TaskFirestoreDAO() {
        db = FirebaseFirestore.getInstance();
    }

    public Observable<Boolean> addTask(TaskEntity taskEntity) {

        PublishSubject<Boolean> publisher = PublishSubject.create();

        db.collection("tasks")
                .add(taskEntity)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        publisher.onNext(true);
                        publisher.onComplete();
                    } else {
                        publisher.onError(task.getException());
                    }
                });

        return publisher;
    }

    public Observable<List<TaskEntity>> getAll() {

        PublishSubject<List<TaskEntity>> publisher = PublishSubject.create();

        db.collection("tasks")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<TaskEntity> lstEntidades = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            lstEntidades.add(document.toObject(TaskEntity.class));
                        }
                        publisher.onNext(lstEntidades);
                        publisher.onComplete();
                    } else {
                        publisher.onError(task.getException());
                    }
                });

        return publisher;

    }

}

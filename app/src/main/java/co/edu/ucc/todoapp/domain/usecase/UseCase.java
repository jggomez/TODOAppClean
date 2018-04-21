package co.edu.ucc.todoapp.domain.usecase;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<T> {

    private final Scheduler executorThread;
    private final Scheduler uiThread;

    public UseCase(Scheduler executorThread,
                   Scheduler uiThread) {
        this.executorThread = executorThread;
        this.uiThread = uiThread;
    }

    public void execute(DisposableObserver disposableObserver) {

        if (disposableObserver == null) {
            throw new IllegalArgumentException("El disposableObserver no es valido");
        }

        final Observable<T> observable =
                createObservable().subscribeOn(executorThread).observeOn(uiThread);

        observable.subscribeWith(disposableObserver);

    }

    public abstract Observable<T> createObservable();
}

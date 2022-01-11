package util;

import java.util.*;

public abstract class MyObservable {
    protected List<MyObserver> observers;

    public MyObservable() {
        observers = new ArrayList<>();
    }

    public void addObserver(MyObserver o) {
        observers.add(o);
    }

    public void removeObserver(MyObserver o) {
        observers.remove(o);
    }

    public abstract void notifyObservers();
}

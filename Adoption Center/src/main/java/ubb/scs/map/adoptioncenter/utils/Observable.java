package ubb.scs.map.adoptioncenter.utils;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Observer> observers =  new ArrayList<Observer>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public List<Observer> getObservers() {
        return observers;
    }
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
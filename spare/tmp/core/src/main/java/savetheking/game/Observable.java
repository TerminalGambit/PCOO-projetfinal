package savetheking.game;

public interface Observable {
    void addObserver(Observer observer);
    void notifyObservers();
}

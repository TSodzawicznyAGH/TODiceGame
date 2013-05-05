package pl.edu.agh.to1.dice.logic.commands;

public class ValueCommandResponse<T> implements CommandResponse {
    T value;

    public ValueCommandResponse(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}

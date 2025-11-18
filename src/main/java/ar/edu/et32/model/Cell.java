package ar.edu.et32.model;

/**
 * Representa una celda del tablero que puede estar vacía o contener un símbolo.
 */
public class Cell {
    public static final String EMPTY = "EMPTY";

    private String state = EMPTY;

    public void empty() {
        state = EMPTY;
    }

    public boolean mark(String symbol) {
        if (!isEmpty()) {
            return false;
        }
        state = symbol;
        return true;
    }

    public boolean isEmpty() {
        return EMPTY.equals(state);
    }

    public String getState() {
        return state;
    }
}

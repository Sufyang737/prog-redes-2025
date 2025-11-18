package ar.edu.et32.model;

/**
 * Representa a un jugador con nombre y símbolo.
 */
public class Player {
    private final String name;
    private final String symbol;

    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public boolean makeMove(Board board, int row, int col) {
        return board.placeSymbol(row, col, symbol);
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}

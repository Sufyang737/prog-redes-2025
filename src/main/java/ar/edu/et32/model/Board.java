package ar.edu.et32.model;

/**
 * Representa un tablero de tres por tres.
 */
public class Board {
    private static final int SIZE = 3;

    private final Cell[][] cells = new Cell[SIZE][SIZE];

    public Board() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new Cell();
            }
        }
    }

    public synchronized boolean placeSymbol(int row, int col, String symbol) {
        validateRange(row);
        validateRange(col);
        return cells[row][col].mark(symbol);
    }

    public synchronized void clearCell(int row, int col) {
        cells[row][col].empty();
    }

    public synchronized boolean hasWinner() {
        return getWinner() != null;
    }

    public synchronized boolean isDraw() {
        return isBoardFull() && !hasWinner();
    }

    public synchronized boolean isBoardFull() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public synchronized String getWinner() {
        // Filas
        for (int row = 0; row < SIZE; row++) {
            String posible = cells[row][0].getState();
            if (!Cell.EMPTY.equals(posible) &&
                posible.equals(cells[row][1].getState()) &&
                posible.equals(cells[row][2].getState())) {
                return posible;
            }
        }

        // Columnas
        for (int col = 0; col < SIZE; col++) {
            String posible = cells[0][col].getState();
            if (!Cell.EMPTY.equals(posible) &&
                posible.equals(cells[1][col].getState()) &&
                posible.equals(cells[2][col].getState())) {
                return posible;
            }
        }

        // Diagonales
        String center = cells[1][1].getState();
        if (!Cell.EMPTY.equals(center)) {
            if (center.equals(cells[0][0].getState()) && center.equals(cells[2][2].getState())) {
                return center;
            }
            if (center.equals(cells[0][2].getState()) && center.equals(cells[2][0].getState())) {
                return center;
            }
        }
        return null;
    }

    public synchronized String renderBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("    0   1   2\n");
        for (int row = 0; row < SIZE; row++) {
            sb.append(row).append("  ");
            for (int col = 0; col < SIZE; col++) {
                String value = cells[row][col].getState();
                sb.append(value.equals(Cell.EMPTY) ? " " : value);
                if (col < SIZE - 1) {
                    sb.append(" | ");
                }
            }
            if (row < SIZE - 1) {
                sb.append("\n   -----------\n");
            }
        }
        return sb.toString();
    }

    private void validateRange(int index) {
        if (index < 0 || index >= SIZE) {
            throw new IllegalArgumentException("La coordenada ingresada está fuera del tablero");
        }
    }
}

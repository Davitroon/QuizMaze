package model;

import logic.DBController;

/**
 * Class representing a Disposition in the maze.
 * It handles the internal matrix and can generate random elements
 * such as medkits and crocodiles, as well as save the matrix to the database.
 */
public class Disposition {

    private int id;
    private int MazeId;
    private int[][] map;

    /**
     * Constructs a Disposition with a given matrix and associated maze ID.
     * 
     * @param map2   Initial matrix representing the disposition.
     * @param MazeId ID of the associated maze.
     */
    public Disposition(int[][] map2, int MazeId) {
        this.MazeId = MazeId;
        this.map = map2;
    }

    /**
     * Sets the ID of the associated maze.
     * 
     * @param MazeId Maze ID to set.
     */
    public void setMazeId(int MazeId) {
        this.MazeId = MazeId;
    }

    /**
     * Generates a matrix by placing a specified number of medkits and crocodiles
     * randomly in empty cells.
     * The starting cell (0,0) and ending cell (bottom-right) are never filled.
     * 
     * @param numMedkits    Number of medkits to place.
     * @param numCrocodiles Number of crocodiles to place.
     * @throws IllegalArgumentException if there are not enough free cells to place all elements.
     */
    public void generateMatrix(int numMedkits, int numCrocodiles) {

        int validCells = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (!(x == 0 && y == 0) && !(x == map[0].length - 1 && y == map.length - 1) && map[y][x] == 0) {
                    validCells++;
                }
            }
        }

        if (numMedkits + numCrocodiles > validCells) {
            throw new IllegalArgumentException("Not enough free cells to place all elements");
        }

        int medkits = 0;
        int crocodiles = 0;
        boolean matrixComplete = false;

        // 0 - Empty, 1 - Medkit, 2 - Crocodile, 3 - Wall
        while (!matrixComplete) {
            int xRandom = (int) (Math.random() * map[0].length);
            int yRandom = (int) (Math.random() * map.length);

            if (!(xRandom == 0 && yRandom == 0) && !(xRandom == map[0].length - 1 && yRandom == map.length - 1)
                    && map[yRandom][xRandom] == 0) {

                double number = Math.random();

                if (number <= 0.50 && medkits < numMedkits) {
                    map[yRandom][xRandom] = 1;
                    medkits++;

                } else if (number <= 1 && crocodiles < numCrocodiles) {
                    map[yRandom][xRandom] = 2;
                    crocodiles++;
                }
            }

            if (medkits == numMedkits && crocodiles == numCrocodiles) {
                matrixComplete = true;
            }
        }
    }

    /**
     * Returns the internal map matrix.
     * 
     * @return 2D array representing the disposition matrix.
     */
    public int[][] getMap() {
        return map;
    }

    /**
     * Sets the internal map matrix.
     * 
     * @param map 2D array to set as the new matrix.
     */
    public void setMap(int[][] map) {
        this.map = map;
    }

    /**
     * Saves the current matrix to the database using the provided DBController.
     * Only non-empty cells are saved.
     * 
     * @param model DBController instance used to insert matrix values.
     */
    public void saveMatrix(DBController model) {
        for (int y = 0; y < map.length - 1; y++) {
            for (int x = 0; x < map[0].length - 1; x++) {
                if (map[y][x] != 0) {
                    model.insertDispositionMatrix(x, y, this.getId(), Integer.valueOf(map[y][x]));
                }
            }
        }
    }

    /**
     * Returns the ID of this disposition.
     * 
     * @return Disposition ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of this disposition.
     * 
     * @param id Disposition ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the associated maze.
     * 
     * @return Maze ID.
     */
    public int getMazeId() {
        return MazeId;
    }
}

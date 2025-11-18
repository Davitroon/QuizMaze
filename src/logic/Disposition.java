package logic;

/**
 * Disposition class that generates random matrices.
 */
public class Disposition {

	private int id;
	private int MazeId;
	private int[][] map;

	private Model model;

	public Disposition(int[][] map2, int MazeId, Model model) {
		this.MazeId = MazeId;
		this.map = map2;
		this.model = model;
	}

	public void setMazeId(int MazeId) {
		this.MazeId = MazeId;
	}

	/**
	 * Method to generate a matrix in the disposition. Generates a random cell and
	 * places an element inside if possible.
	 * 
	 * @param numMedkits    Number of medkits to generate.
	 * @param numCrocodiles Number of crocodiles to generate.
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

		// 0 - Empty. 1 - Medkit. 2 - Crocodile. 3 - Wall.
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

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * Method that reads the matrix and saves the positions where there is a special
	 * cell.
	 */
	public void saveMatrix() {
		for (int y = 0; y < map.length - 1; y++) {
			for (int x = 0; x < map[0].length - 1; x++) {
				if (map[y][x] != 0) {
					model.insertDispositionMatrix(x, y, this.getId(), Integer.valueOf(map[y][x]));
				}
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMazeId() {
		return MazeId;
	}
}

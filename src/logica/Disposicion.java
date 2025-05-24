package logica;

/**
 * Clase de disposicion que genera matrizes al azar.
 */
public class Disposicion {

	private int id;
	private int idLaberinto;
	private int [][] mapa;
	
	private Modelo modelo;
	
	
	public Disposicion(int[][] mapa, int idLaberinto, Modelo modelo) {
		this.idLaberinto = idLaberinto;
		this.mapa = mapa;
		this.modelo = modelo;
	}


	/**
	 * Método para generar una matriz en la disposición. Genera una casilla al azar y mete un elemento dentro si fuese posible.
	 * @param botiquinesGenerados Botiquines generados previamente.
	 * @param cocodrilosGenerados Cocodrilos generados previamente.
	 * @param numBotiquines Número de botiquines a generar.
	 * @param numCocodrilos Número de cocodrilos a generar.
	 */
	public void generarMatriz(int numBotiquines, int numCocodrilos) {
		
		int botiquines = 0;
		int cocodrilos = 0;
		boolean matrizCompleta = false;
		
		// 0 - Vacio. 1 - Botiquin. 2 - Cocodrilo. 3 - Muro.			
		while (!matrizCompleta) {
			int xRandom = (int) (Math.random() * mapa[0].length);
			int yRandom = (int) (Math.random() * mapa.length);
			
			// Validar que la casilla sea valida
			if (!(xRandom == 0 && yRandom == 0)                              // no (0,0)
					 && !(xRandom == mapa[0].length - 1 && yRandom == mapa.length - 1)               // no (última esquina)
					 && mapa[yRandom][xRandom] == 0) {  
				double numero = Math.random();
				
				// Meter elemento
				if (numero <= 0.50 && botiquines < numBotiquines) {
					mapa[yRandom][xRandom] = 1;
					botiquines++;
				 
				} else if (numero <= 1 && cocodrilos < numCocodrilos) {
					mapa[yRandom][xRandom] = 2;
					cocodrilos++;
					
				}
			}
			
			// Si se ha completado la matriz termina el bucle
			if (botiquines == numBotiquines && cocodrilos == numCocodrilos) {
				matrizCompleta = true;
			}
		}
	}
	
	
	/**
	 * Método que lee la matriz y guarda las posiciones donde haya una casilla especial
	 */
	public void guardarMatriz() {
		for (int y = 0; y < mapa.length - 1; y++) {
			for (int x = 0; x < mapa[0].length - 1; x++) {				
				// Si encuentra algo que no sea una casilla vacia (1: Cocodrilo, 2: Botiquín, 3: Muro)
				if (mapa[y][x] != 0) modelo.insertarDisposicionMatriz(x, y, this.getId(), mapa[y][x]);									
			}
		}
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getIdLaberinto() {
		return idLaberinto;
	}
}
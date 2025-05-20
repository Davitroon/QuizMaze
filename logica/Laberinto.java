package logica;

public class Laberinto {
	
	private int id;
	private int ancho;
	private int alto;
	private int numCocodrilos;
	private int danoCocodrilos;
	private int numBotiquines;
	private int vidaBotiquines;
	private int tiempoPregunta;
	private int dañoPregunta;
	private int numPreguntas;
	
	private int [][] mapa;

	public Laberinto(int ancho, int alto, int num_cocodrilos, int dano_cocodrilos, int num_botiquines,
			int vida_botiquines, int tiempo_pregunta, int daño_pregunta, int num_perguntas) {
		this.ancho = ancho;
		this.alto = alto;
		this.numCocodrilos = num_cocodrilos;
		this.danoCocodrilos = dano_cocodrilos;
		this.numBotiquines = num_botiquines;
		this.vidaBotiquines = vida_botiquines;
		this.tiempoPregunta = tiempo_pregunta;
		this.dañoPregunta = daño_pregunta;
		this.numPreguntas = num_perguntas;
		this.mapa = new int [ancho][alto];
	}

	
	public void setMapa(int[][] mapa) {
		this.mapa = mapa;
	}

	
	public int[][] getMapa() {
		return mapa;
	}
	
	
	public void generarDisposicion() {
		
		int botiquines;
		int cocodrilos;
		
		// 0 - Vacio. 1 - Botiquin. 2 - Cocodrilo. 3 - Muro.
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
				if (mapa[i][j] == 0) {
					double numero = Math.random();
					
					if (numero <= 0.15) {
						mapa[i][j] = 1;
					}
					
					if (numero > 0.15 && numero <= 0.30) {
						mapa[i][j] = 2;
					}
				}
			}
		}	
	}


	public int getAncho() {
		return ancho;
	}


	public void setAncho(int ancho) {
		this.ancho = ancho;
	}


	public int getAlto() {
		return alto;
	}


	public void setAlto(int alto) {
		this.alto = alto;
	}


	public int getNumCocodrilos() {
		return numCocodrilos;
	}


	public void setNumCocodrilos(int numCocodrilos) {
		this.numCocodrilos = numCocodrilos;
	}


	public int getDanoCocodrilos() {
		return danoCocodrilos;
	}


	public void setDanoCocodrilos(int danoCocodrilos) {
		this.danoCocodrilos = danoCocodrilos;
	}


	public int getNumBotiquines() {
		return numBotiquines;
	}


	public void setNumBotiquines(int numBotiquines) {
		this.numBotiquines = numBotiquines;
	}


	public int getVidaBotiquines() {
		return vidaBotiquines;
	}


	public void setVidaBotiquines(int vidaBotiquines) {
		this.vidaBotiquines = vidaBotiquines;
	}


	public int getTiempoPregunta() {
		return tiempoPregunta;
	}


	public void setTiempoPregunta(int tiempoPregunta) {
		this.tiempoPregunta = tiempoPregunta;
	}


	public int getDañoPregunta() {
		return dañoPregunta;
	}


	public void setDañoPregunta(int dañoPregunta) {
		this.dañoPregunta = dañoPregunta;
	}


	public int getNumPreguntas() {
		return numPreguntas;
	}


	public void setNumPreguntas(int numPerguntas) {
		this.numPreguntas = numPerguntas;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	
	
	
	
	
}

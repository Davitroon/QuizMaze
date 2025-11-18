package logic;

public class Player {
	
	private int id;
    private String nombre;
    private int x, y;
    private int vida;
    private int puntos; // Se ganan +10pts por respuesta correcta  y +Xpts por el sobrante de los botiquines
    
    public Player(String nombre) {
        this.nombre = nombre;
        this.vida=100; 
        this.puntos = 0;
        this.x = 0;
        this.y = 0;
    }
    ////////////GETTERS Y SETTERS
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
    /////////////////////////////
    

    public void moverA(int x, int y) {
        this.x = x;
        this.y = y;
    }

	public void reducirVida(int cantidad) {
        vida -= cantidad;
    }

	public int curar(int cantidad) {		// Asignamos la vida sobrante del botiquín como puntos!!
	    this.vida += cantidad;
	    if (this.vida > 100) {
	        int sobrante = this.vida - 100;
	        this.vida = 100;
	        return sobrante;
	    }
	    return 0;
	}

    public void sumarPuntos(int cantidad) {
        this.puntos += cantidad;
    }
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
    
    
    

    
}

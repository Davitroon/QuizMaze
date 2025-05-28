package logica;

public class Pregunta {
    private String enunciado;
    private String respuestaCorrecta;
    private String pista;

    public Pregunta(String enunciado, String respuestaCorrecta, String pista) {
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.pista = pista;
    }

    ////////////GETTERS Y SETTERS
	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(String respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public String getPista() {
		return pista;
	}

	public void setPista(String pista) {
		this.pista = pista;
	}
	///////////////////////////////////
    
}

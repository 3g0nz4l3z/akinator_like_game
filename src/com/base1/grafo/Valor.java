package com.base1.grafo;

public class Valor {
	
	private String etiqueta;
	private String pregunta;
	private int peso;

	public Valor(String etiqueta, String pregunta, int peso) {
		
		this.etiqueta = etiqueta;
		this.pregunta = pregunta;
		this.peso = peso;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

}

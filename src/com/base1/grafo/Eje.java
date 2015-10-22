package com.base1.grafo;

public class Eje {
	private Nodo destino;
	private String valor;
	private int peso;
	private String pregunta;
	
	public Eje(Nodo destino, String valor, int peso, String pregunta) {
		this.destino = destino;
		this.valor = valor;
		this.peso = peso;
		this.pregunta = pregunta;
	}

	public Nodo getDestino() {
		return destino;
	}

	public void setDestino(Nodo destino) {
		this.destino = destino;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

}

package com.base1.misc;

public class Caracteristica {
	private String variable;
	private String valor;
	private String pregunta;
	private int peso;

	public Caracteristica(String variable, String valor, String pregunta, int peso) {
		this.variable = variable;
		this.valor = valor;
		this.pregunta = pregunta;
		this.peso = peso;
	}

	/**
	 * @return the variable
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * @param variable
	 *            the variable to set
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the pregunta
	 */
	public String getPregunta() {
		return pregunta;
	}

	/**
	 * @param pregunta
	 *            the pregunta to set
	 */
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	/**
	 * @return the peso
	 */
	public int getPeso() {
		return peso;
	}

	/**
	 * @param peso
	 *            the peso to set
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	public void incrementOne(){
		this.peso++;
	}
	
	public void decrementOne(){
		if (this.peso != 1) {
			this.peso--;
		}
	}
}

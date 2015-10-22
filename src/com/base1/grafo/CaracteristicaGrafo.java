package com.base1.grafo;

import java.util.ArrayList;

public class CaracteristicaGrafo {
	private String caracteristica;
	private ArrayList<Valor> valores;
	
	public CaracteristicaGrafo(String caracteristica) {
		this.setCaracteristica(caracteristica);
		this.valores = new ArrayList<Valor>();
	}

	public void setValor(Valor valor) {
		valores.add(valor);
	}

	public ArrayList<Valor> getValores()
	{
		return valores;
	}
	/**
	 * @return the caracteristica
	 */
	public String getCaracteristica() {
		return caracteristica;
	}

	/**
	 * @param caracteristica the caracteristica to set
	 */
	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}
	
}

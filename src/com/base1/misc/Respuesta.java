package com.base1.misc;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Respuesta {
	private String id_topico;
	private String id_respuesta;
	private int ranking;
	private ArrayList<Caracteristica> caracteristicas;

	public Respuesta(String id_topico, String id_respuesta, int ranking) {
		this.id_topico = id_topico;
		this.id_respuesta = id_respuesta;
		this.ranking = ranking;
		this.caracteristicas = new ArrayList<Caracteristica>();
	}

	/**
	 * @return the id_topico
	 */
	public String getId_topico() {
		return id_topico;
	}

	/**
	 * @param id_topico
	 *            the id_topico to set
	 */
	public void setId_topico(String id_topico) {
		this.id_topico = id_topico;
	}

	/**
	 * @return the id_respuesta
	 */
	public String getId_respuesta() {
		return id_respuesta;
	}

	/**
	 * @param id_respuesta
	 *            the id_respuesta to set
	 */
	public void setId_respuesta(String id_respuesta) {
		this.id_respuesta = id_respuesta;
	}

	/**
	 * @return the ranking
	 */
	public int getRanking() {
		return ranking;
	}

	/**
	 * @param ranking
	 *            the ranking to set
	 */
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	
	/**
	 * @return the caracteristicas
	 */
	public Caracteristica getCaracteristica(String variable) {
		for (Caracteristica caracteristica : caracteristicas) {
			if (caracteristica.getVariable().equals(variable)) {
				return caracteristica;
			}
		}
		return null;
	}

	/**
	 * @return the caracteristicas
	 */
	public Caracteristica getCaracteristica(int index) {
		return this.caracteristicas.get(index);
	}
	
	public ArrayList<Caracteristica> getCaracteristicas(){
		return this.caracteristicas;
	}
	
	/**
	 * @param caracteristicas
	 *            the caracteristicas to set
	 */
	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristicas.add(caracteristica);
	}

	public void incrementOne(){
		this.ranking++;
	}
	
	public void decrementOne(){
		if (this.ranking != 1) {
			this.ranking--;
		}
	}
}

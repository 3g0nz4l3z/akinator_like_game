package com.base1.grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.base1.sql.Conslutor;

public class GrafoDN {
	// private Nodo raiz;

	private ArrayList<Nodo> nodos;

	public GrafoDN() {
		nodos = new ArrayList<Nodo>();
	}

	/**
	 * Agrega un nodo al grafo de no existir este ya
	 * @param caracteristica
	 * @return
	 */
	public boolean set(String caracteristica) {
		if (nodos.size() == 0) {
			return nodos.add(new Nodo(caracteristica));
		}

		for (Nodo nodo : nodos) {
			if (nodo.getCaracteristica().equals(caracteristica)) {
				return false;
			}
		}
		return nodos.add(new Nodo(caracteristica));
	}

	public Nodo get(String caracteristica){
		for (Nodo nodo : nodos) {
			if (nodo.getCaracteristica().equals(caracteristica)) {
				return nodo;
			}
		}
		return null;
	}
	
	/**
	 * Agrega un eje de existir sus nodos
	 * @param cOrigen
	 * @param cDestino
	 * @return
	 */
	public boolean set(String cOrigen, String cDestino, String valor, int peso, String pregunta)
	{	
		Nodo nOrigen = get(cOrigen);
		Nodo nDestino = get(cDestino);
		if (nOrigen!=null && nDestino != null) {
			nOrigen.setEje(nDestino, valor, peso, pregunta);
		}
		return false;
	}
	
	/**
	 * Devuelve la primera ocurrencia de este eje
	 * @param cOrigen
	 * @param cDestino
	 * @return
	 */
	public Eje get(String cOrigen, String cDestino)
	{
		for (Nodo nodo : nodos) {
			if (nodo.getCaracteristica().equals(cOrigen)) {
				ArrayList<Eje> ejes = nodo.getEjes();
				for (Eje eje : ejes) {
					if (eje.getDestino().getCaracteristica().equals(cDestino)) {
						return eje;
					}
				}
			}
		}
		return null;
	}
	
	// public void CrearGrafoDN(String topico) {
	// // 1 - Cargo las caracteristicas
	// Conslutor c = new Conslutor();
	// c.conectar();
	// ArrayList<CaracteristicaGrafo> csg = c.consultaCaracteristicasG(topico);
	// // 2 - Entrevero las caracteristicas
	// Collections.shuffle(csg);
	//
	// // Ahora entro al metodo recursivo
	// this.raiz.armarGrafo(csg);
	// }
	
	public void armarGrafoDN(String topico)
	{
		// 1 - Cargo las caracteristicas
		ArrayList<CaracteristicaGrafo> csg = new Conslutor().consultaCaracteristicasG(topico);
		
		//2 - Entrevero las caracteristicas
		Collections.shuffle(csg);
		
		//3 - Coloco los nodos en el grafo
		for (CaracteristicaGrafo caracteristicaGrafo : csg) {
			this.set(caracteristicaGrafo.getCaracteristica());
		}
		
		//4 - inicio el metodo interno
		armarGrafoDNInterno(csg);
	}
	
	private void armarGrafoDNInterno(ArrayList<CaracteristicaGrafo> csg) {
		if (csg.size() == 0) {
			return;
		}
		
		CaracteristicaGrafo cgAux = csg.remove(0);
		Nodo nodoAux = this.get(cgAux.getCaracteristica());
		for (Valor valor : cgAux.getValores()) {
			Collections.shuffle(csg);
			String randCarac = csg.get(0).getCaracteristica();
			this.set(nodoAux.getCaracteristica(), randCarac , valor.getEtiqueta(), valor.getPeso(), valor.getPregunta());
		}
	}

	public static void main(String[] args) {
		GrafoDN grafoDN = new GrafoDN();
		grafoDN.armarGrafoDN("materia");
		System.out.println(grafoDN);
		System.out.println();
	}

}

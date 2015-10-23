package com.base1.test;

import java.util.ArrayList;

import com.base1.grafo.Nodo;
import com.base1.misc.Tupla;
import com.base1.sql.Consultor;

public class MainTest {
	
	public static void main(String[] args) {
		ArrayList<String> cs = new Consultor().consultarPorCaracteristicas("materia");
		
		Nodo arbolD = new Nodo();
		
		arbolD.armarArbolD(cs,new ArrayList<Tupla>());
		
		
		System.out.println("");
	}
	
}

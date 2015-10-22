package com.base1.test;

import java.util.ArrayList;

import com.base1.grafo.CaracteristicaGrafo;
import com.base1.grafo.Nodo;
import com.base1.sql.Conslutor;

public class MainTest {
	
	public static void main(String[] args) {
		ArrayList<CaracteristicaGrafo> csg = new Conslutor()
				.consultaCaracteristicasG("animal");
		
		Nodo arbolD = new Nodo();
		arbolD.armarArbolD(csg);
		System.out.println("hoja");
		
		
	}
	
}

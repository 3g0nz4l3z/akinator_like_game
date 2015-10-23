package com.base1.juego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.base1.grafo.Valor;
import com.base1.misc.Tupla;
import com.base1.sql.Consultor;

import jline.ConsoleReader;

public class Juego {
	private ConsoleReader consoleReader;
	private Consultor consultor;
	public Juego()
	{
		this.consultor = new Consultor();
		try {
			this.consoleReader = new ConsoleReader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void iniciar()
	{
		ArrayList<String> ts = consultor.consultarPorTopicos();
		String elejirTopico = "Los topicos son: ";
		for (int i = 0; i < ts.size(); i++) {
			elejirTopico+="\n "+i+" - "+ts.get(i);
		}
		mensage(elejirTopico);
		int num = Integer.parseInt(this.input("Presione el valor numerico correspondiente y presione enter: "));
		
		String topico = ts.get(num);
		
		ArrayList<Tupla> tuplas = new ArrayList<>();
		ArrayList<String> cs = consultor.consultarPorCaracteristicas(topico);
		
		while (cs.size() > 0) {
			String cAux = randString(cs);
			ArrayList<Valor> valores = consultor.consultarPorValores(cAux, tuplas);
			Valor vRand = randValor(valores);
			String sValor = vRand.getEtiqueta();
			
			String pregunta = String.format(vRand.getPregunta(), vRand.getEtiqueta());
			String key = input(pregunta);
		
			switch (key) {
			case "si":
				break;
			case "no":
				break;
			case "casi":
				break;
			case "no se":
				break;
			default:
				break;
			}
			
			tuplas.add(new Tupla(cAux,sValor)); // para el arbol			
		}
		
	}
	
	private Valor randValor(ArrayList<Valor> valores) {
		// TODO Auto-generated method stub
		ArrayList<Valor> vsAux = new ArrayList<>();
		for (Valor valor : valores) {
			int psAux = valor.getPeso();
			while (psAux != 0) {
				vsAux.add(valor);
				psAux--;
			}
		}
		int index = new Random().nextInt(vsAux.size());
		return vsAux.remove(index);
	}

	private String randString(ArrayList<String> array) {
		// TODO Auto-generated method stub
		int index = new Random().nextInt(array.size());
		return array.remove(index);
	}

	public void mensage(String mensage)
	{
		System.out.println(mensage);
	}
	
	public String input(String message)
	{
		try {
			return consoleReader.readLine(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public void clear()
	{
		try {
			consoleReader.clearScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

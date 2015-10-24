package com.base1.juego;

import java.awt.RenderingHints.Key;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import com.base1.grafo.Valor;
import com.base1.misc.Caracteristica;
import com.base1.misc.Respuesta;
import com.base1.misc.Tupla;
import com.base1.sql.Consultor;

import jline.ConsoleReader;

public class Juego {
	private ConsoleReader consoleReader;
	private Consultor consultor;

	public Juego() {
		this.consultor = new Consultor();
		try {
			this.consoleReader = new ConsoleReader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void iniciar() {
		ArrayList<String> ts = consultor.consultarPorTopicos();
		String elejirTopico = "Los topicos son: ";
		for (int i = 0; i < ts.size(); i++) {
			elejirTopico += "\n " + i + " - " + ts.get(i);
		}
		mensage(elejirTopico);

		int num = Integer.parseInt(this.input("Presione el valor numerico correspondiente y presione enter: "));

		String topico = ts.get(num);

		ArrayList<Tupla> tuplas = new ArrayList<>();

		ArrayList<Respuesta> respuestas = consultor.consultarPorRespuestas(topico);

		ArrayList<String> cs = consultor.consultarPorCaracteristicas(topico);

		ArrayList<Tupla> csRespuesta = new ArrayList<Tupla>();

		while (cs.size() > 0) {
			String cAux = randString(cs);
			ArrayList<Valor> valores = consultor.consultarPorValores(cAux, tuplas);
			Valor vRand = randValor(valores);
			String sValor = vRand.getEtiqueta();

			String pregunta = String.format(vRand.getPregunta(), vRand.getEtiqueta());
			String key = input(pregunta);

			switch (key) {
			case "si":
				// incr peso
				// vajo un hijo
				// guardo (carac,valor)
				Tupla tAux = new Tupla(cAux, sValor);
				tuplas.add(tAux); // vajo un hijo
				incrCracteristica(respuestas, tAux);
				csRespuesta.add(tAux);
				break;
			case "no":
				// decr peso de no ser 1
				// hago otra pregunta de otro hijo de un hermano
				vRand = randValor(valores);
				sValor = vRand.getEtiqueta();
				decrCaracteristica(respuestas, new Tupla(cAux, sValor));
				break;
			case "casi":
				tuplas.add(new Tupla(cAux, sValor)); // vajo un hijo
				break;
			case "no se":
				// hago otra pregunta de otro hijo de un hermano
				vRand = randValor(valores);
				sValor = vRand.getEtiqueta();
				tuplas.add(new Tupla(cAux, sValor));
				break;
			default:
				break;
			}
		}

		ArrayList<String> sRespuestas = consultor.consultarPorRespuestas(tuplas, topico);
		String sRespuesta = "";
		boolean bSeguirPreguntando = true;
		boolean bPerdedor = false;
		while (bSeguirPreguntando) {
			if (sRespuestas.size() > 0) {
				sRespuesta = randRespuesta(respuestas, sRespuestas);
				String key = input("Es " + sRespuesta + " en lo que estas pensado si o no?");
				switch (key) {
				case "si":
					System.out.println("Gane, gracias por jugar");
					actualizarRespuesta(respuestas, sRespuesta);
					bSeguirPreguntando = false;
					break;
				default:
					sRespuestas.remove(sRespuesta);
					bSeguirPreguntando = false;
					bPerdedor = true;
					break;
				}
			}
		}

		if (bPerdedor) {
			System.out.println("Me rindo!!!");

			System.out.println();

			String in = "";
			if (sRespuestas.size() > 0) {

				System.out.println("Era uno de estos?: ");
				String pregunta = "";
				for (int i = 0; i < sRespuestas.size(); i++) {
					pregunta += "\n " + i + " - " + sRespuestas.get(i);
				}
				System.out.println(pregunta);

				in = input("Presione el numero correspondiente o '+' para nueva respuesta:");

			} else {
				in = input("Presiona '+' para agregar una nueva respuesta:");
			}
			if (in.equals("+")) {
				String sNRespuea = input("En que estabas pensando:");
				Respuesta nRespuesta = new Respuesta(topico, sNRespuea, 1);
				cs = consultor.consultarPorCaracteristicas(topico);
				for (String variable : cs) {
					String valor = input("<como es/son> " + variable + " ?");
					ArrayList<Valor> valores = consultor.consultarPorValores(variable, new ArrayList<Tupla>());
					Caracteristica cAux = new Caracteristica(variable, valor, valores.get(0).getPregunta(), 1);
					nRespuesta.setCaracteristica(cAux);
				}

				consultor.agregarRespuesta(nRespuesta);

			} else {
				num = Integer.parseInt(in);
				for (Respuesta respuesta : respuestas) {
					if (sRespuestas.get(num).equals(respuesta.getId_respuesta())) {
						respuesta.incrementOne();
					}

				}
			}
		}
		consultor.actualizarDatos(respuestas);
	}

	private void actualizarRespuesta(ArrayList<Respuesta> respuestas, String sRespuesta) {
		// TODO Auto-generated method stub
		for (Respuesta respuesta : respuestas) {
			if (sRespuesta.equals(respuesta.getId_respuesta())) {
				respuesta.incrementOne();
			}
		}
	}

	private String randRespuesta(ArrayList<Respuesta> respuestas, ArrayList<String> sRespuestas) {
		ArrayList<String> respuestasPorRanking = new ArrayList<>();
		for (String sRespuesta : sRespuestas) {
			for (Respuesta respuesta : respuestas) {
				if (sRespuesta.equals(respuesta.getId_respuesta())) {
					int aux = respuesta.getRanking();
					while (aux > 0) {
						respuestasPorRanking.add(sRespuesta);
						aux--;
					}
				}
			}
		}
		int index = new Random().nextInt(respuestasPorRanking.size());
		return respuestasPorRanking.remove(index);
	}

	public void incrCracteristica(ArrayList<Respuesta> respuestas, Tupla tCarac) {
		for (Respuesta respuesta : respuestas) {
			ArrayList<Caracteristica> cs = respuesta.getCaracteristicas();
			for (Caracteristica caracteristica : cs) {
				if (caracteristica.getVariable().equals(tCarac.get(0))
						&& caracteristica.getValor().equals(tCarac.get(1))) {
					caracteristica.incrementOne();
				}
			}
		}
	}

	public void decrCaracteristica(ArrayList<Respuesta> respuestas, Tupla tCarac) {
		for (Respuesta respuesta : respuestas) {
			ArrayList<Caracteristica> cs = respuesta.getCaracteristicas();
			for (Caracteristica caracteristica : cs) {
				if (caracteristica.getVariable().equals(tCarac.get(0))
						&& caracteristica.getValor().equals(tCarac.get(1))) {
					caracteristica.decrementOne();
				}
			}
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

	public void mensage(String mensage) {
		System.out.println(mensage);
	}

	// public String input(String message)
	// {
	// try {
	// return consoleReader.readLine(message);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return "";
	// }

	public String input(String mensage) {
		String string;
		System.out.println(mensage);
		string = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			string = bufferedReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}

	public void clear() {
		try {
			consoleReader.clearScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

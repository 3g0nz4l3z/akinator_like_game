package com.base1.sql;

import java.sql.*;
import java.util.ArrayList;

import com.base1.grafo.CaracteristicaGrafo;
import com.base1.grafo.Valor;
import com.base1.misc.Caracteristica;
import com.base1.misc.Respuesta;
import com.base1.misc.Tupla;

/**
 * Esta clase tiene todas las consultas necesarias que cliente genio necesita,
 * fue basada en el codigo de esta pagina
 * http://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm
 * 
 * @author exequiel
 *
 */
public class Consultor {
	private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/db_genio";
	private String user = "root";
	private String password = "root";
	private Connection connection;
	private String consultaRespuestaPorTopico = "select respuesta.id_respuesta, respuesta.ranking, caracteristica.caracteristica, caracteristica.valor, caracteristica.peso, caracteristica.pregunta from respuesta left join caracteristica on caracteristica.id_caracteristica = respuesta.id_respuesta where respuesta.id_topico = ? order by respuesta.id_respuesta";
	private String consultaActualizarRespuesta = "update respuesta set ranking = ? where id_respuesta = ?";
	private String consultaActualizarCaracteristica = "update caracteristica set peso = ? where id_caracteristica = ? and caracteristica = ?";
	private String consultaPorCaracteristicas = "select distinct caracteristica from caracteristica where  id_caracteristica in (select id_respuesta from respuesta where id_topico = ?)";
	private String consultaPorPropsDeCarac = "select distinct valor, pregunta, peso from caracteristica where caracteristica = ?";
	private String consultarPorTopicos = "select id_topico from topico";

	public Consultor() {
		try {
			Class.forName(JDBC_DRIVER).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void conectar() {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("no se pudo conectar");
			e.printStackTrace();
		}
	}

	public void cerrar() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet consulta(String unaConsulta) {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(unaConsulta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultSet;
	}

	public ArrayList<Respuesta> consultarPorRespuestas(String topico) {
		this.conectar();
		ArrayList<Respuesta> respuestas = new ArrayList<Respuesta>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Respuesta respAux = null;
		try {
			statement = connection.prepareStatement(consultaRespuestaPorTopico);
			statement.setString(1, topico);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String id_respuesta = resultSet.getString("id_respuesta");
				int ranking = resultSet.getInt("ranking");
				String caracteristica = resultSet.getString("caracteristica");
				String valor = resultSet.getString("valor");
				String pregunta = resultSet.getString("pregunta");
				int peso = resultSet.getInt("peso");
				// Si la respuesta no esta insertada insertar, si esta
				// insertada agregar caracteristica
				if (respAux != null) {
					// Si sigo obteniendo datos de una respuesta simplemente
					// agrego caracteristica
					if (respAux.getId_respuesta().equals(id_respuesta)) {
						respAux.setCaracteristica(new Caracteristica(caracteristica, valor, pregunta, peso));
					} else {
						// si obtengo una nueva respues guardo la anterior y
						// creo esta nueva respuesta
						respuestas.add(respAux);
						respAux = new Respuesta(topico, id_respuesta, ranking);
						respAux.setCaracteristica(new Caracteristica(caracteristica, valor, pregunta, peso));
					}
				} else {
					// No tengo una primera respuesta
					respAux = new Respuesta(topico, id_respuesta, ranking);
					respAux.setCaracteristica(new Caracteristica(caracteristica, valor, pregunta, peso));
				}
			}
			// Agrego la ultima respuesta
			respuestas.add(respAux);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return respuestas;

	}

	public ArrayList<CaracteristicaGrafo> consultaCaracteristicasG(String topico) {
		ArrayList<CaracteristicaGrafo> csg = new ArrayList<CaracteristicaGrafo>();

		this.conectar();
		ArrayList<String> cString = new ArrayList<String>();
		PreparedStatement statment = null;
		ResultSet resultSet = null;

		try {
			statment = connection.prepareStatement(consultaPorCaracteristicas);
			statment.setString(1, topico);
			resultSet = statment.executeQuery();

			while (resultSet.next()) {
				String caracAux = resultSet.getString("caracteristica");
				cString.add(caracAux);

			}

			for (String caracteristica : cString) {
				statment = connection.prepareStatement(consultaPorPropsDeCarac);
				statment.setString(1, caracteristica);
				resultSet = statment.executeQuery();
				CaracteristicaGrafo cg = new CaracteristicaGrafo(caracteristica);
				while (resultSet.next()) {
					String etiqueta = resultSet.getString("valor");
					String pregunta = resultSet.getString("pregunta");
					int peso = resultSet.getInt("peso");

					cg.setValor(new Valor(etiqueta, pregunta, peso));
				}
				csg.add(cg);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statment.close();
				resultSet.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return csg;
	}

	public void actualizarDatos(ArrayList<Respuesta> respuestas) {
		this.conectar();
		PreparedStatement sttmntRespuesta = null;
		PreparedStatement sttmntCaracteristica = null;

		try {
			sttmntRespuesta = connection.prepareStatement(consultaActualizarRespuesta);
			sttmntCaracteristica = connection.prepareStatement(consultaActualizarCaracteristica);
			for (Respuesta respuesta : respuestas) {
				sttmntRespuesta.setInt(1, respuesta.getRanking());
				sttmntRespuesta.setString(2, respuesta.getId_respuesta());
				sttmntRespuesta.executeUpdate();
				for (Caracteristica caracteristica : respuesta.getCaracteristicas()) {
					sttmntCaracteristica.setInt(1, caracteristica.getPeso());
					sttmntCaracteristica.setString(2, respuesta.getId_respuesta());
					sttmntCaracteristica.setString(3, caracteristica.getVariable());
					sttmntCaracteristica.executeUpdate();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				sttmntCaracteristica.close();
				sttmntRespuesta.close();
				connection.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public ArrayList<String> consultarPorCaracteristicas(String topico) {
		// TODO Auto-generated method stub
		this.conectar();
		ArrayList<String> cString = new ArrayList<String>();
		PreparedStatement statment = null;
		ResultSet resultSet = null;

		try {
			statment = connection.prepareStatement(consultaPorCaracteristicas);
			statment.setString(1, topico);
			resultSet = statment.executeQuery();
			while (resultSet.next()) {
				String caracAux = resultSet.getString("caracteristica");
				cString.add(caracAux);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statment.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return cString;
	}

	public ArrayList<Valor> consultarPorValores(String cAux, ArrayList<Tupla> tuplas) {
		this.conectar();
		ArrayList<Valor> valores = new ArrayList<Valor>();
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		String sQuery = crearQuery(cAux, tuplas); // es una query muy especial

		try {
			statment = connection.prepareStatement(sQuery);
			resultSet = statment.executeQuery();

			while (resultSet.next()) {
				String etiqueta = resultSet.getString("valor");
				String pregunta = resultSet.getString("pregunta");
				int peso = resultSet.getInt("peso");
				valores.add(new Valor(etiqueta, pregunta, peso));

			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				statment.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return valores;
	}

	public ArrayList<String> consultarPorRespuestas(ArrayList<Tupla> tuplas, String topico) {
		this.conectar();
		ArrayList<String> valores = new ArrayList<String>();
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		String sQuery = crearQuery(tuplas, topico); // es una query muy especial

		try {
			statment = connection.prepareStatement(sQuery);
			resultSet = statment.executeQuery();

			while (resultSet.next()) {
				String etiqueta = resultSet.getString("id_respuesta");
				valores.add(etiqueta);

			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				statment.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return valores;
	}

	private String crearQuery(String caracteristica, ArrayList<Tupla> tuplas) {

		String query = "select distinct valor, pregunta, peso from caracteristica where caracteristica = '"
				+ caracteristica + "'";

		if (tuplas.size() > 0) {
			ArrayList<String> clausules = new ArrayList<String>();
			for (Tupla tupla : tuplas) {
				clausules
						.add(" and id_caracteristica in (select id_caracteristica from caracteristica where caracteristica = '"
								+ tupla.get(0) + "' and valor = '" + tupla.get(1) + "')");
			}
			for (String clausula : clausules) {
				query += clausula;
			}
		}
		return query;
	}

	private String crearQuery(ArrayList<Tupla> tuplas, String topico) {

		String query = "select id_respuesta from respuesta where id_topico = '" + topico + "'";

		if (tuplas.size() > 0) {
			ArrayList<String> clausules = new ArrayList<String>();
			for (Tupla tupla : tuplas) {
				clausules
						.add(" and id_respuesta in (select id_caracteristica from caracteristica where caracteristica = '"
								+ tupla.get(0) + "' and valor = '" + tupla.get(1) + "')");
			}
			for (String clausula : clausules) {
				query += clausula;
			}
		}
		return query;
	}

	public ArrayList<String> consultarPorTopicos() {
		this.conectar();
		ArrayList<String> tipicos = new ArrayList<String>();
		PreparedStatement statment = null;
		ResultSet resultSet = null;
		try {
			statment = connection.prepareStatement(consultarPorTopicos);
			resultSet = statment.executeQuery();
			while (resultSet.next()) {
				String caracAux = resultSet.getString("id_topico");
				tipicos.add(caracAux);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statment.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tipicos;
	}

	public void agregarRespuesta(Respuesta respuestaNueva) {
		String queryRespuesta = "INSERT INTO respuesta(id_respuesta, id_topico, ranking) VALUES (?, ?, ?)";
		String queryCaracteristica = "INSERT INTO caracteristica(id_caracteristica, caracteristica, valor, pregunta, peso) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement statment = null;
		try {
			statment = connection.prepareStatement(queryRespuesta);
			statment.setString(1, respuestaNueva.getId_respuesta());
			statment.setString(2, respuestaNueva.getId_topico());
			statment.setInt(3, respuestaNueva.getRanking());
			statment.executeUpdate();

			ArrayList<Caracteristica> caracteristicas = respuestaNueva.getCaracteristicas();
			for (Caracteristica caracteristica : caracteristicas) {
				statment = connection.prepareStatement(queryCaracteristica);
				statment.setString(1, respuestaNueva.getId_respuesta());
				statment.setString(2, caracteristica.getVariable());
				statment.setString(3, caracteristica.getValor());
				statment.setString(4, caracteristica.getPregunta());
				statment.setInt(5, caracteristica.getPeso());
				statment.executeUpdate();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statment.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

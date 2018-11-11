package ar.edu.unq.inscripcionunq.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraWebServiceJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EstudianteWebServiceJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaWebServiceJson;
import ar.edu.unq.inscripcionunq.spring.exception.ConexionWebServiceException;
import ar.edu.unq.inscripcionunq.spring.exception.EncuestaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Equivalencia;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

@Service
@Transactional
public class WebService {
	@Autowired
	public MateriaService materiaServiceImp;

	@Autowired
	public CarreraService carreraServiceImp;

	@Autowired
	public EstudianteService estudianteServiceImp;

	@Autowired
	public EncuestaService encuestaServiceImp;

	@Autowired
	public EquivalenciaService equivalenciaServiceImp;

	public void importarEstudiantes(Long idEncuesta) throws ConexionWebServiceException, EncuestaNoExisteException {
		String response = null;
		try {
			response = Unirest.post("http://gastosfamiliares.esy.es/prueba2.json")
					.header("Content-Type", "application/json").header("cache-control", "no-cache").asString()
					.getBody();
		} catch (UnirestException e) {
			throw new ConexionWebServiceException();
		}
		JsonElement mJson = new JsonParser().parse(response);
		Encuesta encuesta = null;
		try {
			encuesta = encuestaServiceImp.get(idEncuesta);
		} catch (ObjectoNoEncontradoEnBDException e) {
			throw new EncuestaNoExisteException();
		}

		EstudianteWebServiceJson[] estudiantesWebServiceJson = new Gson().fromJson(mJson,
				EstudianteWebServiceJson[].class);

		List<Equivalencia> equivalencias = equivalenciaServiceImp.list();

		Map<String, Materia> materias = new HashMap<String, Materia>();
		Map<String, Carrera> carreras = new HashMap<String, Carrera>();

		for (EstudianteWebServiceJson estudianteWebServiceJson : estudiantesWebServiceJson) {
			Estudiante estudianteNuevo = new Estudiante(estudianteWebServiceJson.datos_personales.nombre,
					estudianteWebServiceJson.datos_personales.apellido, estudianteWebServiceJson.datos_personales.dni,
					estudianteWebServiceJson.datos_personales.email);
			for (CarreraWebServiceJson carreraWebServiceJson : estudianteWebServiceJson.carreras) {
				if (!carreras.containsKey(carreraWebServiceJson.codigo)) {
					carreras.put(carreraWebServiceJson.codigo,
							carreraServiceImp.getCarreraPorCodigo(carreraWebServiceJson.codigo));
				}
				estudianteNuevo.agregarInscripcionACarrera(carreras.get(carreraWebServiceJson.codigo));
			}
			for (MateriaWebServiceJson materiaWebServiceJson : estudianteWebServiceJson.cursadas) {
				if (materiaWebServiceJson.materia.codigo != null) {
					if (!materias.containsKey(materiaWebServiceJson.materia.getCodigoCerosAIzquierda())) {
						materias.put(materiaWebServiceJson.materia.getCodigoCerosAIzquierda(), materiaServiceImp
								.getMateriaPorCodigo(materiaWebServiceJson.materia.getCodigoCerosAIzquierda()));
					}
					Materia materiaAprobada = materias.get(materiaWebServiceJson.materia.getCodigoCerosAIzquierda());
					estudianteNuevo.agregarMateriaAprobada(materiaAprobada);
					List<Materia> materiasEquivalentes = equivalencias.stream()
							.filter(e -> e.esEquivalente(materiaAprobada))
							.map(eq -> eq.getEquivalencia(materiaAprobada)).collect(Collectors.toList());
					materiasEquivalentes.stream().forEach(mE -> estudianteNuevo.agregarMateriaAprobada(mE));
				}
			}
			encuesta.agregarEstudiante(estudianteNuevo);
		}
		encuestaServiceImp.save(encuesta);
	}
}
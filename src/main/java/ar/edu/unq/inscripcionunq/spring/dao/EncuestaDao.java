package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import ar.edu.unq.inscripcionunq.spring.exception.NoExistenUsuariosEnEncuestaException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

public interface EncuestaDao extends GenericDao<Encuesta> {
	
	List<Encuesta> getTodasLasEncuestasActivasParaDni(String dni);

	Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idEncuesta) throws NoExistenUsuariosEnEncuestaException;

	Estudiante getDatosDeUsuarioDesdeEncuesta(String email) throws UsuarioNoExisteException;

	List<Encuesta> getEncuestasDeUnaComision(Long idComision);

	Encuesta getEncuestaConNombre(String nombre);

	Number nroDeAlumnosQueCompletaronEncuesta(Long id);
}
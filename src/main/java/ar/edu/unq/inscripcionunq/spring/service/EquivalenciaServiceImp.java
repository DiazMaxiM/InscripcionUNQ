package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EquivalenciaJson;
import ar.edu.unq.inscripcionunq.spring.dao.EquivalenciaDao;
import ar.edu.unq.inscripcionunq.spring.exception.MateriasEquivalentesException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectoNoEncontradoEnBDException;
import ar.edu.unq.inscripcionunq.spring.model.Equivalencia;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional
public class EquivalenciaServiceImp extends GenericServiceImp<Equivalencia> implements EquivalenciaService {

	@Autowired
	EquivalenciaDao equivalenciaDaoImp;
	
	@Autowired
	MateriaService materiaServiceImp;

	@Override
	public List<Equivalencia> getEquivalencias() {
		return equivalenciaDaoImp.list();
	}

	@Override
	public void agregarNuevaEquivalencia(EquivalenciaJson equivalenciaJson) throws ObjectoNoEncontradoEnBDException, MateriasEquivalentesException {
		Materia materiaOrigen = materiaServiceImp.get(equivalenciaJson.materiaOrigen.id);
		Materia materiaDestino = materiaServiceImp.get(equivalenciaJson.materiaDestino.id);
		Validacion.validarEquivalencia(materiaOrigen, materiaDestino);
		equivalenciaDaoImp.save(new Equivalencia(materiaOrigen, materiaDestino));
	}

	@Override
	public void actualizarEquivalencia(EquivalenciaJson equivalenciaJson) throws ObjectoNoEncontradoEnBDException, MateriasEquivalentesException {
		Equivalencia equivalencia = equivalenciaDaoImp.get(equivalenciaJson.id);
		Materia materiaOrigen = materiaServiceImp.get(equivalenciaJson.materiaOrigen.id);
		Materia materiaDestino = materiaServiceImp.get(equivalenciaJson.materiaDestino.id);
		equivalencia.setMateriaOrigen(materiaOrigen);
		equivalencia.setMateriaDestino(materiaDestino);
		Validacion.validarEquivalencia(materiaOrigen, materiaDestino);
		equivalenciaDaoImp.update(equivalencia);
	}
}
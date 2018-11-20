package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EquivalenciaJson;
import ar.edu.unq.inscripcionunq.spring.dao.EquivalenciaDao;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteEquivalenciaException;
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
	public void agregarNuevaEquivalencia(EquivalenciaJson equivalenciaJson) throws ObjectoNoEncontradoEnBDException, MateriasEquivalentesException, ExisteEquivalenciaException {
		Materia materiaOrigen = materiaServiceImp.get(equivalenciaJson.materiaOrigen.id);
		Materia materiaDestino = materiaServiceImp.get(equivalenciaJson.materiaDestino.id);
		Validacion.validarEquivalencia(materiaOrigen, materiaDestino);
		this.validarSiExisteEquivalencia(materiaOrigen, materiaDestino);
		equivalenciaDaoImp.save(new Equivalencia(materiaOrigen, materiaDestino));
	}

	private void validarSiExisteEquivalencia(Materia materiaOrigen, Materia materiaDestino) throws ExisteEquivalenciaException {
		Equivalencia equivalencia = equivalenciaDaoImp.obtenerEquivalencia(materiaOrigen,materiaDestino);
		if(equivalencia != null) {
			throw new ExisteEquivalenciaException();
		}
	
	}

	@Override
	public void actualizarEquivalencia(EquivalenciaJson equivalenciaJson) throws ObjectoNoEncontradoEnBDException, MateriasEquivalentesException, ExisteEquivalenciaException {
		Equivalencia equivalencia = equivalenciaDaoImp.get(equivalenciaJson.id);
		Materia materiaOrigen = materiaServiceImp.get(equivalenciaJson.materiaOrigen.id);
		Materia materiaDestino = materiaServiceImp.get(equivalenciaJson.materiaDestino.id);
		if(!equivalencia.getMateriaOrigen().equals(materiaOrigen) || !equivalencia.getMateriaDestino().equals(materiaDestino) ) {
			this.validarSiExisteEquivalencia(materiaOrigen, materiaDestino);
		}
		equivalencia.setMateriaOrigen(materiaOrigen);
		equivalencia.setMateriaDestino(materiaDestino);
		Validacion.validarEquivalencia(materiaOrigen, materiaDestino);
		equivalenciaDaoImp.update(equivalencia);
	}
}
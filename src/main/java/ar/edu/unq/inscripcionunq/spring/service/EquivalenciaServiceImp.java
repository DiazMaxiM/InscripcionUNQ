package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EquivalenciaJson;
import ar.edu.unq.inscripcionunq.spring.dao.EquivalenciaDao;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.model.Equivalencia;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

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
	public void agregarNuevaCarrera(EquivalenciaJson equivalenciaJson) throws ObjectNotFoundinDBException {
		Materia materiaOrigen = materiaServiceImp.get(equivalenciaJson.materiaOrigen.id);
		Materia materiaDestino = materiaServiceImp.get(equivalenciaJson.materiaDestino.id);
		equivalenciaDaoImp.save(new Equivalencia(materiaOrigen, materiaDestino));
	}

	@Override
	public void actualizarEquivalencia(EquivalenciaJson equivalenciaJson) throws ObjectNotFoundinDBException {
		Equivalencia equivalencia = equivalenciaDaoImp.get(equivalenciaJson.id);
		equivalencia.setMateriaOrigen(materiaServiceImp.get(equivalenciaJson.materiaOrigen.id));
		equivalencia.setMateriaDestino(materiaServiceImp.get(equivalenciaJson.materiaDestino.id));
		equivalenciaDaoImp.update(equivalencia);
	}

}

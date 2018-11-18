package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.TipoIncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.dao.TipoIncidenciaDao;
import ar.edu.unq.inscripcionunq.spring.exception.YaExisteTipoDeIncidenciaException;
import ar.edu.unq.inscripcionunq.spring.model.TipoIncidencia;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;

@Service
@Transactional
public class TipoIncidenciaServiceImp extends GenericServiceImp<TipoIncidencia> implements TipoIncidenciaService {

	@Autowired
	TipoIncidenciaDao tipoIncidenciaDaoImp;

	@Override
	public List<TipoIncidencia> getTipoIncidencias() {
		return tipoIncidenciaDaoImp.getTipoIncidencias();
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void agregarNuevoTipoIncidencia(TipoIncidenciaJson tipoIncidenciaJson) throws YaExisteTipoDeIncidenciaException {
		try {
			tipoIncidenciaDaoImp.save(new TipoIncidencia(tipoIncidenciaJson.descripcion));
		} catch (ConstraintViolationException e) {
		    throw new YaExisteTipoDeIncidenciaException();
		}
	}

	@Override
	public void actualizarTipoIncidencia(TipoIncidenciaJson tipoIncidenciaJson) throws YaExisteTipoDeIncidenciaException {
		TipoIncidencia tipoIncidencia = tipoIncidenciaDaoImp.get(tipoIncidenciaJson.id);
		if(!tipoIncidencia.getDescripcion().equals(tipoIncidenciaJson.descripcion)) {
			validarSiExisteTipoDeIncidencia(tipoIncidenciaJson.descripcion);
		}
		tipoIncidencia.setDescripcion(tipoIncidenciaJson.descripcion);
		tipoIncidencia.setEstado(TipoEstado.valueOf(tipoIncidenciaJson.estado));
		tipoIncidenciaDaoImp.update(tipoIncidencia);
	}
	private void validarSiExisteTipoDeIncidencia(String descripcion) throws YaExisteTipoDeIncidenciaException {
		TipoIncidencia tipoIncidencia =  tipoIncidenciaDaoImp.getTipoIncidencia(descripcion);
		if(tipoIncidencia != null) {
			 throw new YaExisteTipoDeIncidenciaException();
		}
	}
	
	
}
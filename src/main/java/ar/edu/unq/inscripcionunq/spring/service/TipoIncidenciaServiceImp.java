package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.TipoIncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.dao.TipoIncidenciaDao;
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

	@Override
	public void agregarNuevoTipoIncidencia(TipoIncidenciaJson tipoIncidenciaJson) {
		tipoIncidenciaDaoImp.save(new TipoIncidencia(tipoIncidenciaJson.descripcion));
	}

	@Override
	public void actualizarTipoIncidencia(TipoIncidenciaJson tipoIncidenciaJson) {
		TipoIncidencia tipoIncidencia = tipoIncidenciaDaoImp.get(tipoIncidenciaJson.id);
		tipoIncidencia.setDescripcion(tipoIncidenciaJson.descripcion);
		tipoIncidencia.setEstado(TipoEstado.valueOf(tipoIncidenciaJson.estado));
		tipoIncidenciaDaoImp.update(tipoIncidencia);
	}
}
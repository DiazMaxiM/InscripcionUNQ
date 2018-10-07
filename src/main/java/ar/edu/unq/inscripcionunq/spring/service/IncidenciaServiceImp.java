package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.dao.IncidenciaDao;
import ar.edu.unq.inscripcionunq.spring.dao.TipoIncidenciaDao;
import ar.edu.unq.inscripcionunq.spring.model.Incidencia;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstadoIncidencia;
import ar.edu.unq.inscripcionunq.spring.model.TipoIncidencia;

@Service
@Transactional
public class IncidenciaServiceImp extends GenericServiceImp<Incidencia> implements IncidenciaService {

	@Autowired
	IncidenciaDao incidenciaDaoImp;

	@Autowired
	TipoIncidenciaDao tipoIncidenciaDaoImp;

	@Override
	public List<Incidencia> getIncidencias() {

		return incidenciaDaoImp.getIncidencias();
	}

	@Override
	public void agregarIncidencia(IncidenciaJson incidenciaJson) {
		Incidencia incidencia = new Incidencia(tipoIncidenciaDaoImp.get(incidenciaJson.tipoIncidenciaJson.id),
				incidenciaJson.descripcion);
		incidenciaDaoImp.save(incidencia);
	}

	@Override
	public void actualizarIncidencia(IncidenciaJson incidenciaJson) {
		Incidencia incidencia = incidenciaDaoImp.get(incidenciaJson.id);
		incidencia.setDescripcion(incidenciaJson.descripcion);
		TipoIncidencia tipoIncidencia = tipoIncidenciaDaoImp.get(incidenciaJson.tipoIncidenciaJson.id);
		incidencia.setTipoIncidencia(tipoIncidencia);
		incidencia.setTipoEstadoIncidencia(TipoEstadoIncidencia.valueOf(incidenciaJson.tipoEstadoIncidencia));
		incidenciaDaoImp.update(incidencia);
	}

	@Override
	public List<IncidenciaJson> getIncidenciasJson() {
		return this.getIncidencias().stream().map(incidencia -> new IncidenciaJson(incidencia))
				.collect(Collectors.toList());
	}

}
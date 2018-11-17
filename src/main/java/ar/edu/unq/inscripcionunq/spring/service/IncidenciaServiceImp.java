package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.dao.IncidenciaDao;
import ar.edu.unq.inscripcionunq.spring.dao.TipoIncidenciaDao;
import ar.edu.unq.inscripcionunq.spring.exception.EmailInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Incidencia;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstadoIncidencia;
import ar.edu.unq.inscripcionunq.spring.model.TipoIncidencia;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

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
	public void agregarIncidencia(IncidenciaJson incidenciaJson) throws EmailInvalidoException {
		TipoIncidencia tipo = tipoIncidenciaDaoImp.get(incidenciaJson.tipoIncidencia.id);
		Incidencia incidencia = new Incidencia(tipo, incidenciaJson.descripcion, incidenciaJson.emailDelReportante);
		Validacion.validarIncidencia(incidencia);
		incidenciaDaoImp.save(incidencia);
	}

	@Override
	public void actualizarIncidencia(IncidenciaJson incidenciaJson) {
		Incidencia incidencia = incidenciaDaoImp.get(incidenciaJson.id);
		incidencia.setDescripcion(incidenciaJson.descripcion);
		TipoIncidencia tipoIncidencia = tipoIncidenciaDaoImp.get(incidenciaJson.tipoIncidencia.id);
		incidencia.setTipoIncidencia(tipoIncidencia);
		TipoEstadoIncidencia tipoEstado;
		incidencia.setTipoEstadoIncidencia(TipoEstadoIncidencia.getEnum(incidenciaJson.tipoEstadoIncidencia));

		incidenciaDaoImp.save(incidencia);
	}

	@Override
	public List<IncidenciaJson> getIncidenciasJson() {
		return this.getIncidencias().stream().map(incidencia -> new IncidenciaJson(incidencia))
				.collect(Collectors.toList());
	}
}
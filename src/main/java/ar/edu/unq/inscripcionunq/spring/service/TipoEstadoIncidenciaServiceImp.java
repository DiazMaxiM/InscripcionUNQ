package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.TipoEstadoIncidencia;

@Service
@Transactional
public class TipoEstadoIncidenciaServiceImp implements TipoEstadoIncidenciaService {

	@Override
	public List<TipoEstadoIncidencia> getTipoEstadosIncidencias() {
		List<TipoEstadoIncidencia> tiposIncidencias = new ArrayList<TipoEstadoIncidencia>();
		for (TipoEstadoIncidencia tipoEstadoIncidencia : TipoEstadoIncidencia.values()) {
			tiposIncidencias.add(tipoEstadoIncidencia);
		}
		return tiposIncidencias;
	}
}
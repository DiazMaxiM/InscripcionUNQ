package ar.edu.unq.inscripcionunq.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.TipoReporte;

@Service
@Transactional
public class TipoReporteServiceImp implements TipoReporteService {

	@Override
	public List<TipoReporte> getTipoReportes() {
		 List<TipoReporte> tipoReportes = new ArrayList<TipoReporte>();
		 for (TipoReporte tipoReporte : TipoReporte.values()) {
			tipoReportes.add(tipoReporte);
		}
		return tipoReportes;
	}
}
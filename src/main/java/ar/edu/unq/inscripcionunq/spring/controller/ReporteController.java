package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.TipoReporteJson;
import ar.edu.unq.inscripcionunq.spring.service.TipoReporteService;

@RestController
public class ReporteController {

	@Autowired
	private TipoReporteService tipoReporteServiceImp;

	@GetMapping("/tiposReporte")
	public ResponseEntity<List> getTiposReporte() {
		return ResponseEntity.ok().body(tipoReporteServiceImp.getTipoReportes().stream()
				.map(tipoReporte -> new TipoReporteJson(tipoReporte)).collect(Collectors.toList()));
	}

}
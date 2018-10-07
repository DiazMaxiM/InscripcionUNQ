package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.TipoIncidenciaJson;
import ar.edu.unq.inscripcionunq.spring.service.IncidenciaService;
import ar.edu.unq.inscripcionunq.spring.service.TipoIncidenciaService;

@RestController
public class IncidenciaController {

	@Autowired
	private TipoIncidenciaService tipoIncidenciaServiceImp;

	@Autowired
	private IncidenciaService incidenciaServiceImp;

	@GetMapping("/tipoIncidencias")
	public ResponseEntity<List> getTipoIncidencias() {
		return ResponseEntity.ok().body(tipoIncidenciaServiceImp.getTipoIncidencias().stream()
				.map(tipoIncidencia -> new TipoIncidenciaJson(tipoIncidencia)).collect(Collectors.toList()));
	}

	@PutMapping("/tipoIncidencia")
	public ResponseEntity agregarNuevoTipoIncidencia(@RequestBody TipoIncidenciaJson tipoIncidenciaJson) {
		tipoIncidenciaServiceImp.agregarNuevoTipoIncidencia(tipoIncidenciaJson);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/tipoIncidencia")
	public ResponseEntity actualizarTipoIncidencia(@RequestBody TipoIncidenciaJson tipoIncidenciaJson) {

		tipoIncidenciaServiceImp.actualizarTipoIncidencia(tipoIncidenciaJson);
		return ResponseEntity.ok().build();

	}

	@GetMapping("/incidencias")
	public ResponseEntity<List> getIncidencias() {
		return ResponseEntity.ok().body(incidenciaServiceImp.getIncidenciasJson());
	}

	@PutMapping("/incidencia")
	public ResponseEntity agregarIncidencia(@RequestBody IncidenciaJson incidenciaJson) {
		incidenciaServiceImp.agregarIncidencia(incidenciaJson);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/incidencia")
	public ResponseEntity actualizarIncidencia(@RequestBody IncidenciaJson incidenciaJson) {
		incidenciaServiceImp.actualizarIncidencia(incidenciaJson);
		return ResponseEntity.ok().build();

	}

}
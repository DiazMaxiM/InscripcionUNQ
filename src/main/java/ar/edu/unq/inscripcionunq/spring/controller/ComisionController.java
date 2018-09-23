package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionJson;
import ar.edu.unq.inscripcionunq.spring.service.ComisionService;

@RestController
public class ComisionController {

	@Autowired
	private ComisionService comisionServiceImp;

	@GetMapping("/commission/subject/poll/{idMateria}/{idEncuesta}")
	public ResponseEntity<List<ComisionJson>> comisionesParaMateriasEnEncuesta(@PathVariable String idMateria,
			@PathVariable String idEncuesta) {
		List<ComisionJson> comisiones = comisionServiceImp.getComisionParaMateriaEnEncuesta(idMateria, idEncuesta);

		return ResponseEntity.ok().body(comisiones);
	}
}
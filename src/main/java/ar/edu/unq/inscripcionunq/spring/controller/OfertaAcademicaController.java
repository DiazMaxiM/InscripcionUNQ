package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.OfertaAcademicaJson;
import ar.edu.unq.inscripcionunq.spring.exception.CodigoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.DescripcionInvalidaException;
import ar.edu.unq.inscripcionunq.spring.exception.EstadoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.service.OfertaAcademicaService;

@RestController
public class OfertaAcademicaController {

	@Autowired 
	OfertaAcademicaService ofertaAcademicaServiceImpl;
	
	@GetMapping("/ofertas-academicas")
	public ResponseEntity<List> getOfertas() {
		return ResponseEntity.ok().body(ofertaAcademicaServiceImpl.getOfertasAcademicasJson());
		
	}
	
	@PutMapping("/ofertas-academicas/clonarOferta/")
	public ResponseEntity clonarCarrera(@RequestBody OfertaAcademicaJson ofertaJson) {
		try {
			ofertaAcademicaServiceImpl.clonarOferta(ofertaJson.id);
		} catch (OfertaNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		
		return ResponseEntity.ok().build();

	}
	
	@PutMapping("/ofertas-academicas/crearOferta/")
	public ResponseEntity crearNuevaOferta(@RequestBody OfertaAcademicaJson ofertaJson) {
		try {
			ofertaAcademicaServiceImpl.crearOferta(ofertaJson);
		} catch (DescripcionInvalidaException | CodigoInvalidoException | EstadoInvalidoException
				| NombreInvalidoException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/ofertas-academicas/actualizarOferta/")
	public ResponseEntity actualizarOferta(@RequestBody OfertaAcademicaJson ofertaJson) {
		try {
			ofertaAcademicaServiceImpl.actualizarOferta(ofertaJson);
		} catch (DescripcionInvalidaException | CodigoInvalidoException | EstadoInvalidoException
				| NombreInvalidoException |OfertaNoExisteException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();

	}
}

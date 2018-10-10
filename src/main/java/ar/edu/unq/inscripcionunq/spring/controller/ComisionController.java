package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionCompletaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.exception.ComisionSinHorariosException;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.CupoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.TypeDay;
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
	
	@GetMapping("/comisiones/comisionesEnPeriodo/{idPeriodo}")
	public ResponseEntity<List<ComisionCompletaJson>> getComisiones(@PathVariable String idPeriodo) {
		List<ComisionCompletaJson> comisiones = comisionServiceImp.getComisionesEnPeriodo(idPeriodo);

		return ResponseEntity.ok().body(comisiones);
	}
	
	@GetMapping("/dias")
	public ResponseEntity getDias() {
		return ResponseEntity.ok().body(TypeDay.values());
	}
	
	@PutMapping("/comision/nuevaComision")
	public ResponseEntity agregarNuevaComision(@RequestBody ComisionCompletaJson comisionJson) {
		try {
			comisionServiceImp.crearNuevaComision(comisionJson);
		} catch (PeriodoInvalidoException | MateriaNoExisteException | NombreInvalidoException | CupoInvalidoException
				| ComisionSinHorariosException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/comision/editarComision")
	public ResponseEntity editarComision(@RequestBody ComisionCompletaJson comisionJson) {
		try {
			comisionServiceImp.editarComision(comisionJson);
		} catch (PeriodoInvalidoException | MateriaNoExisteException | NombreInvalidoException | CupoInvalidoException
				| ComisionSinHorariosException | CommissionNotExistenException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().build();

	}
}
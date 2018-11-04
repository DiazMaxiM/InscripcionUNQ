package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EncuestaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EncuestaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.EstudianteJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.IdJson;
import ar.edu.unq.inscripcionunq.spring.exception.CertificadoException;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.ConexionWebServiceException;
import ar.edu.unq.inscripcionunq.spring.exception.EncuestaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.StudentNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.VariasComisionesDeUnaMateriaException;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;
import ar.edu.unq.inscripcionunq.spring.service.EncuestaService;
import ar.edu.unq.inscripcionunq.spring.service.EstudianteService;

@RestController
public class EncuestaController {
	@Autowired
	private EncuestaService encuestaServiceImp;
	@Autowired
	private EstudianteService estudianteServiceImp;

	@GetMapping("/poll/user/{dni}")
	public ResponseEntity<List<EncuestaJson>> habilitarEncuestasParaUnUsuario(@PathVariable String dni) {
		List<Encuesta> encuestas = encuestaServiceImp.getTodasLasEncuestasActivasParaDni(dni);

		List<EncuestaJson> minipolls = encuestas.stream().map(m -> new EncuestaJson(m.getId(), m.getNombre(),
				m.getHoraComienzo(), m.getHoraFin(), encuestaServiceImp.puedeGenerarPDF(dni, m.getId()))

		).collect(Collectors.toList());
		return ResponseEntity.ok().body(minipolls);

	}

	@GetMapping("/poll/userData/{dni}/{idEncuesta}")
	public ResponseEntity datosDeUsuarioParaEncuesta(@PathVariable String dni, @PathVariable Long idEncuesta) {
		Estudiante estudiante;
		try {
			estudiante = encuestaServiceImp.getDatosDeUsuarioParaEncuesta(dni, idEncuesta);
		} catch (UserInPollNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(exception));
		}
		EstudianteJson estudianteJson = new EstudianteJson(estudiante);
		return ResponseEntity.ok().body(estudianteJson);
	}

	@PostMapping("/poll/comisionesSeleccionada/{id}")
	public ResponseEntity actualizarDatosDeUsuario(@PathVariable String id, @RequestBody List<IdJson> idsJson) {
		try {
			encuestaServiceImp.setComisionesSeleccionadas(id, idsJson);
		} catch (IdNumberFormatException | StudentNotExistenException | CommissionNotExistenException
				| VariasComisionesDeUnaMateriaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		try {
			try {
				estudianteServiceImp.enviarCertificado(id);
			} catch (DocumentException | EmailException | CertificadoException e) {
			}
		} catch (StudentNotExistenException | IdNumberFormatException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().body(null);
	}
	
	@GetMapping("/encuestas")
	public ResponseEntity<List<EncuestaSistemaJson>> encuestas(){
		return ResponseEntity.ok().body(encuestaServiceImp.getEncuestaJson());

	}
	
	@PutMapping("/encuestas/nuevaEncuesta")
	public ResponseEntity agregarNuevaEncuesta(@RequestBody EncuestaSistemaJson encuestaJson) {
		try {
			encuestaServiceImp.crearNuevaEncuesta(encuestaJson);
		} catch (IdNumberFormatException | PeriodoInvalidoException | ConexionWebServiceException | EncuestaNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/encuestas/actualizarEncuesta")
	public ResponseEntity actualizarEncuesta(@RequestBody EncuestaSistemaJson encuestaJson) {
		try {
			encuestaServiceImp.actualizarEncuesta(encuestaJson);
		} catch (IdNumberFormatException | PeriodoInvalidoException | EncuestaNoExisteException e) {
	    	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		
		return ResponseEntity.ok().build();

	}
	
	@PostMapping("/encuestas/asociarOfertasParaEncuesta/{idEncuesta}")
	public ResponseEntity asociarOfertasParaEncuesta(@PathVariable String idEncuesta, @RequestBody List<IdJson> idsJson) {
		try {
			encuestaServiceImp.asociarOfertasParaEncuesta(idEncuesta,idsJson);
		} catch (IdNumberFormatException | EncuestaNoExisteException | OfertaNoExisteException e) {
	    	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		
		return ResponseEntity.ok().build();

	}
	

}
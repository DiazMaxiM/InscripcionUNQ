package ar.edu.unq.inscripcionunq.spring.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import ar.edu.unq.inscripcionunq.spring.exception.CantidadMateriasInscripcionSuperadaException;
import ar.edu.unq.inscripcionunq.spring.exception.CertificadoException;
import ar.edu.unq.inscripcionunq.spring.exception.ComisionNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ConexionWebServiceException;
import ar.edu.unq.inscripcionunq.spring.exception.EncuestaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.EstudianteNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ExisteEncuestaConMismoNombreException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoCumplePrerrequisitoException;
import ar.edu.unq.inscripcionunq.spring.exception.NoExistenUsuariosEnEncuestaException;
import ar.edu.unq.inscripcionunq.spring.exception.OfertaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
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

		List<EncuestaJson> miniEncuestas = encuestas.stream()
				.map(m -> new EncuestaJson(m.getId(), m.getNombre(), m.getHoraComienzo(), m.getHoraFin(),
						encuestaServiceImp.puedeGenerarPDF(dni, m.getId()), m.getLimilteMaxMaterias())

				).collect(Collectors.toList());
		return ResponseEntity.ok().body(miniEncuestas);
	}

	@GetMapping("/poll/userData/{dni}/{idEncuesta}")
	public ResponseEntity datosDeUsuarioParaEncuesta(@PathVariable String dni, @PathVariable Long idEncuesta) {
		Estudiante estudiante;
		try {
			estudiante = encuestaServiceImp.getDatosDeUsuarioParaEncuesta(dni, idEncuesta);
		} catch (NoExistenUsuariosEnEncuestaException exception) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(exception));
		}
		EstudianteJson estudianteJson = new EstudianteJson(estudiante);
		return ResponseEntity.ok().body(estudianteJson);
	}

	@PostMapping("/poll/comisionesSeleccionada/{id}")
	public ResponseEntity actualizarDatosDeUsuario(@PathVariable String id, @RequestBody List<IdJson> idsJson) {
		try {
			encuestaServiceImp.setComisionesSeleccionadas(id, idsJson);
		} catch (FormatoNumeroIdException | EstudianteNoExisteException | ComisionNoExisteException
				| VariasComisionesDeUnaMateriaException | MateriaNoCumplePrerrequisitoException
				| CantidadMateriasInscripcionSuperadaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		try {
			try {
				estudianteServiceImp.enviarCertificado(id);
			} catch (DocumentException | EmailException | CertificadoException e) {
			}
		} catch (EstudianteNoExisteException | FormatoNumeroIdException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		return ResponseEntity.ok().body(null);
	}

	@GetMapping("/encuestas")
	public ResponseEntity<List<EncuestaSistemaJson>> encuestas() {
		return ResponseEntity.ok().body(encuestaServiceImp.getEncuestaJson());
	}

	@PutMapping("/encuestas/nuevaEncuesta")
	public ResponseEntity agregarNuevaEncuesta(@RequestBody EncuestaSistemaJson encuestaJson) {
		try {
			encuestaServiceImp.crearNuevaEncuesta(encuestaJson);
		} catch (FormatoNumeroIdException | PeriodoInvalidoException | ConexionWebServiceException
				| EncuestaNoExisteException | OfertaNoExisteException | ExisteEncuestaConMismoNombreException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/encuestas/actualizarEncuesta")
	public ResponseEntity actualizarEncuesta(@RequestBody EncuestaSistemaJson encuestaJson) {
		try {
			encuestaServiceImp.actualizarEncuesta(encuestaJson);
		} catch (FormatoNumeroIdException | PeriodoInvalidoException | EncuestaNoExisteException
				| ExisteEncuestaConMismoNombreException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/encuestas/asociarOfertasParaEncuesta/{idEncuesta}")
	public ResponseEntity asociarOfertasParaEncuesta(@PathVariable String idEncuesta,
			@RequestBody List<IdJson> idsJson) {
		try {
			encuestaServiceImp.asociarOfertasParaEncuesta(idEncuesta, idsJson);
		} catch (FormatoNumeroIdException | EncuestaNoExisteException | OfertaNoExisteException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/generarReporte/{idEncuesta}/{tipoEncuesta}")
	public ResponseEntity generarReporte(@PathVariable String idEncuesta, @PathVariable String tipoEncuesta)
			throws IOException {
		byte[] xlsBytes;
		try {
			xlsBytes = encuestaServiceImp.getReporte(idEncuesta, tipoEncuesta).getBinaryPDF();
		} catch (FormatoNumeroIdException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}

		HttpHeaders encabezados = new HttpHeaders();
		encabezados.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));

		String nombreArchivo = "output.xls";

		encabezados.setContentDispositionFormData(nombreArchivo, nombreArchivo);
		encabezados.setCacheControl("must-revalidate, post-check=0,pre-check=0");
		return new ResponseEntity<>(xlsBytes, encabezados, HttpStatus.OK);
	}

	@PostMapping("/guardarJson")
	public ResponseEntity guardarArchivo(@RequestBody String archivo) {
		try {
			encuestaServiceImp.guardarArchivo(archivo);
			return ResponseEntity.ok().build();
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e);
		}
	}
	
	@GetMapping("/encuesta/estudiantes/{idEncuesta}")
	public ResponseEntity getEstudiantesDeEncuesta(@PathVariable String idEncuesta) {
		List<EstudianteJson> estudiantesJson;
		try {
			estudiantesJson = encuestaServiceImp.getEstudiantesDeEncuesta(idEncuesta);
			return ResponseEntity.ok().body(estudiantesJson);
		} catch (FormatoNumeroIdException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
	}
	
	

}
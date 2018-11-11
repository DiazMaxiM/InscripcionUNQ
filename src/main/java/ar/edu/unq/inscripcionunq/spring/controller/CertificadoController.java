package ar.edu.unq.inscripcionunq.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ExceptionJson;
import ar.edu.unq.inscripcionunq.spring.exception.CertificadoException;
import ar.edu.unq.inscripcionunq.spring.exception.FormatoNumeroIdException;
import ar.edu.unq.inscripcionunq.spring.exception.EstudianteNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Certificado;
import ar.edu.unq.inscripcionunq.spring.service.EstudianteService;

@RestController
public class CertificadoController {
	@Autowired
	private EstudianteService estudianteServiceImp;

	@GetMapping("/pdf/{idEstudiante}")
	public ResponseEntity prueba(@PathVariable String idEstudiante) throws DocumentException {

		Certificado certificado = new Certificado();

		byte[] pdfBytes;
		try {
			pdfBytes = estudianteServiceImp.getCertificado(idEstudiante).getBinaryPDF();
		} catch (EstudianteNoExisteException | FormatoNumeroIdException | CertificadoException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionJson(e));
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "output.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0,pre-check=0");
		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}
}
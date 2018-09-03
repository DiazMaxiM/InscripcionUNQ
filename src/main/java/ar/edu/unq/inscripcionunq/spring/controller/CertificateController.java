package ar.edu.unq.inscripcionunq.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import ar.edu.unq.inscripcionunq.spring.model.Certificate;
import ar.edu.unq.inscripcionunq.spring.service.StudentService;

@RestController
public class CertificateController {
	@Autowired
	private StudentService StudentServiceImp;

	@GetMapping("/pdf")
	public ResponseEntity<byte[]> prueba() {
		Certificate certificate = new Certificate();
		byte[] pdfBytes;

		try {
			pdfBytes = certificate.getBinaryPDF();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "output.pdf";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0,pre-check=0");
			return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
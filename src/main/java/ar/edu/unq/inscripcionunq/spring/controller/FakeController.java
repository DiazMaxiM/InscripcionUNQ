package ar.edu.unq.inscripcionunq.spring.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeController {

	@RequestMapping(value = "user/{dni}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> user(@PathVariable long dni) {
		if (new Long(33810763).equals(dni)) {
			return ResponseEntity.ok()
					.body("{\"id\":1,\"name\":\"Diaz Maximiliano Martin\",\"email\":\"diazmaxi@gmail.com\"}");
		}
		return ResponseEntity.badRequest().body("{\"errorId\":1,\"msg\":\"Alumno Inexistente o Irregular\"}");
	}

}
package ar.edu.unq.inscripcionunq.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.inscripcionunq.spring.service.MateriaService;

@RestController
public class MateriaController {
	@Autowired
	private MateriaService materiaServiceImp;


	@GetMapping("/materias")
	public ResponseEntity<List> getMaterias() {
		return ResponseEntity.ok().body(materiaServiceImp.list());
	}
}

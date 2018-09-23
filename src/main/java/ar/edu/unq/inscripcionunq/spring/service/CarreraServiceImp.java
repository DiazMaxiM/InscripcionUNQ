package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;

@Service
@Transactional

public class CarreraServiceImp extends GenericServiceImp<Carrera> implements GenericService<Carrera> {

}

package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Career;

@Service
@Transactional

public class CareerServiceImp extends GenericServiceImp<Career> implements GenericService<Career> {

}

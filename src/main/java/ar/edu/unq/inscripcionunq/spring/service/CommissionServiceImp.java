package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Commission;

@Service
@Transactional

public class CommissionServiceImp extends GenericServiceImp<Commission> implements GenericService<Commission> {

}

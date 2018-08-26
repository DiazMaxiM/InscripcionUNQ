package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Matter;

@Service
@Transactional
public class MatterServiceImp extends GenericServiceImp<Matter> implements GenericService<Matter> {

}

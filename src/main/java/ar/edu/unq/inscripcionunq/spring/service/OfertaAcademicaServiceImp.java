package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;

@Service
@Transactional
public class OfertaAcademicaServiceImp extends GenericServiceImp<OfertaAcademica> implements GenericService<OfertaAcademica> {

}

package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.AcademicOffer;

@Service
@Transactional
public class AcademicOfferServiceImp extends GenericServiceImp<AcademicOffer> implements GenericService<AcademicOffer> {

}

package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.model.Poll;

@Service
@Transactional
public class PollServiceImp extends GenericServiceImp<Poll> implements GenericService<Poll> {

}

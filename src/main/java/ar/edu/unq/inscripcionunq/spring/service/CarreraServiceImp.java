package ar.edu.unq.inscripcionunq.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;

@Service
@Transactional
public class CarreraServiceImp extends GenericServiceImp<Carrera> implements CarreraService {
    

	@Override
	public void eliminarCarrera(String idCarrera) throws IdNumberFormatException, CarreraNoExisteException {
		Carrera carrera;
		try {
			carrera = this.get(new Long(idCarrera));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new CarreraNoExisteException();
		}
		
		this.delete(carrera);
	}
      
}

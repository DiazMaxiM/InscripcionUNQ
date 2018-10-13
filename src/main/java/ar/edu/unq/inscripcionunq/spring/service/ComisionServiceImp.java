package ar.edu.unq.inscripcionunq.spring.service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionCompletaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.ComisionJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.HorarioJson;
import ar.edu.unq.inscripcionunq.spring.dao.ComisionDao;
import ar.edu.unq.inscripcionunq.spring.dao.MateriaDao;
import ar.edu.unq.inscripcionunq.spring.dao.PeriodoDao;
import ar.edu.unq.inscripcionunq.spring.exception.ComisionSinHorariosException;
import ar.edu.unq.inscripcionunq.spring.exception.CommissionNotExistenException;
import ar.edu.unq.inscripcionunq.spring.exception.CupoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.NombreInvalidoException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.exception.PeriodoInvalidoException;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;
import ar.edu.unq.inscripcionunq.spring.validacion.Validacion;

@Service
@Transactional

public class ComisionServiceImp extends GenericServiceImp<Comision> implements ComisionService {
	@Autowired
	private ComisionDao comisionDaoImp;
	
	@Autowired
	private MateriaDao materiaDaoImp;
	
	@Autowired
	private PeriodoDao periodoDaoImp;

	@Override
	public List<ComisionJson> getComisionParaMateriaEnEncuesta(String idMateria, String idEncuesta) {

		List<Comision> comisiones = comisionDaoImp.getComisionParaMateriaEnEncuesta(new Long(idMateria),
				new Long(idEncuesta));
		
		return comisiones.stream().map(commission -> new ComisionJson(commission)).collect(Collectors.toList());
	}

	@Override
	public List<Comision> getTodasLasComisionesDeMateriaEnEncuesta(String idEncuesta) {

		return comisionDaoImp.getTodasLasComisionesDeMateriaEnEncuesta(new Long(idEncuesta));
	}

	@Override
	public List<ComisionCompletaJson> getComisionesEnPeriodo(String idPeriodo) {
		List<Comision> comisiones = comisionDaoImp.getComisionParaPeriodo(new Long(idPeriodo));
		return crearComisionesJson(comisiones);
	}

	private List<ComisionCompletaJson> crearComisionesJson(List<Comision> comisiones){
		return comisiones.stream().map(commission -> new ComisionCompletaJson(commission)).collect(Collectors.toList());
	}

	@Override
	public void crearNuevaComision(ComisionCompletaJson comisionJson) throws PeriodoInvalidoException, MateriaNoExisteException, NombreInvalidoException, CupoInvalidoException, ComisionSinHorariosException {
		Comision comision = armarComisionDesdeJson(comisionJson);
		this.save(comision);
		
	}

	private Comision armarComisionDesdeJson(ComisionCompletaJson comisionJson) throws PeriodoInvalidoException, MateriaNoExisteException, NombreInvalidoException, CupoInvalidoException, ComisionSinHorariosException {
		Materia materia = materiaDaoImp.get(comisionJson.materia.id);
		Periodo periodo = periodoDaoImp.get(comisionJson.periodo.id);
		Comision comision = new Comision(comisionJson.nombre, materia, comisionJson.cupo, periodo);
		for (HorarioJson horario : comisionJson.horarioJson) {
			LocalTime horaInicio = LocalTime.of(horario.horaComienzo.hour, horario.horaComienzo.minute);
			comision.agregarHorarios(horario.dia, horaInicio ,horario.duracion);
		}
		Validacion.validarComision(comision);
		return comision;
	}

	@Override
	public void editarComision(ComisionCompletaJson comisionJson) throws PeriodoInvalidoException, MateriaNoExisteException, NombreInvalidoException, CupoInvalidoException, ComisionSinHorariosException, CommissionNotExistenException {
		try {
			Comision comision = this.get(comisionJson.id);
			Comision comisionEditada = armarComisionDesdeJson(comisionJson);
			comision.modificarDatos(comisionEditada);
			this.save(comision);
		} catch (ObjectNotFoundinDBException e) {
			throw new CommissionNotExistenException();
			
		}
		
	}

	@Override
	public void eliminarComision(String idComision) throws IdNumberFormatException, CommissionNotExistenException {
		try {
		   Comision comision = this.get(new Long(idComision));
		   this.delete(comision);
		} catch (NumberFormatException e) { 
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new CommissionNotExistenException();
		}
			
	}

}

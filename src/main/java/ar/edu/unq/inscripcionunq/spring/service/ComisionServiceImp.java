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
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.Periodo;

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
	public void crearNuevaComision(ComisionCompletaJson comisionJson) {
		Comision comision = armarComisionDesdeJson(comisionJson);
		this.save(comision);
		
	}

	private Comision armarComisionDesdeJson(ComisionCompletaJson comisionJson) {
		Materia materia = materiaDaoImp.get(comisionJson.materia.id);
		Periodo periodo = periodoDaoImp.get(comisionJson.periodo.id);
		Comision comision = new Comision(comisionJson.nombre, materia, comisionJson.cupo, periodo);
		for (HorarioJson horario : comisionJson.horarioJson) {
			LocalTime horaInicio = LocalTime.of(horario.horaComienzo.hour, horario.horaComienzo.minute);
			comision.agregarHorarios(horario.dia, horaInicio ,horario.duracion);
		}
		return comision;
	}

}

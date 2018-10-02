package ar.edu.unq.inscripcionunq.spring.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ar.edu.unq.inscripcionunq.spring.dao.MateriaDao;
import ar.edu.unq.inscripcionunq.spring.exception.CarreraNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.IdNumberFormatException;
import ar.edu.unq.inscripcionunq.spring.exception.MateriaNoExisteException;
import ar.edu.unq.inscripcionunq.spring.exception.ObjectNotFoundinDBException;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaSistemaJson;
import ar.edu.unq.inscripcionunq.spring.controller.miniobject.CarreraJson;
import ar.edu.unq.inscripcionunq.spring.model.TypeStatus;
import ar.edu.unq.inscripcionunq.spring.dao.CarreraDao;
import java.util.stream.Collectors;

@Service
@Transactional

public class MateriaServiceImp extends GenericServiceImp<Materia> implements MateriaService {

    @Autowired 
	CarreraDao carreraDaoImp;

	@Override
	@Transactional
	public List<Materia> getMateriasParaCarreras(List<Carrera> carreras) {
		return ((MateriaDao) genericDao).getMateriasParaCarreras(carreras);
	}
	
    @Override
	public List<MateriaSistemaJson> getMateriasJson() {
		List<Materia> materias = this.list();
		return materias.stream().map(o -> this.crearMateriaJson(o)).collect(Collectors.toList());
	}

    private MateriaSistemaJson crearMateriaJson(Materia materia) {
        List<CarreraJson> carrerasJson = new ArrayList<CarreraJson>();
        materia.getCarreras().forEach((carrera) -> {
            
			CarreraJson carreraJson = new CarreraJson(carrera.getId(),carrera.getCodigo(),
				carrera.getDescripcion(),TypeStatus.esEstadoHabiltado(carrera.getEstado()));
            carrerasJson.add(carreraJson);
		});
		
		MateriaSistemaJson materiaJson = new MateriaSistemaJson(materia.getId(), materia.getCodigo(), materia.getNombre(), materia.getHoras(), 
carrerasJson, TypeStatus.esEstadoHabiltado(materia.getEstado()));
		return materiaJson;
		
	}


	@Override
	public void eliminarMateria(String idMateria) throws IdNumberFormatException, MateriaNoExisteException {
		Materia materia;
		try {
			materia = this.get(new Long(idMateria));
		} catch (NumberFormatException e) {
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new MateriaNoExisteException();
		}
		
		this.delete(materia);
	}

    @Override
	public void actualizarMateria(MateriaSistemaJson materiaJson) throws IdNumberFormatException, MateriaNoExisteException {
		Materia materiaNueva = this.mapearMateriaDesdeJson(materiaJson);
		Materia materiaOriginal = new Materia();
		try {
			materiaOriginal = this.get(materiaJson.id);
        } catch (NumberFormatException e) { 
			throw new IdNumberFormatException();
		} catch (ObjectNotFoundinDBException e) {
			throw new MateriaNoExisteException();
		}
		if (materiaNueva != null) {
            materiaOriginal.update(materiaNueva);
            this.save(materiaOriginal);
		}
		
    }

    private Materia mapearMateriaDesdeJson(MateriaSistemaJson materiaJson) throws IdNumberFormatException, MateriaNoExisteException  {
		
		TypeStatus estado = materiaJson.estado ? TypeStatus.ENABLED : TypeStatus.DISABLED;
        List<Carrera> carreras = new ArrayList<Carrera>();
        materiaJson.carreras.forEach((carreraJson) -> {
            TypeStatus estadoCarrera = carreraJson.habilitada ? TypeStatus.ENABLED : TypeStatus.DISABLED;
			Carrera carrera = new Carrera(carreraJson.codigo,
				carreraJson.descripcion);
            carrera.setEstado(estadoCarrera);
            Carrera carreraOriginal = carreraDaoImp.get(carreraJson.id);
            carreraOriginal.actualizarInformacion(carrera);
            carreras.add(carreraOriginal);
		});
		Materia materia = new Materia(materiaJson.codigo,materiaJson.nombre,materiaJson.horas,carreras, estado);
//		Validacion.validarMateria(materia);
		return materia;
	}

}

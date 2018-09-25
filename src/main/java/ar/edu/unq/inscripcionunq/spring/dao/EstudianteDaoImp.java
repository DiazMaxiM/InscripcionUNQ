package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.controller.miniobject.MateriaJson;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

@Repository

public class EstudianteDaoImp extends GenericDaoImp<Estudiante> implements EstudianteDao {

	@Override
	protected Class<Estudiante> getDomainClass() {
		return Estudiante.class;
	}

	@Override
	public List<MateriaJson> materiasDesaprobadasConComisionesDisponiblesDeUsuario(Long idUser) {
		return null;
	}

}

package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

@Repository
public class MateriaDaoImp extends GenericDaoImp<Materia> implements MateriaDao {

	@Override
	protected Class<Materia> getDomainClass() {
		return Materia.class;
	}

	@Override
	public List<Materia> getMateriasParaCarreras(List<Carrera> carreras) {
		List<Long> idCarreras = new ArrayList<Long>();
		for (Carrera carrera : carreras) {
			idCarreras.add(carrera.getId());
		}
		Session sesion = this.sessionFactory.getCurrentSession();
		Query<Materia> query = sesion.createQuery(
				"select distinct materia from Materia as materia join materia.carreras c where c.id in (:carrerasId)");
		query.setParameterList("carrerasId", idCarreras);
		return query.getResultList();
	}

}

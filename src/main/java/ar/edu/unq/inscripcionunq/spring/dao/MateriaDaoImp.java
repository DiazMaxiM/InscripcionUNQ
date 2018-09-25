package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.ArrayList;
import java.util.List;

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
	public List<Materia> getMateriasParaCarreras(List<Carrera> careers) {
		List<Long> careersId = new ArrayList<Long>();
		for (Carrera career : careers) {
			careersId.add(career.getId());
		}
		Session session = this.sessionFactory.getCurrentSession();
		Query<Materia> query = session.createQuery(
				"select distinct materia from Materia as materia join materia.carreras c where c.id in (:carrerasId)");
		query.setParameterList("carrerasId", careersId);
		return query.getResultList();
	}

}

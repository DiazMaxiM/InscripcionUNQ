package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Incidencia;

@Repository
public class IncidenciaDaoImp extends GenericDaoImp<Incidencia> implements IncidenciaDao {

	@Override
	protected Class<Incidencia> getDomainClass() {
		return Incidencia.class;
	}

	@Override
	public List<Incidencia> getIncidencias() {
		Session session = this.sessionFactory.getCurrentSession();
		return (List<Incidencia>) session.createQuery("Select i from Incidencia i").getResultList();
	}
}
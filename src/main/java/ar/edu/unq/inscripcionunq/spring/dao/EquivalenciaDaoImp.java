package ar.edu.unq.inscripcionunq.spring.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Equivalencia;
import ar.edu.unq.inscripcionunq.spring.model.Materia;

@Repository
public class EquivalenciaDaoImp extends GenericDaoImp<Equivalencia> implements EquivalenciaDao {

	@Override
	protected Class<Equivalencia> getDomainClass() {
		return Equivalencia.class;
	}

	@Override
	public Equivalencia obtenerEquivalencia(Materia materiaOrigen, Materia materiaDestino) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Equivalencia) session.createQuery("from Equivalencia e where (e.materiaOrigen = :materiaOrigen and "
				+ "e.materiaDestino = :materiaDestino) or (e.materiaOrigen = :materiaDestino and e.materiaDestino = :materiaOrigen)")
				.setParameter("materiaOrigen", materiaOrigen)
				.setParameter("materiaDestino", materiaDestino)
				.uniqueResult();
	}

}
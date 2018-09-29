package ar.edu.unq.inscripcionunq.spring.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;

@Repository
public class CarreraDaoImp extends GenericDaoImp<Carrera> implements CarreraDao {

	@Override
	protected Class<Carrera> getDomainClass() {
		return Carrera.class;
	}

	@Override
	public Carrera encontrarCarreraConElMismoCodigo(String codigo) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Carrera) session.createQuery("from Carrera where codigo = :codigo")
				.setParameter("codigo", codigo)
			    .uniqueResult();
	}

}

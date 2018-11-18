package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.TipoIncidencia;

@Repository
public class TipoIncidenciaDaoImp extends GenericDaoImp<TipoIncidencia> implements TipoIncidenciaDao {

	@Override
	protected Class<TipoIncidencia> getDomainClass() {
		return TipoIncidencia.class;
	}

	@Override
	public List<TipoIncidencia> getTipoIncidencias() {
		Session session = this.sessionFactory.getCurrentSession();
		return (List<TipoIncidencia>) session.createQuery("Select ti from TipoIncidencia ti").getResultList();
	}

	@Override
	public TipoIncidencia getTipoIncidencia(String descripcion) {
		Session session = this.sessionFactory.getCurrentSession();
		return (TipoIncidencia) session.createQuery("from TipoIncidencia where descripcion = :descripcion")
				.setParameter("descripcion", descripcion)
				.uniqueResult();
	}
}
package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Comision;

@Repository
public class ComisionDaoImp extends GenericDaoImp<Comision> implements ComisionDao {

	@Override
	protected Class<Comision> getDomainClass() {
		return Comision.class;
	}

	@Override
	public List<Comision> getComisionParaMateriaEnEncuesta(Long idMateria, Long idEncuesta) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Comision> query = session.createQuery("select distinct c from Encuesta p join p.ofertasAcademicas o "
				+ "join o.comisiones c join c.materia s where p.id=:idEncuesta and s.id= :idMateria");
		query.setParameter("idEncuesta", idEncuesta);
		query.setParameter("idMateria", idMateria);
		return query.getResultList();
	}

	@Override
	public List<Comision> getTodasLasComisionesDeMateriaEnEncuesta(Long idEncuesta) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Comision> query = session.createQuery("select distinct c from Encuesta p join p.ofertasAcademicas o "
				+ "join o.comisiones c join c.materia s where p.id=:idEncuesta");
		query.setParameter("idEncuesta", idEncuesta);
		return query.getResultList();
	}

	@Override
	public List<Comision> getComisionParaPeriodo(Long idPeriodo) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Comision> query = session
				.createQuery("from Comision c where c.periodo.id = :idPeriodo order by c.materia.nombre asc");
		query.setParameter("idPeriodo", idPeriodo);
		return query.getResultList();
	}

	@Override
	public Comision obtenerComisionConNombreEnPeriodo(String nombre, Long idPeriodo) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Comision) session.createQuery("from Comision c where c.nombre = :nombre and c.periodo.id = :idPeriodo")
				.setParameter("idPeriodo", idPeriodo).setParameter("nombre", nombre).uniqueResult();
	}
}
package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Comision;
import ar.edu.unq.inscripcionunq.spring.model.OfertaAcademica;


@Repository
public class OfertaAcademicaDaoImp extends GenericDaoImp<OfertaAcademica> implements OfertaAcademicaDao {

	@Override
	protected Class<OfertaAcademica> getDomainClass() {
		return OfertaAcademica.class;
	}

	@Override
	public Carrera getCarreraEnOferta(Long idOferta) {
		Session session = this.sessionFactory.getCurrentSession();
		
		return (Carrera) session.createQuery("Select carrera from OfertaAcademica as oferta where oferta.id  = :idOferta")
				.setParameter("idOferta", idOferta)
			    .uniqueResult();
	}

	@Override
	public List<Comision> getComisionesEnOferta(Long idOferta) {
		Session session = this.sessionFactory.getCurrentSession();
		return (List<Comision>) session.createQuery("Select comisiones from OfertaAcademica as oferta where oferta.id  = :idOferta")
				.setParameter("idOferta", idOferta)
				.getResultList();
	}
	
	@Override
	public OfertaAcademica getOfertaPorNombre(String nombre) {
		Session session = this.sessionFactory.getCurrentSession();
		
		return (OfertaAcademica) session.createQuery("from OfertaAcademica as oferta where oferta.nombre  = :nombre")
				.setParameter("nombre", nombre)
			    .uniqueResult();
	}

	@Override
	public List<OfertaAcademica> getOfertasParaPeriodo(Long idPeriodo) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<OfertaAcademica> query = session.createQuery("from OfertaAcademica o where o.periodo.id = :idPeriodo");
		query.setParameter("idPeriodo", idPeriodo);
		return query.getResultList();
	}

}

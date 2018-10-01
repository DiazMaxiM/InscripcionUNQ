package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
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
	


}

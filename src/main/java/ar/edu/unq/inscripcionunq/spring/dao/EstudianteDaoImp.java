package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
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

	@Override
	public Integer getNroEstudiantesPorComision(Long idComision) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Estudiante> query = session.createQuery(
				"select distinct estudiante from Estudiante as estudiante join estudiante.registroComisiones c where c.id = :comisionId");
		query.setParameter("comisionId", idComision);
		if (query.getResultList() == null) return 0;	    
		return query.getResultList().size();
	}
}

package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.exception.UserInPollNotFoundException;
import ar.edu.unq.inscripcionunq.spring.exception.UsuarioNoExisteException;
import ar.edu.unq.inscripcionunq.spring.model.Encuesta;
import ar.edu.unq.inscripcionunq.spring.model.Estudiante;

@Repository

public class EncuestaDaoImp extends GenericDaoImp<Encuesta> implements EncuestaDao {

	@Override
	protected Class<Encuesta> getDomainClass() {
		return Encuesta.class;
	}

	public List<Encuesta> getTodasLasEncuestasActivasParaDni(String dni) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("select p from Encuesta p join p.estudiantes s where s.dni=:dni ")
				.setParameter("dni", dni).getResultList();
	}

	@Override
	public Estudiante getDatosDeUsuarioParaEncuesta(String dni, Long idPoll) throws UserInPollNotFoundException {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Estudiante> query = session.createQuery(
				"select estudiante from Encuesta as encuesta inner join encuesta.estudiantes estudiante where encuesta.id=:idEncuesta and estudiante.dni=:dni  ");
		query.setParameter("idEncuesta", idPoll);
		query.setParameter("dni", dni);
		List<Estudiante> results = query.getResultList();
		if (results.isEmpty()) {
			throw new UserInPollNotFoundException();
		}
		return query.getResultList().get(0);
	}

	@Override
	public List<Encuesta> getEncuestasDeUnaComision(Long idComision) {
		Session session = this.sessionFactory.getCurrentSession();
		return session
				.createQuery(
						"select e from Encuesta e join e.ofertasAcademicas oa join oa.comisiones c where c.id=:id ")
				.setParameter("id", idComision).getResultList();
	}

	@Override
	public Estudiante getDatosDeUsuarioDesdeEncuesta(String email) throws UsuarioNoExisteException {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Estudiante> query = session.createQuery(
				"select estudiante from Encuesta as encuesta inner join encuesta.estudiantes estudiante where estudiante.email=:email");
		query.setParameter("email", email);
		List<Estudiante> results = query.getResultList();
		if (results.isEmpty()) {
			throw new UsuarioNoExisteException();
		}
		return query.getResultList().get(0);
	}

	@Override
	public Encuesta getEncuestaConNombre(String nombre) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Encuesta) session.createQuery("from Encuesta where nombre = :nombre").setParameter("nombre", nombre)
				.uniqueResult();
	}
}

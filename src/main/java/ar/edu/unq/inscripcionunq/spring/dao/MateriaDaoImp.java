package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Carrera;
import ar.edu.unq.inscripcionunq.spring.model.Materia;
import ar.edu.unq.inscripcionunq.spring.model.TipoEstado;

@Repository
public class MateriaDaoImp extends GenericDaoImp<Materia> implements MateriaDao {

	@Override
	protected Class<Materia> getDomainClass() {
		return Materia.class;
	}

	@Override
	public List<Materia> getMateriasParaCarreras(List<Carrera> carreras) {
		List<Long> idCarreras = new ArrayList<Long>();
		for (Carrera carrera : carreras) {
			idCarreras.add(carrera.getId());
		}
		Session sesion = this.sessionFactory.getCurrentSession();
		Query<Materia> query = sesion.createQuery(
				"select distinct materia from Materia as materia join materia.carreras c where c.id in (:carrerasId) order by materia.nombre asc");
		query.setParameterList("carrerasId", idCarreras);
		return query.getResultList();
	}

	@Override
	public Materia encontrarMateriaConElMismoCodigo(String codigo) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Materia) session.createQuery("from Materia where codigo = :codigo").setParameter("codigo", codigo)
				.uniqueResult();
	}

	@Override
	public List<Materia> getMateriasParaCarrera(Long idCarrera) {
		Session sesion = this.sessionFactory.getCurrentSession();
		Query<Materia> query = sesion.createQuery(
				"select distinct materia from Materia as materia join materia.carreras c where c.id = :carreraId");
		query.setParameter("carreraId", idCarrera);
		return query.getResultList();
	}

	@Override
	public List<Materia> getMaterias() {
		Session sesion = this.sessionFactory.getCurrentSession();
		Query<Materia> query = sesion.createQuery(
				"select materia from Materia as materia where materia.estado = :estado order by materia.nombre asc");
		query.setParameter("estado", TipoEstado.ENABLED);

		return query.getResultList();
	}

	@Override
	public List<Materia> getPrerrequisitosParaMateria(Long id) {
		Session sesion = this.sessionFactory.getCurrentSession();
		Query<Materia> query = sesion.createQuery(
				"select materia from Materia as materia where materia.estado = :estado and materia.id = :id order by materia.nombre asc");
		query.setParameter("estado", TipoEstado.ENABLED);
		query.setParameter("id", id);

		return query.getResultList();
	}
}
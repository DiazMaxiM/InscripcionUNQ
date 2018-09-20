package ar.edu.unq.inscripcionunq.spring.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Administrador;

@Repository
public class AdministradorDaoImp extends GenericDaoImp<Administrador> implements AdministradorDao 
{

	@Override
	protected Class<Administrador> getDomainClass() {		
		return Administrador.class;
	}

	@Override
	public Administrador getAdministradorDesdeEmailYPassword(String email, String password) {
		Session session = this.sessionFactory.getCurrentSession();
		
		return (Administrador) session.createQuery("from Administrador where email = :email and "
				+ "password = :password")
				.setParameter("email", email)
			    .setParameter("password", password)
			    .uniqueResult();
	}


}

package ar.edu.unq.inscripcionunq.spring.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.Usuario;

@Repository
public class UsuarioDaoImp extends GenericDaoImp<Usuario> implements UsuarioDao 
{

	@Override
	protected Class<Usuario> getDomainClass() {		
		return Usuario.class;
	}

	@Override
	public Usuario obtenerUsuarioDesdeEmaill(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		
		return (Usuario) session.createQuery("from Usuario where email = :email")
				.setParameter("email", email)
			    .uniqueResult();
	}


}

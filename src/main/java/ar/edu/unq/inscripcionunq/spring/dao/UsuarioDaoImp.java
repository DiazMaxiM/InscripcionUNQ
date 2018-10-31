package ar.edu.unq.inscripcionunq.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import ar.edu.unq.inscripcionunq.spring.model.TipoPerfil;
import ar.edu.unq.inscripcionunq.spring.model.Usuario;

@Repository
public class UsuarioDaoImp extends GenericDaoImp<Usuario> implements UsuarioDao 
{

	@Override
	protected Class<Usuario> getDomainClass() {		
		return Usuario.class;
	}

	@Override
	public Usuario obtenerUsuarioDesdeEmail(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		
		return (Usuario) session.createQuery("from Usuario where email = :email")
				.setParameter("email", email)
			    .uniqueResult();
	}

	@Override
	public List<Usuario> obtenerUsuariosConPerfil(TipoPerfil perfil) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from Usuario u join u.perfiles p where p = :perfil")
				.setParameter("perfil", perfil)
				.getResultList();
	}


}

package maboutique.dao.jpa;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import maboutique.data.User;

@Stateless
@LocalBean
public class DaoUser {

	// Champs
	@PersistenceContext
	private EntityManager em;
	
	
	// Actions
	public int inserer( User user ) {
		em.persist(user);
		em.flush();
		return user.getId();
	}
	
	public void modifier( User user ) {
		em.merge( user );
	}
	
	public void supprimer( int iduser ) {
		em.remove( em.getReference( User.class, iduser ));
	}
	
	public User retrouver( int iduser ) {
		return em.find( User.class, iduser );
	}
	
	public List<User> listerTout() {
		var jpql = "SELECT u FROM User u ORDER BY u.name, u.firstname";
		var query = em.createQuery( jpql, User.class );
		return query.getResultList();
	}
	
	public User authentifier( String email, String motDePasse ) {
		var jpql = "SELECT u FROM User u "
		 + " WHERE u.login = :email AND u.pass = :mdp";
		var query = em.createQuery( jpql, User.class );
		query.setParameter( "email", email );
		query.setParameter( "mdp", motDePasse );
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	
}

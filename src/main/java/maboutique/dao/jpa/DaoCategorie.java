package maboutique.dao.jpa;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import maboutique.data.Categorie;

@Stateless
@LocalBean
public class DaoCategorie {

	// Champs
	@PersistenceContext
	private EntityManager em;
	
	
	// Actions
	public int inserer( Categorie categorie ) {
		em.persist(categorie);
		em.flush();
		return categorie.getId();
	}
	
	public void modifier( Categorie categorie ) {
		em.merge( categorie );
	}
	
	public void supprimer( int idcategorie ) {
		em.remove( em.getReference( Categorie.class, idcategorie ));
	}
	
	public Categorie retrouver( int idcategorie ) {
		return em.find( Categorie.class, idcategorie );
	}
	
	public List<Categorie> listerTout() {
		var jpql = "SELECT c FROM Categorie c ORDER BY c.nom";
		var query = em.createQuery( jpql, Categorie.class );
		return query.getResultList();
	}
	
	
}

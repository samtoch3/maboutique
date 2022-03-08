package maboutique.dao.jpa;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import maboutique.data.Produit;
import maboutique.data.User;

@Stateless
@LocalBean
public class DaoProduit {

	// Champs
	@PersistenceContext
	private EntityManager em;
	
	
	// Actions
	public int inserer( Produit produit ) {
		//produit.setQte(produit.getQte()+1);
		em.persist(produit);
		em.flush();
		return produit.getId();
	}
	
	public void modifier( Produit produit ) {
		em.merge( produit );
	}
	
	public void supprimer( int idproduit ) {
		//if()
		em.remove( em.getReference( Produit.class, idproduit ));
	}
	
	public Produit retrouver( int idproduit ) {
		return em.find( Produit.class, idproduit );
	}
	
	public List<Produit> listerTout() {
		var jpql = "SELECT p FROM Produit p ORDER BY p.nom";
		var query = em.createQuery( jpql, Produit.class );
		return query.getResultList();
	}
	
	public List<Produit> listerCatalogue(User u){
		var jpql = "SELECT p FROM Produit p WHERE p.statut='D' AND p.vendeur.id <> :v ORDER BY p.nom, p.categorie.nom";
		var query = em.createQuery( jpql, Produit.class );
		query.setParameter("v", u.getId());
		return query.getResultList();
	}
	
	
}

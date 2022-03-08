package maboutique.model;

import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoProduit;
import maboutique.data.Produit;
import maboutique.util.UtilJsf;
import java.io.Serializable;

@Named
@ViewScoped
@SuppressWarnings("serial")
public class ModelProduit implements Serializable {

	// Champs
	private List<Produit> liste;
	
	private Produit courant;
	
	@Inject 
	private DaoProduit daoProduit;
	
	
	public List<Produit> getListe() {
		if ( liste == null ) {
			liste = daoProduit.listerTout();
		}
		return liste;
	}
	
	public Produit getCourant() {
		if ( courant == null ) {
			courant = new Produit();
		}
		return courant;
	}
	
	
	// Actions
	public String actualiserCourant() {
		if ( courant != null ) {
			courant = daoProduit.retrouver( courant.getId() );
			if ( courant == null ) {
			UtilJsf.messageError( "La produit demandée n'existe pas" );
			return "liste";
		}
	}
	return null;
	}
	
	public String validerMiseAJour() {
		if (courant.getId()==null) {
			daoProduit.inserer( courant );
		} else {
			daoProduit.modifier( courant );
		}
		UtilJsf.messageInfo( "Mise à jour effectuée avec succès." );
		return "liste";
	}
	
	public String supprimer( Produit item ) {
		daoProduit.supprimer( item.getId() );
		liste = null;
		UtilJsf.messageInfo( "Suppression effectuée avec succès." );
		return "liste";
	}

	
}

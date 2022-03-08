package maboutique.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoProduit;
import maboutique.data.Produit;
import maboutique.util.UtilJsf;

@SessionScoped
@Named
@SuppressWarnings("serial")
public class ModelPanier implements Serializable {

	private List<Produit> panier = new ArrayList<Produit>();

	@Inject
	private ModelConnexion modelConnexion;
	
	@Inject
	private DaoProduit daoProduit;
	
	public List<Produit> getPanier() {
		return panier;
	}
	
	public String valider() {
		for(Produit p : panier) {
			p.setStatut("C");
			System.out.println(p.getVendeur());
			p.setAcheteur(modelConnexion.getActifUser());
			daoProduit.modifier(p);
		}
		panier.clear();
		UtilJsf.messageInfo("Votre commande a été bien enregistrée");
		return "home";
	}
	
	public double getTotal() {
		double t = 0;
		for(Produit p : panier) {
			t += p.getPrix();
		}
		return t;
	}
	
	public String supprimer(Produit p) {
		panier.remove(p);
		return null;
	}
	
	
	
	
}

package maboutique.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoProduit;
import maboutique.data.Produit;

@Named
@ViewScoped
@SuppressWarnings("serial")
public class ModelCatalogue implements Serializable {

	// Champs
	private List<ProduitAChoisir> liste;

	@Inject
	private DaoProduit daoProduit;
	
	@Inject
	private ModelPanier modelPanier;

	@Inject
	private ModelConnexion modelConnexion;

	public String remplirPanier() {
		modelPanier.getPanier().clear();
		for(ProduitAChoisir p : liste) {
			if(p.isChoisi())
				modelPanier.getPanier().add(p.getProduit());
		}
		
		return "panier";
	}
	
	public List<ProduitAChoisir> getListe() {
		
		liste =  new ArrayList<ModelCatalogue.ProduitAChoisir>();
		
		for(Produit p : daoProduit.listerCatalogue(modelConnexion.getActifUser())) {
			ProduitAChoisir pac = new ProduitAChoisir(p, false);
			if(modelPanier.getPanier().contains(pac.getProduit())) { 
				pac.setChoisi(true);
			}
			liste.add(pac);
		}
			
		
		return liste;
	}

	// Classe auxiliaire
	public static class ProduitAChoisir {
		// Champs
		private Produit produit;
		private boolean choisi;

		// Constructeur
		public ProduitAChoisir(Produit produit, boolean choisi) {
			this.produit = produit;
			this.choisi = choisi;
		}

		// Getters & Setters
		public Produit getProduit() {
			return produit;
		}

		public void setProduit(Produit produit) {
			this.produit = produit;
		}

		public boolean isChoisi() {
			return choisi;
		}

		public void setChoisi(boolean choisi) {
			this.choisi = choisi;
		}
	}

}

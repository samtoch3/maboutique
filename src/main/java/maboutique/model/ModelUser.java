package maboutique.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoUser;
import maboutique.data.User;
import maboutique.util.UtilJsf;
import java.io.Serializable;

@Named
@ViewScoped
@SuppressWarnings("serial")
public class ModelUser implements Serializable {

	// Champs
	private List<User> liste;

	private List<User> listWithNull;

	private User courant;

	@Inject
	private DaoUser daoUser;

	public List<User> getListe() {
		if (liste == null) {
			liste = daoUser.listerTout();
		}
		return liste;
	}

	public List<User> getListWithNull() {
		if (listWithNull == null) {
			listWithNull = new ArrayList<>();
			listWithNull.add(null);
			listWithNull.addAll(getListe());
		}
		return listWithNull;
	}

	public User getCourant() {
		if (courant == null) {
			courant = new User();
		}
		return courant;
	}

	// Actions
	public String actualiserCourant() {
		if (courant != null) {
			courant = daoUser.retrouver(courant.getId());
			if (courant == null) {
				UtilJsf.messageError("L'utilisateur demandé n'existe pas");
				return "liste";
			}
		}
		return null;
	}

	public String validerMiseAJour() {
		if (courant.getId() == null) {
			daoUser.inserer(courant);
		} else {
			daoUser.modifier(courant);
		}
		UtilJsf.messageInfo("Mise à jour effectuée avec succès.");
		return "liste";
	}

	public String supprimer(User item) {
		daoUser.supprimer(item.getId());
		liste = null;
		UtilJsf.messageInfo("Suppression effectuée avec succès.");
		return "liste";
	}

}

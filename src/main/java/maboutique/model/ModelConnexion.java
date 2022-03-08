package maboutique.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoUser;
import maboutique.data.User;
import maboutique.util.UtilJsf;

@Named @SessionScoped
public class ModelConnexion implements Serializable {
	
	// champs
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String login;
	
	protected String pass;
	
	protected User actifUser;
	
	@Inject
	private DaoUser daoUser;

	// getters & setters
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public User getActifUser() {
		return actifUser;
	}
	
	// methodes / actions

	public String login() {
		actifUser = daoUser.authentifier(login, pass);
	
		if ( actifUser != null ) {
			 UtilJsf.messageInfo( "Connexion réussie." );
			 login = null;
			 pass = null;
			 return "home";
			 //return "/pages/home?faces-redirect=true";
		} else {
			 UtilJsf.messageError( "Identifiant ou mot de passe invalide." );
			 return "login";
			 //return "/pages/login?faces-redirect=true";
		}
	}
	
	public String logout() {
		UtilJsf.sessionInvalidate();
		UtilJsf.messageInfo( "Vous avez été déconnecté" );
		return "login";
		//return "/pages/login?faces-redirect=true";
	}
	
	public boolean isLoggedIn() {
		 return actifUser != null;
		}

	public boolean isAdmin() {
		return actifUser != null && actifUser.isAdmin();
	}
}

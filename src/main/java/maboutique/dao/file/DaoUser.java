package maboutique.dao.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;

import maboutique.data.User;


//@Singleton
//@LocalBean
public class DaoUser {
	
	
	// Constantes
	
	private static final String CHEMIN_FICHIER = "C:\\TEMP\\tp-web-java\\utilisateur.data";
	
	
	// Champs
	
	private Map<Integer, User> map = new HashMap<>();
	
	
	// Initialisation et fermeture
	
	@PostConstruct
	private void init() {

		try {
			
			BufferedReader in = null;
			String ligne;
			in =new BufferedReader(new FileReader( CHEMIN_FICHIER ));
			while ((ligne = in.readLine()) != null) {
				String items[] = ligne.split(";");
				User u = new User();
				u.setId( Integer.parseInt( items[0]) );
				u.setName( items[1] );
				u.setFirstname( items[2] );
				u.setLogin( items[3] );
				u.setPass( items[4] );
				u.setAdmin( Boolean.parseBoolean( items[5] ) );
				map.put( u.getId(), u);
			}
			in.close();
			
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
	}
	
	
	// Actions
	
	@Lock(LockType.WRITE)
	public int inserer( User User ) {
		User.setId( Collections.max( map.keySet() ) + 1 );
		map.put( User.getId(), User );
		sauvegarder();
		return User.getId();
	}
	
	@Lock(LockType.WRITE)
	public void modifier( User User ) {
		map.put( User.getId(), User );
		sauvegarder();
	}
	
	@Lock(LockType.WRITE)
	public void supprimer( int idUser ) {
		map.remove( idUser );
		sauvegarder();
	}
	
	@Lock(LockType.READ)
	public User retrouver( int idUser ) {
		return clone( map.get( idUser ) );
	}
	
	@Lock(LockType.READ)
	public List<User> listerTout() {
		var liste = new ArrayList<User>();
		for ( User u : map.values() ) {
			liste.add( clone( u ) );
		}
		return liste;
	}
	
	@Lock(LockType.READ)
	public User authentifier( String Login, String  motDePasse ) {
		for( User u : map.values() ) {
			if ( u.getLogin().equals(Login) && u.getPass().equals(motDePasse) ) {
				return clone( u );
			}
		}
		return null;
	}

	
	// Méthodes auxiliaires
	
	private User clone( User source ) {
		User target = new User();
		target.setId( source.getId() );
		target.setName( source.getName() );
		target.setFirstname( source.getFirstname() );
		target.setLogin( source.getLogin() );
		target.setPass( source.getPass() );
		target.setAdmin( source.isAdmin() );
		return target;
	}

	
	private void sauvegarder() {
		try {
			BufferedWriter out = null;
			out =new BufferedWriter(new FileWriter( CHEMIN_FICHIER ) );
			String  ligne;
			for( User u : map.values() ) {
				ligne = String.format( "%d;%s;%s;%s;%s;%b", u.getId(), u.getName(), u.getFirstname(), u.getLogin(), u.getPass(), u.isAdmin() );
				out.write( ligne );
				out.newLine();
			}
			out.close();
			
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
	}
}

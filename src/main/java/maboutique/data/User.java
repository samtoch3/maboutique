package maboutique.data;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="utilisateur")
public class User implements Serializable {

	// champs
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="id")
	private Integer id;
	
	@NotBlank( message="Le nom doit être renseigné")
	@Size(max=50, message="Valeur trop longue pour le nom : 50 car. maxi" )
	@Column(name="nom")
	private String name;
	
	@NotBlank( message="Le prénom doit être renseigné")
	@Size(max=50, message="Valeur trop longue pour le prénom : 50 car. maxi")
	@Column(name="prenom")
	private String firstname;
	
	@NotBlank( message="L'adresse e-mail doit être renseignée")
	@Email( message="L'adresse e-mail n'est pas valide.")
	@Column(name="email")
	private String login;
	
	@NotBlank( message="Le mot de passe doit être renseigné")
	@Size(min=5, message="Le mot d passe doit avoir au moins 5 caractères" )
	@Size(max=50, message="Le mot d passe doit avoir au plus 50 caractères")
	@Column(name="motDePasse")
	private String pass;
	
	@Column(name="admin")
	private boolean admin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}

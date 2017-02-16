package fr.assj.gestiontournoi.utilisateur;

import java.sql.Timestamp;
import java.util.Date;

public class Utilisateur {
	public static final int nbEssaiConnexion = 3; // nb d'échec de connexions toléré avant verrouillage du compte 
	public static final int nbMoisAvantExpirationPwd = 6; // durée de validité (en mois) d'un mot de passe avant expiration
	public static final int nbMinCaracPwd = 6; // nb minimum de caractères d'un mot de passe
	public static final int nbMaxCaracPwd = 25; // nb maximum de caractères d'un mot de passe
	public static final boolean forcerMinusculePwd = true; // indique si le mot de passe doit contenir au moins une lettre en minuscule A->Z
	public static final boolean forcerMajusculePwd = true; // indique si le mot de passe doit contenir au moins une lettre en majuscule A->Z
	public static final boolean forcerChiffrePwd = true; // ndique si le mot de passe doit contenir au moins 1 chiffre 0-9
	public static final boolean forcerSpecialPwd = true; // indique si le mot de passe doit contenir au moins un caractère spécial @#$%
	public static final String listeCaracPwd = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // liste des caractères alphannumériques autorisés 
	public static final String listeCaracSpecialPwd = "@ # $ %"; // liste des caractères spéciaux autorisés (séparés par des espaces
	
	private int id;
  	private String login;
  	private String password; // mot de passe hashé !! (en  utilisant l'id comme clé de hashage)
  	private boolean pwdUsageUnique;
  	private boolean actif;
  	private int nbEchec;
	private Timestamp dateDerniereConnexion;
  	private Date dateExpiration;
  	private String email;
  	private boolean cguApprouve;
  	
	/**
	 * @return La valeur de l'attribut id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id La valeur a affecter à l'attribut id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return La valeur de l'attribut login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login La valeur a affecter à l'attribut login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * @return La valeur de l'attribut password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password La valeur a affecter à l'attribut password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return La valeur de l'attribut actif
	 */
	public boolean isActif() {
		return actif;
	}
	/**
	 * @param actif La valeur a affecter à l'attribut actif
	 */
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	/**
	 * @return La valeur de l'attribut nbEchec
	 */
	public int getNbEchec() {
		return nbEchec;
	}
	/**
	 * @param nbEchec La valeur a affecter à l'attribut nbEchec
	 */
	public void setNbEchec(int nbEchec) {
		this.nbEchec = nbEchec;
	}
	/**
	 * @return La valeur de l'attribut dateDerniereConnexion
	 */
	public Timestamp getDateDerniereConnexion() {
		return dateDerniereConnexion;
	}
	/**
	 * @param dateDerniereConnexion La valeur a affecter à l'attribut dateDerniereConnexion
	 */
	public void setDateDerniereConnexion(Timestamp dateDerniereConnexion) {
		this.dateDerniereConnexion = dateDerniereConnexion;
	}
	/**
	 * @return La valeur de l'attribut dateExpiration
	 */
	public Date getDateExpiration() {
		return dateExpiration;
	}
	/**
	 * @param dateExpiration La valeur a affecter à l'attribut dateExpiration
	 */
	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	/**
	 * @return La valeur de l'attribut email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email La valeur a affecter à l'attribut email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return La valeur de l'attribut pwdUsageUnique
	 */
	public boolean isPwdUsageUnique() {
		return pwdUsageUnique;
	}
	/**
	 * @param pwdUsageUnique La valeur a affecter à l'attribut pwdUsageUnique
	 */
	public void setPwdUsageUnique(boolean pwdUsageUnique) {
		this.pwdUsageUnique = pwdUsageUnique;
	}

	/**
	 * @return La valeur de l'attribut cguApprouve
	 */
	public boolean isCguApprouve() {
		return cguApprouve;
	}
	/**
	 * @param cguApprouve La valeur a affecter à l'attribut cguApprouve
	 */
	public void setCguApprouve(boolean cguApprouve) {
		this.cguApprouve = cguApprouve;
	}
	
	/**
	 * 
	 * @return le statut du compte fournisseur
	 */
	public String getStatut() {
		if (!this.isActif()) {
			return "Supprimé";
		} else if (this.isVerrouille()) {
			return "Verrouillé";
		} else if (this.isExpire()) {
			return "Expiré";
		} else {
			return "OK";
		}
	}
	
	/**
	 * 
	 * @return true si le compte a été vérouillé après 3 échecs de connexions
	 */
	public boolean isVerrouille() {
		return this.nbEchec >= nbEssaiConnexion ? true : false;
	}
	
	/**
	 * 
	 * @return true si la date d'expiration du compte est passée
	 */
	public boolean isExpire() {
		return this.dateExpiration.before(new Date()) ? true : false;
	}
	
	private boolean admin;

	/**
	 * 
	 * @return true si l'utilisateur est reconnu comme administrateur du site
	 */
	public boolean isAdmin() {
		return this.admin;
	}
	
	/**
	 * 
	 * @param isAdmin
	 */
	public void setAdmin(boolean isAdmin) {
		this.admin = isAdmin;
	}
}

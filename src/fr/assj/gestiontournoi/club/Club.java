package fr.assj.gestiontournoi.club;

import java.util.Vector;

import fr.assj.gestiontournoi.contact.Contact;
import fr.assj.gestiontournoi.equipe.Equipe;

public class Club {
	private int id;
	private String nom;
	private int numeroAffiliation;
	private String ligue;
	private String district;
	private String siteWeb;
	private String adresse1;
	private String adresse2;
	private String adresse3;
	private String codePostal;
	private String ville;
	private String pays;
	private String tel1;
	private String tel2;
	private String fax1;
	private String fax2;
	private String email1;
	private String email2;
	private String couleur1;
	private String couleur2;
	private String stade;
	private String logo;
	private Vector<Equipe> listeEquipes;
	private Vector<Contact> listeContacts;
	private Vector<Equipe> listeTerrains;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getNumeroAffiliation() {
		return numeroAffiliation;
	}
	public void setNumeroAffiliation(int numeroAffiliation) {
		this.numeroAffiliation = numeroAffiliation;
	}
	public String getLigue() {
		return ligue;
	}
	public void setLigue(String ligue) {
		this.ligue = ligue;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getSiteWeb() {
		return siteWeb;
	}
	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}
	public String getAdresse1() {
		return adresse1;
	}
	public void setAdresse1(String adresse1) {
		this.adresse1 = adresse1;
	}
	public String getAdresse2() {
		return adresse2;
	}
	public void setAdresse2(String adresse2) {
		this.adresse2 = adresse2;
	}
	public String getAdresse3() {
		return adresse3;
	}
	public void setAdresse3(String adresse3) {
		this.adresse3 = adresse3;
	}
	public String getAdresse() {
		return adresse1 + (adresse2!=null ? "<br>" +adresse2 : "") + (adresse3!=null ? "<br>" + adresse3 : "") + "<br>" + codePostal + "<br>" + ville;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getFax1() {
		return fax1;
	}
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}
	public String getFax2() {
		return fax2;
	}
	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getCouleur1() {
		return couleur1;
	}
	public void setCouleur1(String couleur1) {
		this.couleur1 = couleur1;
	}
	public String getCouleur2() {
		return couleur2;
	}
	public void setCouleur2(String couleur2) {
		this.couleur2 = couleur2;
	}
	public String getStade() {
		return stade;
	}
	public void setStade(String stade) {
		this.stade = stade;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public Vector<Equipe> getListeEquipes() {
		return listeEquipes;
	}
	public void setListeEquipes(Vector<Equipe> listeEquipes) {
		this.listeEquipes = listeEquipes;
	}
	
	public void ajouterEquipe(Equipe equipe) {
		if (equipe == null) {
			return;
		}
		if (this.listeEquipes == null) {
			// si la liste des équipes n'a pas encore été instanciée alors on le fait
			this.listeEquipes = new Vector<Equipe>();
		}
		this.listeEquipes.add(equipe);
	}
	
	public Vector<Contact> getListeContacts() {
		return listeContacts;
	}
	public void setListeContacts(Vector<Contact> listeContacts) {
		this.listeContacts = listeContacts;
	}
	public void ajouterContact(Contact contact) {
		if (contact == null) {
			return;
		}
		if (this.listeContacts == null) {
			// si la liste des équipes n'a pas encore été instanciée alors on le fait
			this.listeContacts = new Vector<Contact>();
		}
		this.listeContacts.add(contact);
	}
	
	public Vector<Equipe> getListeTerrains() {
		return listeTerrains;
	}
	public void setListeTerrains(Vector<Equipe> listeTerrains) {
		this.listeTerrains = listeTerrains;
	}
	public void ajouterTerrain(Equipe terrain) {
		if (terrain == null) {
			return;
		}
		if (this.listeTerrains == null) {
			// si la liste des équipes n'a pas encore été instanciée alors on le fait
			this.listeTerrains = new Vector<Equipe>();
		}
		this.listeTerrains.add(terrain);
	}
}

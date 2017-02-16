package fr.assj.gestiontournoi.equipe;

import java.util.Vector;

import fr.assj.gestiontournoi.categorie.Categorie;
import fr.assj.gestiontournoi.groupe.Groupe;
import fr.assj.gestiontournoi.match.Match;

public class Equipe {
	private int id;
	private String libelle;
	private int photo;
	private int club;
	private String libelleClub;
	private String pays;
	private String ligue;
	private String district;
	
	private Categorie categorie;
	
	private int nbPoints;
	private int nbMatchsJoues;
	private int nbVictoires;
	private int nbNuls;
	private int nbDefaites;
	private int nbButsPour;
	private int nbButsContre;
	private int classement;
	
	private Groupe groupePhase1;
	
	
	private Vector<Match> listeMatchs;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public int getPhoto() {
		return photo;
	}
	public void setPhoto(int photo) {
		this.photo = photo;
	}
	public int getClub() {
		return club;
	}
	public void setClub(int club) {
		this.club = club;
	}
	public int getNbPoints() {
		return nbPoints;
	}
	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}
	public int getNbMatchsJoues() {
		return nbMatchsJoues;
	}
	public void setNbMatchsJoues(int nbMatchsJoues) {
		this.nbMatchsJoues = nbMatchsJoues;
	}
	public int getNbVictoires() {
		return nbVictoires;
	}
	public void setNbVictoires(int nbVictoires) {
		this.nbVictoires = nbVictoires;
	}
	public int getNbNuls() {
		return nbNuls;
	}
	public void setNbNuls(int nbNuls) {
		this.nbNuls = nbNuls;
	}
	public int getNbDefaites() {
		return nbDefaites;
	}
	public void setNbDefaites(int nbDefaites) {
		this.nbDefaites = nbDefaites;
	}
	public int getNbButsPour() {
		return nbButsPour;
	}
	public void setNbButsPour(int nbButsPour) {
		this.nbButsPour = nbButsPour;
	}
	public int getNbButsContre() {
		return nbButsContre;
	}
	public void setNbButsContre(int nbButsContre) {
		this.nbButsContre = nbButsContre;
	}
	public int getClassement() {
		return classement;
	}
	public void setClassement(int classement) {
		this.classement = classement;
	}
	public void setListeMatchs(Vector<Match> listeMatchs) {
		this.listeMatchs = listeMatchs;
	}
	public Vector<Match> getListeMatchs() {
		return listeMatchs;
	}
	public void ajouterMatch(Match match) {
		if (match == null) {
			return;
		}
		if (this.listeMatchs == null) {
			// si la liste des équipes n'a pas encore été instanciée alors on le fait
			this.listeMatchs = new Vector<Match>();
		}
		this.listeMatchs.add(match);
	}
	public Groupe getGroupePhase1() {
		return groupePhase1;
	}
	public void setGroupePhase1(Groupe groupePhase1) {
		this.groupePhase1 = groupePhase1;
	}
	public String getLibelleClub() {
		return libelleClub;
	}
	public void setLibelleClub(String libelleClub) {
		this.libelleClub = libelleClub;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getLigue() {
		return ligue;
	}
	public void setLigue(String ligue) {
		this.ligue = ligue;
	}
}

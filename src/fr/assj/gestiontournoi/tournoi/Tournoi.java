package fr.assj.gestiontournoi.tournoi;

import java.util.Date;
import java.util.Vector;

import fr.assj.gestiontournoi.equipe.Equipe;
import fr.assj.gestiontournoi.groupe.Groupe;

public class Tournoi {
	
	// Attributs
	private int id;
	private String libelle;
	private int statut;
	private String libelleStatut;
	
	private Date dateTournoi;
	private String heureDebut;
	private int typeTournoi;
	private String libelleTypeTournoi;
	private int categorie;
	private String description;
	private String libelleCategorie;
	private String lieu;
	private int reglement;
	private int plaquette;
	private int nbEquipes;
	private int nbTerrains;
	private int dureeRencontre;
	private int baremeVictoire;
	private int baremeNul;
	private int baremeDefaite;
	private int nbGroupes;
	private int nbEquipesQualifiees;
	private int nbEquipesQualifieesParGroupe;
	private boolean consolante;
	
	private Vector<Equipe> listeEquipesInscrites;
	private Vector<Groupe> listeGroupes;
	
	
	// Méthodes
	
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
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public String getLibelleStatut() {
		return libelleStatut;
	}
	public void setLibelleStatut(String libelleStatut) {
		this.libelleStatut = libelleStatut;
	}
	public Date getDateTournoi() {
		return dateTournoi;
	}
	public void setDateTournoi(Date dateTournoi) {
		this.dateTournoi = dateTournoi;
	}
	public int getTypeTournoi() {
		return typeTournoi;
	}
	public void setTypeTournoi(int typeTournoi) {
		this.typeTournoi = typeTournoi;
	}
	public String getLibelleTypeTournoi() {
		return libelleTypeTournoi;
	}
	public void setLibelleTypeTournoi(String libelleTypeTournoi) {
		this.libelleTypeTournoi = libelleTypeTournoi;
	}
	public int getCategorie() {
		return categorie;
	}
	public void setCategorie(int categorie) {
		this.categorie = categorie;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLibelleCategorie() {
		return libelleCategorie;
	}
	public void setLibelleCategorie(String libelleCategorie) {
		this.libelleCategorie = libelleCategorie;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public int getReglement() {
		return reglement;
	}
	public void setReglement(int reglement) {
		this.reglement = reglement;
	}
	public int getPlaquette() {
		return plaquette;
	}
	public void setPlaquette(int plaquette) {
		this.plaquette = plaquette;
	}
	public int getNbEquipes() {
		return nbEquipes;
	}
	public void setNbEquipes(int nbEquipes) {
		this.nbEquipes = nbEquipes;
	}
	public int getNbTerrains() {
		return nbTerrains;
	}
	public void setNbTerrains(int nbTerrains) {
		this.nbTerrains = nbTerrains;
	}
	public int getDureeRencontre() {
		return dureeRencontre;
	}
	public void setDureeRencontre(int dureeRencontre) {
		this.dureeRencontre = dureeRencontre;
	}
	public int getBaremeVictoire() {
		return baremeVictoire;
	}
	public void setBaremeVictoire(int baremeVictoire) {
		this.baremeVictoire = baremeVictoire;
	}
	public int getBaremeNul() {
		return baremeNul;
	}
	public void setBaremeNul(int baremeNul) {
		this.baremeNul = baremeNul;
	}
	public int getBaremeDefaite() {
		return baremeDefaite;
	}
	public void setBaremeDefaite(int baremeDefaite) {
		this.baremeDefaite = baremeDefaite;
	}
	public int getNbGroupes() {
		return nbGroupes;
	}
	public void setNbGroupes(int nbGroupes) {
		this.nbGroupes = nbGroupes;
	}
	public int getNbEquipesQualifiees() {
		return nbEquipesQualifiees;
	}
	public void setNbEquipesQualifiees(int nbEquipesQualifiees) {
		this.nbEquipesQualifiees = nbEquipesQualifiees;
	}
	public int getNbEquipesQualifieesParGroupe() {
		return nbEquipesQualifieesParGroupe;
	}
	public void setNbEquipesQualifieesParGroupe(int nbEquipesQualifieesParGroupe) {
		this.nbEquipesQualifieesParGroupe = nbEquipesQualifieesParGroupe;
	}
	public boolean isConsolante() {
		return consolante;
	}
	public void setConsolante(boolean consolante) {
		this.consolante = consolante;
	}
	
	public void setListeEquipesInscrites(Vector<Equipe> listeEquipesInscrites) {
		this.listeEquipesInscrites = listeEquipesInscrites;
	}
	public Vector<Equipe> getListeEquipesInscrites() {
		return listeEquipesInscrites;
	}
	
	public void setListeGroupes(Vector<Groupe> listeGroupes) {
		this.listeGroupes = listeGroupes;
	}
	public Vector<Groupe> getListeGroupes() {
		return listeGroupes;
	}
	public String getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}
}

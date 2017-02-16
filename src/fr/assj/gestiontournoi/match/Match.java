package fr.assj.gestiontournoi.match;

import java.util.Date;

import fr.assj.gestiontournoi.equipe.Equipe;

public class Match {
	private int id;
	private int codeTournoi;
	private String groupe;
	private Date jour;
	private String horaire;
	private String lieu;
	private int statut;
	private int equipeLoc;
	private Equipe equipeLocale;
	private int equipeVis;
	private Equipe equipeVisiteur;
	private int scoreLocal;
	private int scoreVisiteur;
	private int tour;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCodeTournoi() {
		return codeTournoi;
	}
	public void setCodeTournoi(int codeTournoi) {
		this.codeTournoi = codeTournoi;
	}
	public String getGroupe() {
		return groupe;
	}
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}
	public Date getJour() {
		return jour;
	}
	public void setJour(Date jour) {
		this.jour = jour;
	}
	public String getHoraire() {
		return horaire;
	}
	public void setHoraire(String horaire) {
		this.horaire = horaire;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public int getEquipeLoc() {
		return equipeLoc;
	}
	public void setEquipeLoc(int equipeLoc) {
		this.equipeLoc = equipeLoc;
	}
	public Equipe getEquipeLocale() {
		return equipeLocale;
	}
	public void setEquipeLocale(Equipe equipeLocale) {
		this.equipeLocale = equipeLocale;
	}
	public int getEquipeVis() {
		return equipeVis;
	}
	public void setEquipeVis(int equipeVis) {
		this.equipeVis = equipeVis;
	}
	public Equipe getEquipeVisiteur() {
		return equipeVisiteur;
	}
	public void setEquipeVisiteur(Equipe equipeVisiteur) {
		this.equipeVisiteur = equipeVisiteur;
	}
	public int getScoreLocal() {
		return scoreLocal;
	}
	public void setScoreLocal(int scoreLocal) {
		this.scoreLocal = scoreLocal;
	}
	public int getScoreVisiteur() {
		return scoreVisiteur;
	}
	public void setScoreVisiteur(int scoreVisiteur) {
		this.scoreVisiteur = scoreVisiteur;
	}
	public int getTour() {
		return tour;
	}
	public void setTour(int tour) {
		this.tour = tour;
	}
	
	
}

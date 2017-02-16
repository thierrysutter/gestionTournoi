package fr.assj.gestiontournoi.groupe;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import fr.assj.gestiontournoi.equipe.Equipe;
import fr.assj.gestiontournoi.match.Match;

public class Groupe {
	private int codeTournoi;
	private String libelle;
	private Vector<Equipe> listeEquipes;
	private Vector<Match> listeMatchs;
	
	public int getCodeTournoi() {
		return codeTournoi;
	}
	public void setCodeTournoi(int codeTournoi) {
		this.codeTournoi = codeTournoi;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Vector<Equipe> getListeEquipes() {
		return trierEquipeParLibelle(listeEquipes);
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
	
	public int getNbEquipes() {
		if (this.listeEquipes == null) {
			return -1;
		}
		return this.listeEquipes.size();
	}
	
	public void ajouterMatch(Match match) {
		if (match == null) {
			return;
		}
		if (this.listeMatchs == null) {
			// si la liste des matchs n'a pas encore été instanciée alors on le fait
			this.listeMatchs = new Vector<Match>();
		}
		this.listeMatchs.add(match);
	}
	
	public Vector<Match> getListeMatchs() {
		return listeMatchs;
	}
	
//	public Vector<Match> getListeMatchs() {
//		this.listeMatchs = new Vector<Match>();
//		
//		for(Equipe equipe : this.listeEquipes) {
//			for(Match match : equipe.getListeMatchs()) {
//				if (!listeMatchs.contains(match)) {
//					listeMatchs.add(match);
//				}
//			}
//		}
//		
//		// suppression des doublons
//		
//		
//		
//		listeMatchs.trimToSize();
//		
//		// trie des matchs par numéro du tour
//		Collections.sort(listeMatchs, new Comparator<Object>() {
//			public int compare(final Object o1, final Object o2) {
//				if (o1 != o2) {
//					final int codeCritere1 = ((Match) o1).getTour();
//					final int codeCritere2 = ((Match) o2).getTour();
//
//					if (codeCritere1 <= codeCritere2) {
//						return -1;
//					} else {
//						return 1;
//					}
//					
//				}
//				return 0;
//			}
//		});
//		
//		return this.listeMatchs;
//	}
	
	/**
	 * Tri de la liste des équipes par ordre alphabétique
	 * 
	 * @param listeEquipes
	 * @return le vecteur trié
	 */
	public static Vector<Equipe> trierEquipeParLibelle(Vector<Equipe> listeEquipes) {
		if (listeEquipes == null) {
			return null;
		}
		
		Collections.sort(listeEquipes, new Comparator<Object>() {
			@Override
			public int compare(final Object o1, final Object o2) {
				if (o1 == o2) {
					return 0;
				}
				return ((Equipe) o1).getLibelle().compareTo(((Equipe) o2).getLibelle());
			}
		});
		return listeEquipes;
	}
	
	
	/**
	 * Tri de la liste des équipes pour obtenir le classement du groupe en fonction des critères
	 * 
	 * 1. nombre de points
	 * 2. différence de buts (goal average)
	 * 3. nombre de buts marqués
	 * 4. nombre de buts encaissés
	 * 5. ordre alphabétique
	 * 
	 */
	public Vector<Equipe> getClassement() {
		if (this.listeEquipes == null) {
			return null;
		}
		
		Collections.sort(this.listeEquipes, new Comparator<Object>() {
			@Override
			public int compare(final Object o1, final Object o2) {
				if (o1 == o2) {
					return 0;
				}
				if (((Equipe) o1).getClassement() > ((Equipe) o2).getClassement()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		return this.listeEquipes;
	}
	
}

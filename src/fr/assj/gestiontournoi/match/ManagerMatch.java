package fr.assj.gestiontournoi.match;

import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.equipe.Equipe;

public class ManagerMatch {
	/**
	 * Logger spécifique à cette classe.
	 */
	protected static Logger logger = Logger.getLogger(ManagerMatch.class);
	
	/**
	 * 
	 * @param connection
	 * @param tour
	 * @param listeEquipes
	 * @param jour
	 * @param codeTournoi
	 * @param libelleTournoi
	 */
	public static void creerTour(Connection connection, int tour, Vector<Equipe> listeEquipes, Date jour, String heure, int codeTournoi, String libelleTournoi) {
		try {
		
			int mid = listeEquipes.size() / 2;
			
			Vector<Equipe> liste1 = new Vector<Equipe>();
			for (int j=0; j<mid; j++) {
				liste1.add(listeEquipes.elementAt(j));
			}
			
			Vector<Equipe> liste2 = new Vector<Equipe>();
			for(int j=listeEquipes.size()-1; j>=mid; j--) {
				liste2.add(listeEquipes.elementAt(j));
			}
			
			for (int k=0; k<liste1.size(); k++) {
				Equipe equipe1;
				Equipe equipe2;
				if (tour%2 == 1) {
					equipe1 = liste1.elementAt(k);
					equipe2 = liste2.elementAt(k);
				} else {
					equipe1 = liste2.elementAt(k);
					equipe2 = liste1.elementAt(k);
				}
				
				Match match = new Match();
				match.setCodeTournoi(codeTournoi);
				match.setEquipeLoc(equipe1.getId());
				//match.setLibelleEquipe1(equipe1.getLibelle());
				match.setEquipeVis(equipe2.getId());
				//match.setLibelleEquipe2(equipe2.getLibelle());
				match.setTour(tour);
				match.setJour(jour);
				match.setHoraire(heure);
				match.setLieu("");
				
				if (equipe1.getId()!=-1 && equipe2.getId()!=-1) {
					equipe1.ajouterMatch(match);
					equipe2.ajouterMatch(match);
					// création du match en base
					ajouterMatchGroupe(connection, codeTournoi, libelleTournoi, equipe1.getId(), equipe2.getId(), tour, jour, "12:00", "");
				}
			}
		} catch (Exception e) {
			logger.error("erreur : " + e);
		}		
	}
	
	/**
	 * 
	 * @param connection
	 * @param codeTournoi
	 * @param groupe
	 * @param equipe1
	 * @param equipe2
	 * @param tour
	 * @param jour
	 * @param heure
	 * @param lieu
	 * @return
	 * @throws Exception
	 */
	public static int ajouterMatchGroupe(Connection connection, int codeTournoi, String groupe, int equipe1, int equipe2, int tour, Date jour, String heure, String lieu) throws Exception {
		try {
			logger.info("Insertion du match.");
			return new DaoMatch(connection).ajouterMatchGroupe(codeTournoi, groupe, jour, tour, heure, lieu, equipe1, equipe2);
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param codeTournoi
	 * @param groupe
	 * @param equipe
	 * @return
	 * @throws Exception
	 */
	public static Vector<Match> trouverMatchsEquipe(Connection connection, int codeTournoi, String groupe, int equipe) throws Exception {
		try {
			logger.info("Recherche les matchs d'une équipe.");
			return new DaoMatch(connection).trouverMatchsEquipe(codeTournoi, groupe, equipe);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param codeTournoi
	 * @param groupe
	 * @return
	 * @throws Exception
	 */
	public static Vector<Match> trouverMatchsGroupe(Connection connection, int codeTournoi, String groupe) throws Exception {
		try {
			logger.info("Recherche les matchs d'une équipe.");
			return new DaoMatch(connection).trouverMatchsGroupe(codeTournoi, groupe);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param connexion
	 * @param codeTournoi
	 * @return
	 * @throws Exception
	 */
	public static int supprimerMatchsTournoi(Connection connexion, int codeTournoi) throws Exception {
		try {
			return new DaoMatch(connexion).supprimerMatchsTournoi(codeTournoi);
		} catch (Exception e) {
			logger.error("Erreur lors de la création d'un groupe en base.");
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param codeTournoi
	 * @return
	 * @throws Exception
	 */
	public static Vector<Match> trouverMatchsTournoi(Connection connection, int codeTournoi) throws Exception {
		try {
			logger.info("Recherche les matchs d'un tournoi.");
			return new DaoMatch(connection).trouverMatchsTournoi(codeTournoi);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param match
	 * @return
	 * @throws Exception
	 */
	public static int enregistrerResultat(Connection connection, Match match) throws Exception {
		try {
			logger.info("Enregistrement du résultat d'une rencontre.");
			// mise à jour du résultat du match
			new DaoMatch(connection).enregistrerResultat(match);
			
			// mise à jour des données pour le calcul du classement des équipes disputant ce match
			//new DaoGroupe(connection).mettreAJourDonneesEquipe(match);
			
			return 1; 
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param idMatch
	 * @param scoreLocal
	 * @param scoreVisiteur
	 * @return
	 * @throws Exception
	 */
	public static int enregistrerResultat(Connection connection, int idMatch, int scoreLocal, int scoreVisiteur) throws Exception {
		try {
			logger.info("Enregistrement du résultat d'une rencontre.");
			// mise à jour du résultat du match
			new DaoMatch(connection).enregistrerResultat(idMatch, scoreLocal, scoreVisiteur);
			
			// mise à jour des données pour le calcul du classement des équipes disputant ce match
			//new DaoGroupe(connection).mettreAJourDonneesEquipe(match);
			
			return 1; 
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param idMatch
	 * @param horaire
	 * @return
	 * @throws Exception
	 */
	public static int enregistrerHoraire(Connection connection, int idMatch, String horaire) throws Exception {
		try {
			logger.info("Enregistrement de l'horaire d'une rencontre.");
			// mise à jour du résultat du match
			new DaoMatch(connection).enregistrerHoraire(idMatch, horaire);
			
			return 1; 
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
}

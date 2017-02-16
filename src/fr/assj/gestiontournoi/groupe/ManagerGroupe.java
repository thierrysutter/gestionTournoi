package fr.assj.gestiontournoi.groupe;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.equipe.DaoEquipe;
import fr.assj.gestiontournoi.equipe.Equipe;
import fr.assj.gestiontournoi.match.ManagerMatch;
import fr.assj.gestiontournoi.match.Match;

public class ManagerGroupe {
	/**
	 * Logger spécifique à cette classe.
	 */
	protected static Logger logger = Logger.getLogger(ManagerGroupe.class);
	
	/**
	 * 
	 * @param connexion
	 * @param codeTournoi
	 * @param libelleGroupe
	 * @return
	 * @throws Exception
	 */
	public static int ajouterGroupeTournoi(Connection connexion, int codeTournoi, String libelleGroupe) throws Exception {
		try {
			return new DaoGroupe(connexion).ajouterGroupeTournoi(codeTournoi, libelleGroupe);
		} catch (Exception e) {
			logger.error("Erreur lors de la création d'un groupe en base.");
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connexion
	 * @param codeTournoi
	 * @return
	 * @throws Exception
	 */
	public static int supprimerGroupesTournoi(Connection connexion, int codeTournoi) throws Exception {
		try {
			return new DaoGroupe(connexion).supprimerGroupesTournoi(codeTournoi);
		} catch (Exception e) {
			logger.error("Erreur lors de la création d'un groupe en base.");
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connexion
	 * @param codeTournoi
	 * @return
	 * @throws Exception
	 */
	public static Vector<Groupe> trouverGroupesTournoi(Connection connexion, int codeTournoi) throws Exception {
		try {
			return new DaoGroupe(connexion).trouverGroupesTournoi(codeTournoi);
		} catch (Exception e) {
			logger.error("Erreur lors de la recherche des groupes du tournoi.");
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
	public static int ajouterEquipeGroupeTournoi(Connection connection, int codeTournoi, Groupe groupe) throws Exception {
		try {
			logger.info("Affectation des équipes dans les groupes.");
			return new DaoGroupe(connection).ajouterEquipeGroupeTournoi(codeTournoi, groupe);
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connexion
	 * @param codeTournoi
	 * @return
	 * @throws Exception
	 */
	public static Vector<Groupe> trouverGroupesEquipesTournoi(Connection connexion, int codeTournoi) throws Exception {
		try {
			Vector<Groupe> resultat = new DaoGroupe(connexion).trouverGroupesTournoi(codeTournoi);
			
			if (resultat!= null && !resultat.isEmpty()) {
				// pour chaque groupe, on cherche la liste des équipes qui le compose
				DaoEquipe daoEquipe = new DaoEquipe(connexion);
				for (Groupe groupe : resultat) {
					Hashtable<Integer, Equipe> equipes = daoEquipe.trouverEquipesGroupesTournoi(codeTournoi, groupe.getLibelle());
					
					Vector<Match> matchsGroupe = ManagerMatch.trouverMatchsGroupe(connexion, codeTournoi, groupe.getLibelle());
					
					if (equipes != null && !equipes.isEmpty()) {
						for (Match match : matchsGroupe) {
							groupe.ajouterMatch(match);
							equipes.get(match.getEquipeLoc()).ajouterMatch(match);
							equipes.get(match.getEquipeVis()).ajouterMatch(match);
						}
						
						Enumeration<Equipe> enumEquipes = equipes.elements();
						while(enumEquipes.hasMoreElements()) {
							Equipe equipe = enumEquipes.nextElement();
							equipe.setGroupePhase1(groupe);
							groupe.ajouterEquipe(equipe);
						}
					}
				}
			}
			
			return resultat;
		} catch (Exception e) {
			logger.error("Erreur lors de la recherche des groupes du tournoi.");
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
	public static int supprimerEquipesGroupesTournoi(Connection connexion, int codeTournoi) throws Exception {
		try {
			return new DaoGroupe(connexion).supprimerEquipesGroupesTournoi(codeTournoi);
		} catch (Exception e) {
			logger.error("Erreur lors de la création d'un groupe en base.");
			logger.error(e);
			return -1;
		}
	}
	
	public static int initialiserDonneesEquipes(Connection connexion, int codeTournoi) throws Exception {
		try {
			return new DaoGroupe(connexion).initialiserDonneesEquipes(codeTournoi);
		} catch (Exception e) {
			logger.error("Erreur lors de la création d'un groupe en base.");
			logger.error(e);
			return -1;
		}
	}
}

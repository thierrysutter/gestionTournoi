package fr.assj.gestiontournoi.tournoi;

import java.sql.Connection;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.equipe.DaoEquipe;

public class ManagerTournoi {
	/**
	 * Logger spécifique à cette classe.
	 */
	protected static Logger logger = Logger.getLogger(ManagerTournoi.class);
	
	/**
	 * Recherche la totalité des tournois
	 * @param connexion
	 * @return
	 * @throws Exception
	 */
	public static Vector<Tournoi> trouverTournois(Connection connexion) throws Exception {
		try {
			
			Vector<Tournoi> tournois = new DaoTournoi(connexion).trouverTournois(-1);
			DaoEquipe daoEquipe = new DaoEquipe(connexion);
			for (Tournoi tournoi : tournois) {
				tournoi.setListeEquipesInscrites(daoEquipe.trouverEquipesTournoi(tournoi.getId()));
			}
			return tournois;
		} catch (Exception e) {
			logger.error("Erreur lors de la recherche des tournois en base.");
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
	public static Tournoi trouverTournoiParId(Connection connexion, int codeTournoi) throws Exception {
		try {
			return new DaoTournoi(connexion).trouverTournoiParId(codeTournoi);
		} catch (Exception e) {
			logger.error("Erreur lors de la recherche des tournois en base.");
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param connexion
	 * @param tournoi
	 * @return
	 * @throws Exception
	 */
	public static int ajouterTournoi(Connection connexion, Tournoi tournoi) throws Exception {
		try {
			return new DaoTournoi(connexion).ajouterTournoi(tournoi);
		} catch (Exception e) {
			logger.error("Erreur lors de la création d'un tournoi en base.");
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connexion
	 * @param tournoi
	 * @return
	 * @throws Exception
	 */
	public static int modifierTournoi(Connection connexion, Tournoi tournoi) throws Exception {
		try {
			return new DaoTournoi(connexion).modifierTournoi(tournoi);
		} catch (Exception e) {
			logger.error("Erreur lors de la création d'un tournoi en base.");
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
	public static int supprimerTournoi(Connection connexion, int codeTournoi) throws Exception {
		try {
			return new DaoTournoi(connexion).supprimerTournoi(codeTournoi);
		} catch (Exception e) {
			logger.error("Erreur lors de la suppression d'un tournoi en base.");
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connexion
	 * @param codeTournoi
	 * @param nouveauStatut
	 * @return
	 * @throws Exception
	 */
	public static int changerStatutTournoi(Connection connexion, int codeTournoi, int nouveauStatut) throws Exception {
		try {
			return new DaoTournoi(connexion).changerStatutTournoi(codeTournoi, nouveauStatut);
		} catch (Exception e) {
			logger.error("Erreur lors du changement de statut du tournoi en base.");
			logger.error(e);
			return -1;
		}
	}
}

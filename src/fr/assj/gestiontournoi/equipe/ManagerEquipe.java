package fr.assj.gestiontournoi.equipe;

import java.sql.Connection;
import java.util.Vector;

import org.apache.log4j.Logger;

public class ManagerEquipe {
	/**
	 * Logger spécifique à cette classe.
	 */
	protected static Logger logger = Logger.getLogger(ManagerEquipe.class);
	
	/**
	 * 
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public static Vector<Equipe> trouverEquipes(Connection connection, String club, String equipe) throws Exception {
		try {
			logger.info("recherche les équipes enregstrées.");
			return new DaoEquipe(connection).trouverEquipes(club, equipe);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public static Vector<Equipe> trouverEquipes(Connection connection) throws Exception {
		try {
			logger.info("recherche les équipes enregstrées.");
			return new DaoEquipe(connection).trouverEquipes();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param codeTournoi
	 * @return
	 * @throws Exception
	 */
	public static Vector<Equipe> trouverEquipesDispo(Connection connection, int codeTournoi, int categorie) throws Exception {
		try {
			logger.info("recherche les équipes non encore inscrites au tournoi.");
			return new DaoEquipe(connection).trouverEquipesDispo(codeTournoi, categorie);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param codeTournoi
	 * @return
	 * @throws Exception
	 */
	public static Vector<Equipe> trouverEquipesTournoi(Connection connection, int codeTournoi) throws Exception {
		try {
			logger.info("recherche les équipes inscrites à un tournoi donné.");
			return new DaoEquipe(connection).trouverEquipesTournoi(codeTournoi);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param codeTournoi
	 * @return
	 * @throws Exception
	 */
	public static int supprimerEquipesTournoi(Connection connection, int codeTournoi) throws Exception {
		try {
			logger.info("suppression des équipes inscrites à un tournoi donné.");
			return new DaoEquipe(connection).supprimerEquipesTournoi(codeTournoi);
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param nomEquipe
	 * @return
	 * @throws Exception
	 */
	public static int ajouterEquipe(Connection connection, String nomEquipe) throws Exception {
		try {
			logger.info("ajout d'équipe dans le système.");
			return new DaoEquipe(connection).ajouterEquipe(nomEquipe);
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param nomEquipe
	 * @param club
	 * @param categorie
	 * @return
	 * @throws Exception
	 */
	public static int ajouterEquipe(Connection connection, String nomEquipe, int club, int categorie) throws Exception {
		try {
			logger.info("ajout d'équipe dans le système.");
			return new DaoEquipe(connection).ajouterEquipe(nomEquipe, club, categorie);
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
	
	public static int inscrireEquipeTournoi(Connection connection, int idEquipe, int codeTournoi) throws Exception {
		try {
			logger.info("inscription d'une équipe dans le tournoi.");
			return new DaoEquipe(connection).inscrireEquipeTournoi(idEquipe, codeTournoi);
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
}

package fr.assj.gestiontournoi.club;

import java.sql.Connection;
import java.util.Vector;

import org.apache.log4j.Logger;

public class ManagerClub {
	/**
	 * Logger spécifique à cette classe.
	 */
	protected static Logger logger = Logger.getLogger(ManagerClub.class);
	
	/**
	 * 
	 * @param connection
	 * @return
	 * @throws Exception
	 */
	public static Vector<Club> trouverClubs(Connection connection, String club) throws Exception {
		try {
			logger.info("recherche les clubs enregstrés.");
			return new DaoClub(connection).trouverClubs(club);
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
	public static Club trouverClubParId(Connection connection, int idClub) throws Exception {
		try {
			logger.info("recherche les clubs enregstrés.");
			return new DaoClub(connection).trouverClubParId(idClub);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param codeClub
	 * @return
	 * @throws Exception
	 */
	public static int supprimerClub(Connection connection, int codeClub) throws Exception {
		try {
			logger.info("suppression d'un club.");
			return new DaoClub(connection).supprimerClub(codeClub);
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
	
	/**
	 * 
	 * @param connection
	 * @param nomClub
	 * @param ligue
	 * @param district
	 * @param numAffiliation
	 * @param adresse
	 * @param codePostal
	 * @param ville
	 * @param pays
	 * @param tel
	 * @param fax
	 * @param email
	 * @param siteWeb
	 * @return
	 * @throws Exception
	 */
	public static int ajouterClub(Connection connection, String nomClub, String ligue, String district, String numAffiliation, String adresse, String codePostal, String ville, String pays, String tel, String fax, String email, String siteWeb) throws Exception {
		try {
			logger.info("ajout d'un club dans le système.");
			return new DaoClub(connection).ajouterClub(nomClub, ligue, district, numAffiliation, adresse, codePostal, ville, pays, tel, fax, email, siteWeb);
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
	}
}

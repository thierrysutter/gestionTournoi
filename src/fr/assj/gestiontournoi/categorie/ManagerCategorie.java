package fr.assj.gestiontournoi.categorie;

import java.sql.Connection;
import java.util.Vector;

import org.apache.log4j.Logger;

public class ManagerCategorie {
	/**
	 * Logger spécifique à cette classe.
	 */
	protected static Logger logger = Logger.getLogger(ManagerCategorie.class);
	
	public static Vector<Categorie> trouverCategories(Connection connection) throws Exception {
		try {
			logger.info("recherche les tournois enregstrés.");
			return new DaoCategorie(connection).trouverCategories();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
}

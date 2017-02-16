package fr.assj.gestiontournoi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.categorie.Categorie;
import fr.assj.gestiontournoi.categorie.DaoCategorie;
import fr.assj.gestiontournoi.categorie.ListeCategories;
import fr.assj.gestiontournoi.equipe.DaoEquipe;
import fr.assj.gestiontournoi.equipe.Equipe;
import fr.assj.gestiontournoi.equipe.ListeEquipes;

public class ManagerBase {
	/**
	 * Logger spécifique à cette classe.
	 */
	protected static Logger logger = Logger.getLogger(ManagerBase.class);
	
	public static void construireListeReference(final Connection connexion) throws SQLException {
		if (logger.isDebugEnabled()) {
			logger.info("Chargement des données de session.");
		}
		
		ManagerBase.reinitialiser();
		ManagerBase.construireListeCategories(connexion);
		//ManagerBase.construireListeEquipes(connexion);
	}
	
	public static void reinitialiser() {
		ListeCategories.getInstance().reinitialiser();
		ListeEquipes.getInstance().reinitialiser();
	}
	
	public static void construireListeCategories(final Connection connexion) throws SQLException {
		try {
			// recherche des strate puis ajout dans la liste
			final Vector<Categorie> listeCategories = new DaoCategorie(connexion).trouverCategories();
			final Iterator<Categorie> itrCategorie = listeCategories.iterator();
			while (itrCategorie.hasNext()) {
				final Categorie categorie = itrCategorie.next();
				ListeCategories.getInstance().ajouterCategorie(categorie);
			}
		} catch (final SQLException e) {
			logger.error("Erreur lors de la recherche des catégories", e);
			throw e;
		}
	}
	
	public static void construireListeEquipes(final Connection connexion) throws SQLException {
		try {
			// recherche des strate puis ajout dans la liste
			final Vector<Equipe> listeEquipes = new DaoEquipe(connexion).trouverEquipes();
			final Iterator<Equipe> itrEquipe = listeEquipes.iterator();
			while (itrEquipe.hasNext()) {
				final Equipe equipe = itrEquipe.next();
				ListeEquipes.getInstance().ajouterEquipe(equipe);
			}
		} catch (final SQLException e) {
			logger.error("Erreur lors de la recherche des équipes", e);
			throw e;
		}
	}
}

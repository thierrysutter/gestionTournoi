package fr.assj.gestiontournoi.categorie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.commun.DBInterface;
import fr.assj.gestiontournoi.commun.MySQL;

public class DaoCategorie {
	protected Connection connection;
	protected Logger logger;
    protected DBInterface dbi;
		
	public DaoCategorie(Connection connection) {
		this.connection = connection;
		this.dbi = new MySQL();
		this.logger = Logger.getLogger(DaoCategorie.class);
	}
	
	public Vector<Categorie> trouverCategories() throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT categorie.ID, categorie.LIBELLE ");
		sql.append("FROM CATEGORIE categorie");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche la liste des catégories en base");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Vector<Categorie> resultat = new Vector<Categorie>();
			
			while (rs.next()) {
				Categorie categorie = new Categorie();
				categorie.setId(dbi.fromDB(rs.getInt("ID")));
				categorie.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
				
				resultat.add(categorie);
			}
			
			return resultat;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		}
	}
}

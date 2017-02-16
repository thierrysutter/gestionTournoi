package fr.assj.gestiontournoi.utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.commun.DBInterface;
import fr.assj.gestiontournoi.commun.MySQL;

/**
 * 
 * @author tsutter
 *
 */
public class DaoUtilisateur {
	protected Connection connection;
	protected Logger logger;
    protected DBInterface dbi;

    /**
     * Constructeur
     * 
     * @param connection
     */
	public DaoUtilisateur(Connection connection) {
		this.connection = connection;
		this.dbi = new MySQL();
		this.logger = Logger.getLogger(DaoUtilisateur.class);
	}
	
	public Utilisateur trouverCompteUtilisateurParLogin(String login) throws SQLException {
		
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT ID ");
		sql.append("FROM USER ");
		sql.append("WHERE LOGIN = " + dbi.toDB(login));
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche du compte de l'utilisateur.");
			logger.debug(sql);
		}
		
		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Utilisateur user = null;
			
			while (rs.next()) {
				user= new Utilisateur();
				user.setId(dbi.fromDB(rs.getInt("ID")));
				user.setLogin(dbi.fromDB(rs.getString("LOGIN")));
			}
			
			return user;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		}
	}
	
	public int tracerEchecConnexion(int idUtilisateur) throws SQLException {
		StringBuffer sql = new StringBuffer(200);

		sql.append("UPDATE USER ");
		sql.append("  set NB_ECHEC = NB_ECHEC + 1 ");
		sql.append("where ID = " + dbi.toDB(idUtilisateur));
		
		if (logger.isDebugEnabled()) {
			logger.debug(sql.toString());
		}
		Statement st = null;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			return st.executeUpdate(sql.toString());
		} finally {
			if (st != null) {
				st.close();
			}
		}
	}
	
	public int tracerConnexion(int idUtilisateur) throws SQLException {
		StringBuffer sql = new StringBuffer(200);

		sql.append("UPDATE USER ");
		sql.append("  set DATE_DERNIERE_CONNEXION = curdate(), NB_ECHEC = 0 ");
		sql.append("where ID = " + dbi.toDB(idUtilisateur));
		
		if (logger.isDebugEnabled()) {
			logger.debug(sql.toString());
		}
		Statement st = null;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			return st.executeUpdate(sql.toString());
		} finally {
			if (st != null) {
				st.close();
			}
		}
	}
}

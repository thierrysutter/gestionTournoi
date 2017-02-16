package fr.assj.gestiontournoi.groupe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.commun.DBInterface;
import fr.assj.gestiontournoi.commun.MySQL;
import fr.assj.gestiontournoi.equipe.Equipe;
import fr.assj.gestiontournoi.match.Match;

public class DaoGroupe {
	protected Connection connection;
	protected Logger logger;
    protected DBInterface dbi;
    
    /**
     * Constructeur
     * 
     * @param connection
     */
	public DaoGroupe(Connection connection) {
		this.connection = connection;
		this.dbi = new MySQL();
		this.logger = Logger.getLogger(DaoGroupe.class);
	}
	
	/**
	 * 
	 * @param codeTournoi
	 * @param libelleGroupe
	 * @return
	 * @throws SQLException
	 */
	public int ajouterGroupeTournoi(int codeTournoi, String libelleGroupe) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("INSERT INTO GROUPE_TOURNOI ");
		sql.append("(TOURNOI, LIBELLE, DATE_CREATION, USER_MAJ, DERNIERE_MAJ) ");
		sql.append("VALUES (");
		sql.append(""+dbi.toDB(codeTournoi)+",");
		sql.append(""+dbi.toDB(libelleGroupe)+",");
		sql.append("curdate(),");
		sql.append("'ADMIN',");
		sql.append("now()");
		sql.append(")");
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Insertion du groupe.");
			logger.debug(sql);
		}
		
		Statement st = null;
		ResultSet rs = null;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			return st.executeUpdate(sql.toString());
		}finally {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			
		}
	}
	
	/**
	 * 
	 * @param codeTournoi
	 * @return
	 * @throws SQLException
	 */
	public int supprimerGroupesTournoi(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("DELETE FROM GROUPE_TOURNOI ");
		sql.append("WHERE TOURNOI = " + dbi.toDB(codeTournoi));
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Suppression des groupes du tournoi.");
			logger.debug(sql);
		}
		
		Statement st = null;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			return st.executeUpdate(sql.toString());
		}finally {
			if (st != null) {
				st.close();
			}
			
		}
	}
	
	/**
	 * 
	 * @param codeTournoi
	 * @return
	 * @throws SQLException
	 */
	public Vector<Groupe> trouverGroupesTournoi(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT groupeTournoi.TOURNOI, groupeTournoi.LIBELLE ");
		sql.append("FROM GROUPE_TOURNOI groupeTournoi ");
		sql.append("WHERE groupeTournoi.TOURNOI = " + dbi.toDB(codeTournoi) + " ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche la liste des groupes du tournoi.");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Vector<Groupe> resultat = new Vector<Groupe>();
			
			while (rs.next()) {
				Groupe groupe = new Groupe();
				groupe.setCodeTournoi(dbi.fromDB(rs.getInt("TOURNOI")));
				groupe.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
				
				resultat.add(groupe);
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
	
	
	public int ajouterEquipeGroupeTournoi(int codeTournoi, Groupe groupe) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		Statement st = null;
		//PreparedStatement ps = null;
		ResultSet rs = null;
		int retour = -1;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			int i=1;
			for(Equipe equipe : groupe.getListeEquipes()) {
				sql = new StringBuffer(200);
				
				sql.append("INSERT INTO EQUIPE_GROUPE_TOURNOI (TOURNOI, GROUPE, EQUIPE, " +
						"NB_MATCHS_JOUES, NB_POINTS, NB_VICTOIRES, NB_NULS, NB_DEFAITES, NB_BUTS_POUR, NB_BUTS_CONTRE, CLASSEMENT, " +
						"DATE_CREATION, USER_MAJ, DERNIERE_MAJ) VALUES (");
				sql.append(dbi.toDB(codeTournoi)+", ");
				sql.append(dbi.toDB(groupe.getLibelle())+", ");
				sql.append(dbi.toDB(equipe.getId())+", ");
				sql.append("0,0,0,0,0,0,0,"+dbi.toDB(i)+", ");
				sql.append("curdate(), ");
				sql.append("'TEST', ");
				sql.append("now() ");
				sql.append(")");
				
				// logger
				if (logger.isDebugEnabled()) {
					logger.debug("Insertion de l'équipe " + equipe.getLibelle() + " dans le groupe " + groupe.getLibelle());
					logger.debug(sql.toString());
				}
				
				retour = st.executeUpdate(sql.toString());
				i++;
			}
		
			return retour;
		
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		}
	}
	
	/**
	 * 
	 * @param codeTournoi
	 * @return
	 * @throws SQLException
	 */
	public int supprimerEquipesGroupesTournoi(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("DELETE FROM EQUIPE_GROUPE_TOURNOI ");
		sql.append("WHERE TOURNOI = " + dbi.toDB(codeTournoi));
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Suppression de la composition des groupes du tournoi.");
			logger.debug(sql);
		}
		
		Statement st = null;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			return st.executeUpdate(sql.toString());
		}finally {
			if (st != null) {
				st.close();
			}
			
		}
	}
	
	/**
	 * 
	 * @param codeTournoi
	 * @return
	 * @throws SQLException
	 */
	public int initialiserDonneesEquipes(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("UPDATE EQUIPE_GROUPE_TOURNOI ");
		sql.append("SET NB_MATCHS_JOUES = 0, ");
		sql.append("NB_POINTS = 0, ");
		sql.append("NB_VICTOIRES = 0, ");
		sql.append("NB_NULS = 0, ");
		sql.append("NB_DEFAITES = 0, ");
		sql.append("NB_BUTS_POUR = 0, ");
		sql.append("NB_BUTS_CONTRE = 0, ");
		sql.append("CLASSEMENT = 0, ");
		sql.append("USER_MAJ = 'tsutter', ");
		sql.append("DERNIERE_MAJ = now()");
		sql.append("WHERE TOURNOI = " + dbi.toDB(codeTournoi));
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Initialisation des données des équipes pour le tournoi.");
			logger.debug(sql);
		}
		
		Statement st = null;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			return st.executeUpdate(sql.toString());
		}finally {
			if (st != null) {
				st.close();
			}
			
		}
	}
	
	public int mettreAJourDonneesEquipe(Match match) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("UPDATE EQUIPE_GROUPE_TOURNOI ");
		sql.append("SET NB_MATCHS_JOUES = NB_MATCHS_JOUES+1, ");
		sql.append("NB_POINTS = NB_POINTS + " + (match.getScoreLocal()>match.getScoreVisiteur() ? 3 : match.getScoreLocal()<match.getScoreVisiteur() ? 0 : 1) + ", ");
		sql.append("NB_VICTOIRES = NB_VICTOIRES + " + (match.getScoreLocal()>match.getScoreVisiteur() ? 1 : 0) + ", ");
		sql.append("NB_NULS = NB_NULS + " + (match.getScoreLocal()==match.getScoreVisiteur() ? 1 : 0) + ", ");
		sql.append("NB_DEFAITES = NB_DEFAITES + " + (match.getScoreLocal()<match.getScoreVisiteur() ? 1 : 0) + ", ");
		sql.append("NB_BUTS_POUR = NB_BUTS_POUR + " + match.getScoreLocal() + ", ");
		sql.append("NB_BUTS_CONTRE = NB_BUTS_CONTRE + " + match.getScoreVisiteur() + ", ");
		sql.append("USER_MAJ = 'tsutter', ");
		sql.append("DERNIERE_MAJ = now()");
		sql.append("WHERE TOURNOI = " + dbi.toDB(match.getCodeTournoi())+ " ");
		sql.append("AND GROUPE = " + dbi.toDB(match.getGroupe()) + " ");
		sql.append("AND EQUIPE = " + dbi.toDB(match.getEquipeLoc()) + " ");
		
		StringBuffer sql2 = new StringBuffer(200);
		sql2.append("UPDATE EQUIPE_GROUPE_TOURNOI ");
		sql2.append("SET NB_MATCHS_JOUES = NB_MATCHS_JOUES+1, ");
		sql2.append("NB_POINTS = NB_POINTS + " + (match.getScoreLocal()<match.getScoreVisiteur() ? 3 : match.getScoreLocal()>match.getScoreVisiteur() ? 0 : 1) + ", ");
		sql2.append("NB_VICTOIRES = NB_VICTOIRES + " + (match.getScoreLocal()<match.getScoreVisiteur() ? 1 : 0) + ", ");
		sql2.append("NB_NULS = NB_NULS + " + (match.getScoreLocal()==match.getScoreVisiteur() ? 1 : 0) + ", ");
		sql2.append("NB_DEFAITES = NB_DEFAITES + " + (match.getScoreLocal()>match.getScoreVisiteur() ? 1 : 0) + ", ");
		sql2.append("NB_BUTS_POUR = NB_BUTS_POUR + " + match.getScoreVisiteur() + ", ");
		sql2.append("NB_BUTS_CONTRE = NB_BUTS_CONTRE + " + match.getScoreLocal() + ", ");
		sql2.append("USER_MAJ = 'tsutter', ");
		sql2.append("DERNIERE_MAJ = now()");
		sql2.append("WHERE TOURNOI = " + dbi.toDB(match.getCodeTournoi())+ " ");
		sql2.append("AND GROUPE = " + dbi.toDB(match.getGroupe()) + " ");
		sql2.append("AND EQUIPE = " + dbi.toDB(match.getEquipeVis()) + " ");
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Mise à jour des données des équipes pour le tournoi.");
			logger.debug(sql);
			logger.debug(sql2);
		}
		
		Statement st = null;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			
			st.executeUpdate(sql.toString());
			
			st.executeUpdate(sql2.toString());
			
			return 1;
		}finally {
			if (st != null) {
				st.close();
			}
			
		}
	}
}

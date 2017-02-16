package fr.assj.gestiontournoi.equipe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.categorie.Categorie;
import fr.assj.gestiontournoi.commun.DBInterface;
import fr.assj.gestiontournoi.commun.MySQL;

public class DaoEquipe {
	protected Connection connection;
	protected Logger logger;
    protected DBInterface dbi;

    /**
     * 
     * @param connection
     */
	public DaoEquipe(Connection connection) {
		this.connection = connection;
		this.dbi = new MySQL();
		this.logger = Logger.getLogger(DaoEquipe.class);
	}
	
	/**
	 * Recherche toutes les équipes enregistrées dans le système
	 * @return un vecteur d'Equipe
	 * @throws SQLException
	 */
	public Vector<Equipe> trouverEquipes(String libelleClub, String libelleEquipe) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		
		
		sql.append("SELECT equipe.ID, equipe.LIBELLE, club.ID as CLUB, club.NOM as NOM_CLUB, categorie.ID as CATEGORIE, categorie.LIBELLE as LIBELLE_CATEGORIE, ");
		sql.append("club.PAYS, club.LIGUE, club.DISTRICT ");
		sql.append("FROM EQUIPE equipe ");
		sql.append("INNER JOIN CLUB club ON (equipe.club=club.id) ");
		sql.append("INNER JOIN CATEGORIE categorie ON (equipe.categorie=categorie.id) ");
		sql.append("WHERE equipe.LIBELLE like "+dbi.toDB("%"+libelleEquipe.toUpperCase()+"%") + " ");
		sql.append("AND club.NOM like "+dbi.toDB("%"+libelleClub.toUpperCase()+"%") + " ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche la liste des équipes en base");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Vector<Equipe> resultat = new Vector<Equipe>();
			
			while (rs.next()) {
				Equipe equipe = new Equipe();
				equipe.setId(dbi.fromDB(rs.getInt("ID")));
				equipe.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
				
				equipe.setClub(dbi.fromDB(rs.getInt("CLUB")));
				equipe.setLibelleClub(dbi.fromDB(rs.getString("NOM_CLUB")));
				
				equipe.setPays(dbi.fromDB(rs.getString("PAYS")));
				equipe.setLigue(dbi.fromDB(rs.getString("LIGUE")));
				equipe.setDistrict(dbi.fromDB(rs.getString("DISTRICT")));
				
				Categorie categorie = new Categorie();
				categorie.setId(dbi.fromDB(rs.getInt("CATEGORIE")));
				categorie.setLibelle(dbi.fromDB(rs.getString("LIBELLE_CATEGORIE")));
				equipe.setCategorie(categorie);
				
				resultat.add(equipe);
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
	
	/**
	 * Recherche toutes les équipes enregistrées dans le système
	 * @return un vecteur d'Equipe
	 * @throws SQLException
	 */
	public Vector<Equipe> trouverEquipes() throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT equipe.ID, equipe.LIBELLE, club.ID as CLUB, club.NOM as NOM_CLUB, categorie.ID as CATEGORIE, categorie.LIBELLE as LIBELLE_CATEGORIE ");
		sql.append("FROM EQUIPE equipe ");
		sql.append("LEFT OUTER JOIN CLUB club ON (equipe.club=club.id) ");
		sql.append("LEFT OUTER JOIN CATEGORIE categorie ON (equipe.categorie=categorie.id) ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche la liste des équipes en base");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Vector<Equipe> resultat = new Vector<Equipe>();
			
			while (rs.next()) {
				Equipe equipe = new Equipe();
				equipe.setId(dbi.fromDB(rs.getInt("ID")));
				equipe.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
				
				equipe.setClub(dbi.fromDB(rs.getInt("CLUB")));
				equipe.setLibelleClub(dbi.fromDB(rs.getString("NOM_CLUB")));
				
				Categorie categorie = new Categorie();
				categorie.setId(dbi.fromDB(rs.getInt("CATEGORIE")));
				categorie.setLibelle(dbi.fromDB(rs.getString("LIBELLE_CATEGORIE")));
				equipe.setCategorie(categorie);
				
				resultat.add(equipe);
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
	
	/**
	 * Recherche toutes les équipes enregistrées dans le système et non encore inscrites au tounoi
	 * @param codeTournoi
	 * @return
	 * @throws SQLException
	 */
	public Vector<Equipe> trouverEquipesDispo(int codeTournoi, int categorie) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT equipe.ID, equipe.LIBELLE ");
		sql.append("FROM EQUIPE equipe ");
		sql.append("WHERE not exists (select 1 from EQUIPE_TOURNOI et where et.EQUIPE=equipe.ID and et.TOURNOI="+dbi.toDB(codeTournoi)+") ");
		sql.append("AND categorie = "+dbi.toDB(categorie)+" ");
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche la liste des équipes engageables au tournoi.");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Vector<Equipe> resultat = new Vector<Equipe>();
			
			while (rs.next()) {
				Equipe equipe = new Equipe();
				equipe.setId(dbi.fromDB(rs.getInt("ID")));
				equipe.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
				
				resultat.add(equipe);
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
	
	/**
	 * Recherche toutes les équipes inscrites à un tournoi
	 * @param codeTournoi
	 * @return un vecteur d'Equipe
	 * @throws SQLException
	 */
	public Vector<Equipe> trouverEquipesTournoi(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT equipe.ID, equipe.LIBELLE ");
		sql.append("FROM EQUIPE_TOURNOI equipeTournoi, EQUIPE equipe ");
		/*sql.append("LEFT OUTER JOIN EQUIPE_GROUPE_TOURNOI equipeGroupeTournoi ON equipeGroupeTournoi.equipe=equipeTournoi.equipe and equipeGroupeTournoi.tournoi=equipeTournoi.tournoi ");
		sql.append("INNER JOIN GROUPE_TOURNOI equipeGroupeTournoi ON equipeGroupeTournoi.equipe=equipeTournoi.equipe and equipeGroupeTournoi.tournoi=equipeTournoi.tournoi ");*/
		sql.append("WHERE equipeTournoi.TOURNOI = " + dbi.toDB(codeTournoi) + " ");
		sql.append("AND equipeTournoi.EQUIPE = equipe.ID ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche la liste des équipes inscrites à un tournoi.");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Vector<Equipe> resultat = new Vector<Equipe>();
			
			while (rs.next()) {
				Equipe equipe = new Equipe();
				equipe.setId(dbi.fromDB(rs.getInt("ID")));
				equipe.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
				
				resultat.add(equipe);
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
	
	/**
	 * 
	 * @param codeTournoi
	 * @return
	 * @throws SQLException
	 */
	public int supprimerEquipesTournoi(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("DELETE FROM EQUIPE_TOURNOI ");
		sql.append("WHERE TOURNOI = " + dbi.toDB(codeTournoi) + " ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Suppression des équipes inscrites à un tournoi.");
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
	
	/**
	 * 
	 * @param nomEquipe
	 * @return
	 * @throws SQLException
	 */
	public int ajouterEquipe(String nomEquipe) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("INSERT INTO EQUIPE (LIBELLE, PHOTO, CLUB, DATE_CREATION, USER_MAJ, DERNIERE_MAJ) VALUES ( ");
		sql.append("" + dbi.toDB(nomEquipe) + ", ");
		sql.append("-1, ");
		sql.append("-1, ");
		sql.append("curdate(), ");
		sql.append("'TEST', ");
		sql.append("now()) ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Ajout d'une équipe dans le système.");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		int idEquipe = -1;
		
		try {
			//exécution de la requête
			//exécution de la requête
			st = this.connection.createStatement();
			st.executeUpdate(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			rs = st.getGeneratedKeys();
			if (rs.next()) {
				idEquipe = rs.getInt(1);
		    }
			return idEquipe;
		} finally {
			if (st != null) {
				st.close();
			}
		}
	}
	
	/**
	 * 
	 * @param nomEquipe
	 * @param club
	 * @param categorie
	 * @return
	 * @throws SQLException
	 */
	public int ajouterEquipe(String nomEquipe, int club, int categorie) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("INSERT INTO EQUIPE (LIBELLE, CLUB, CATEGORIE, DATE_CREATION, USER_MAJ, DERNIERE_MAJ) VALUES ( ");
		sql.append("" + dbi.toDB(nomEquipe) + ", ");
		sql.append("" + dbi.toDB(club) + ", ");
		sql.append("" + dbi.toDB(categorie) + ", ");
		sql.append("curdate(), ");
		sql.append("'TEST', ");
		sql.append("now()) ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Ajout d'une équipe dans le système.");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		int idEquipe = -1;
		
		try {
			//exécution de la requête
			//exécution de la requête
			st = this.connection.createStatement();
			st.executeUpdate(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			rs = st.getGeneratedKeys();
			if (rs.next()) {
				idEquipe = rs.getInt(1);
		    }
			return idEquipe;
		} finally {
			if (st != null) {
				st.close();
			}
		}
	}
	
	/**
	 * 
	 * @param idEquipe
	 * @param codeTournoi
	 * @return
	 * @throws SQLException
	 */
	public int inscrireEquipeTournoi(int idEquipe, int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("INSERT INTO EQUIPE_TOURNOI (TOURNOI, EQUIPE, DATE_CREATION, USER_MAJ, DERNIERE_MAJ) VALUES ( ");
		sql.append("" + dbi.toDB(codeTournoi) + ", ");
		sql.append("" + dbi.toDB(idEquipe) + ", ");
		sql.append("curdate(), ");
		sql.append("'TEST', ");
		sql.append("now()) ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Inscription d'une équipe dans le tournoi.");
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
	
//	/**
//	 * 
//	 * @param codeTournoi
//	 * @param groupe
//	 * @return
//	 * @throws SQLException
//	 */
//	public Vector<Equipe> trouverEquipesGroupesTournoi(int codeTournoi, String groupe) throws SQLException {
//		StringBuffer sql = new StringBuffer(200);
//		
//		sql.append("SELECT equipe.ID, equipe.LIBELLE ");
//		sql.append("FROM EQUIPE_GROUPE_TOURNOI equipeGroupeTournoi, GROUPE_TOURNOI groupeTournoi, EQUIPE equipe ");
//		sql.append("WHERE equipeGroupeTournoi.TOURNOI = " + dbi.toDB(codeTournoi) + " ");
//		
//		sql.append("AND equipeGroupeTournoi.TOURNOI = groupeTournoi.TOURNOI ");
//		sql.append("AND equipeGroupeTournoi.GROUPE = groupeTournoi.LIBELLE ");
//		sql.append("AND groupeTournoi.LIBELLE ="+dbi.toDB(groupe));
//		sql.append("AND equipeGroupeTournoi.EQUIPE = equipe.ID ");
//		
//		// logger
//		if (logger.isDebugEnabled()) {
//			logger.debug("Recherche la liste des équipes d'un groupe.");
//			logger.debug(sql.toString());
//		}
//
//		Statement st = null;
//		ResultSet rs = null;
//		
//		try {
//			//exécution de la requête
//			st = this.connection.createStatement();
//			rs = st.executeQuery(sql.toString());
//			
//			Vector<Equipe> resultat = new Vector<Equipe>();
//			
//			while (rs.next()) {
//				Equipe equipe = new Equipe();
//				equipe.setId(dbi.fromDB(rs.getInt("ID")));
//				equipe.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
//				
//				resultat.add(equipe);
//			}
//			
//			return resultat;
//		} finally {
//			if (rs != null) {
//				rs.close();
//			}
//			if (st != null) {
//				st.close();
//			}
//		}
//	}
	
	
//	/**
//	 * 
//	 * @param codeTournoi
//	 * @param groupe
//	 * @return
//	 * @throws SQLException
//	 */
//	public Hashtable<Integer, Equipe> trouverEquipesGroupesTournoi(int codeTournoi, String groupe) throws SQLException {
//		StringBuffer sql = new StringBuffer(200);
//		
//		sql.append("SELECT equipe.ID, equipe.LIBELLE, ");
//		sql.append("coalesce(equipeGroupeTournoi.NB_POINTS, 0) as NB_POINTS, ");
//		sql.append("coalesce(equipeGroupeTournoi.NB_MATCHS_JOUES, 0) as NB_MATCHS_JOUES, ");
//		sql.append("coalesce(equipeGroupeTournoi.NB_VICTOIRES, 0) as NB_VICTOIRES, ");
//		sql.append("coalesce(equipeGroupeTournoi.NB_NULS, 0) as NB_NULS, ");
//		sql.append("coalesce(equipeGroupeTournoi.NB_DEFAITES, 0) as NB_DEFAITES, ");
//		sql.append("coalesce(equipeGroupeTournoi.NB_BUTS_POUR, 0) as NB_BUTS_POUR, ");
//		sql.append("coalesce(equipeGroupeTournoi.NB_BUTS_CONTRE, 0) as NB_BUTS_CONTRE, ");
//		sql.append("coalesce(equipeGroupeTournoi.NB_BUTS_POUR, 0) - coalesce(equipeGroupeTournoi.NB_BUTS_CONTRE, 0) as DIFFERENCE, ");
//		sql.append("coalesce(equipeGroupeTournoi.CLASSEMENT, 0) as CLASSEMENT ");
//		
//		sql.append("FROM EQUIPE_GROUPE_TOURNOI equipeGroupeTournoi, GROUPE_TOURNOI groupeTournoi, EQUIPE equipe ");
//		sql.append("WHERE equipeGroupeTournoi.TOURNOI = " + dbi.toDB(codeTournoi) + " ");
//		
//		sql.append("AND equipeGroupeTournoi.TOURNOI = groupeTournoi.TOURNOI ");
//		sql.append("AND equipeGroupeTournoi.GROUPE = groupeTournoi.LIBELLE ");
//		sql.append("AND groupeTournoi.LIBELLE ="+dbi.toDB(groupe));
//		sql.append("AND equipeGroupeTournoi.EQUIPE = equipe.ID ");
//		
//		sql.append("ORDER BY equipeGroupeTournoi.CLASSEMENT asc, equipeGroupeTournoi.NB_POINTS desc, DIFFERENCE desc, equipeGroupeTournoi.NB_BUTS_POUR desc, equipeGroupeTournoi.NB_BUTS_CONTRE asc, equipe.LIBELLE asc ");
//		
//		// logger
//		if (logger.isDebugEnabled()) {
//			logger.debug("Recherche la liste des équipes d'un groupe.");
//			logger.debug(sql.toString());
//		}
//
//		Statement st = null;
//		ResultSet rs = null;
//		
//		try {
//			//exécution de la requête
//			st = this.connection.createStatement();
//			rs = st.executeQuery(sql.toString());
//			
//			//Vector<Equipe> resultat = new Vector<Equipe>();
//			Hashtable<Integer, Equipe> resultat = new Hashtable<Integer, Equipe>();
//			
//			while (rs.next()) {
//				Equipe equipe = new Equipe();
//				equipe.setId(dbi.fromDB(rs.getInt("ID")));
//				equipe.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
//				
//				equipe.setNbPoints(dbi.fromDB(rs.getInt("NB_POINTS")));
//				equipe.setNbMatchsJoues(dbi.fromDB(rs.getInt("NB_MATCHS_JOUES")));
//				equipe.setNbVictoires(dbi.fromDB(rs.getInt("NB_VICTOIRES")));
//				equipe.setNbNuls(dbi.fromDB(rs.getInt("NB_NULS")));
//				equipe.setNbDefaites(dbi.fromDB(rs.getInt("NB_DEFAITES")));
//				equipe.setNbButsPour(dbi.fromDB(rs.getInt("NB_BUTS_POUR")));
//				equipe.setNbButsContre(dbi.fromDB(rs.getInt("NB_BUTS_CONTRE")));
//				equipe.setClassement(dbi.fromDB(rs.getInt("CLASSEMENT")));
//				
//				resultat.put(equipe.getId(), equipe);
//			}
//			
//			return resultat;
//		} finally {
//			if (rs != null) {
//				rs.close();
//			}
//			if (st != null) {
//				st.close();
//			}
//		}
//	}
	
	/**
	 * 
	 * @param codeTournoi
	 * @param groupe
	 * @return
	 * @throws SQLException
	 */
	public Hashtable<Integer, Equipe>  trouverEquipesGroupesTournoi(int codeTournoi, String groupe) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT e.ID as EQUIPE, e.LIBELLE, ");
		sql.append("sum(coalesce(v.NB_POINTS,0)) as NB_POINTS, sum(coalesce(v.nb_matchs_joues, 0)) as nb_matchs_joues, ");
		sql.append("sum(coalesce(v.nb_victoires,0)) as nb_victoires, sum(coalesce(v.nb_nuls,0)) as nb_nuls, ");
		sql.append("sum(coalesce(v.nb_defaites,0)) as nb_defaites, sum(coalesce(v.NB_BUTS_POUR,0)- coalesce(v.NB_BUTS_CONTRE,0)) as DIFFERENCE, ");
		sql.append("sum(coalesce(v.NB_BUTS_POUR,0)) as NB_BUTS_POUR, sum(coalesce(v.NB_BUTS_CONTRE,0)) as NB_BUTS_CONTRE ");
		sql.append("FROM EQUIPE e ");
		sql.append("INNER JOIN EQUIPE_GROUPE_TOURNOI egt ON (e.ID=egt.EQUIPE) ");
		sql.append("LEFT OUTER JOIN (");
		sql.append("SELECT m.EQUIPE_LOCAL as EQUIPE, m.GROUPE, m.TOURNOI, ");
		sql.append("sum(case when m.STATUT=3 then case when m.SCORE_LOCAL>m.SCORE_VISITEUR then 3 when m.SCORE_LOCAL<m.SCORE_VISITEUR then 0 else 1 end else 0 end) as nb_points, "); 
		sql.append("sum(case when m.STATUT=3 then 1 else 0 end) as nb_matchs_joues, ");
		sql.append("sum(case when m.STATUT=3 and m.SCORE_LOCAL>m.SCORE_VISITEUR then 1 else 0 end) as nb_victoires, ");
		sql.append("sum(case when m.STATUT=3 and m.SCORE_LOCAL=m.SCORE_VISITEUR then 1 else 0 end) as nb_nuls, ");
		sql.append("sum(case when m.STATUT=3 and m.SCORE_LOCAL<m.SCORE_VISITEUR then 1 else 0 end) as nb_defaites, ");
		sql.append("sum(m.SCORE_LOCAL) as nb_buts_pour, sum(m.SCORE_VISITEUR) as nb_buts_contre ");
		sql.append("FROM `MATCH` m ");
		sql.append("GROUP BY m.EQUIPE_LOCAL, m.GROUPE, m.TOURNOI ");
		sql.append("UNION ");
		sql.append("SELECT m.EQUIPE_VISITEUR as EQUIPE, m.GROUPE, m.TOURNOI, ");
		sql.append("sum(case when m.STATUT=3 then case when m.SCORE_LOCAL<m.SCORE_VISITEUR then 3 when m.SCORE_LOCAL>m.SCORE_VISITEUR then 0 else 1 end else 0 end) as nb_points, ");
		sql.append("sum(case when m.STATUT=3 then 1 else 0 end) as nb_matchs_joues, ");
		sql.append("sum(case when m.STATUT=3 and m.SCORE_LOCAL<m.SCORE_VISITEUR then 1 else 0 end) as nb_victoires, ");
		sql.append("sum(case when m.STATUT=3 and m.SCORE_LOCAL=m.SCORE_VISITEUR then 1 else 0 end) as nb_nuls, ");
		sql.append("sum(case when m.STATUT=3 and m.SCORE_LOCAL>m.SCORE_VISITEUR then 1 else 0 end) as nb_defaites, ");		
		sql.append("sum(m.SCORE_VISITEUR) as nb_buts_pour, sum(m.SCORE_LOCAL) as nb_buts_contre ");
		sql.append("FROM `MATCH` m ");
		sql.append("GROUP BY m.EQUIPE_VISITEUR, m.GROUPE, m.TOURNOI ");
		sql.append(") v ON (egt.TOURNOI=v.TOURNOI and egt.GROUPE=v.GROUPE and egt.EQUIPE=v.EQUIPE) ");
		sql.append("WHERE egt.TOURNOI="+dbi.toDB(codeTournoi)+" and egt.groupe="+dbi.toDB(groupe)+" ");
		sql.append("GROUP BY e.ID, e.LIBELLE ");
		sql.append("ORDER BY NB_POINTS DESC, difference DESC, nb_buts_pour desc, nb_buts_contre asc, e.LIBELLE asc ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche le classement d'un groupe.");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Hashtable<Integer, Equipe> resultat = new Hashtable<Integer, Equipe>();
			int i = 1;
			while (rs.next()) {
				Equipe equipe = new Equipe();
				equipe.setId(dbi.fromDB(rs.getInt("EQUIPE")));
				equipe.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
				
				equipe.setNbPoints(dbi.fromDB(rs.getInt("NB_POINTS")));
				equipe.setNbMatchsJoues(dbi.fromDB(rs.getInt("NB_MATCHS_JOUES")));
				equipe.setNbVictoires(dbi.fromDB(rs.getInt("NB_VICTOIRES")));
				equipe.setNbNuls(dbi.fromDB(rs.getInt("NB_NULS")));
				equipe.setNbDefaites(dbi.fromDB(rs.getInt("NB_DEFAITES")));
				equipe.setNbButsPour(dbi.fromDB(rs.getInt("NB_BUTS_POUR")));
				equipe.setNbButsContre(dbi.fromDB(rs.getInt("NB_BUTS_CONTRE")));
				equipe.setClassement(i);
				
				resultat.put(equipe.getId(), equipe);
				
				i++;
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

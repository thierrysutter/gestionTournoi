package fr.assj.gestiontournoi.match;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.commun.DBInterface;
import fr.assj.gestiontournoi.commun.MySQL;
import fr.assj.gestiontournoi.equipe.Equipe;

public class DaoMatch {
	protected Connection connection;
	protected Logger logger;
    protected DBInterface dbi;
    
    /**
     * Constructeur
     * 
     * @param connection
     */
	public DaoMatch(Connection connection) {
		this.connection = connection;
		this.dbi = new MySQL();
		this.logger = Logger.getLogger(DaoMatch.class);
	}
	
	public int ajouterMatchGroupe(int codeTournoi, String groupe, Date jour, int tour, String heure, String lieu, int equipe1, int equipe2) throws SQLException {
		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		StringBuffer sql = new StringBuffer(200);
		sql.append("INSERT INTO `MATCH` (TOURNOI, GROUPE, DATE_MATCH, TOUR, HORAIRE, LIEU, STATUT, EQUIPE_LOCAL, EQUIPE_VISITEUR, SCORE_LOCAL, SCORE_VISITEUR, DATE_CREATION, USER_MAJ, DERNIERE_MAJ) VALUES (");
		sql.append(dbi.toDB(codeTournoi)+", ");
		sql.append(dbi.toDB(groupe)+", ");
		sql.append(dbi.toDB(jour)+", ");
		sql.append(dbi.toDB(tour)+", ");
		sql.append(dbi.toDB(heure)+", ");
		sql.append(dbi.toDB(lieu)+", ");
		sql.append(dbi.toDB(0)+", ");
		sql.append(dbi.toDB(equipe1)+", ");
		sql.append(dbi.toDB(equipe2)+", ");
		sql.append(dbi.toDB(0)+", ");
		sql.append(dbi.toDB(0)+", ");
		sql.append("curdate(), ");
		sql.append("'TEST', ");
		sql.append("now() ");
		sql.append(")");
			
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Création du match ");
			logger.debug(sql.toString());
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
	
	public Vector<Match> trouverMatchsTournoi(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT m.ID, m.TOURNOI, m.GROUPE, m.DATE_MATCH as JOUR, m.TOUR, m.HORAIRE, m.LIEU, m.STATUT, ");
		sql.append("m.EQUIPE_LOCAL, e1.LIBELLE as LIBELLE_EQUIPE_LOCALE, ");
		sql.append("m.EQUIPE_VISITEUR, e2.LIBELLE as LIBELLE_EQUIPE_VISITEUR, ");
		sql.append("m.SCORE_LOCAL, m.SCORE_VISITEUR ");
		sql.append("FROM `MATCH` m ");
		sql.append("INNER JOIN EQUIPE_GROUPE_TOURNOI egt1 ON (m.TOURNOI=egt1.TOURNOI and m.EQUIPE_LOCAL=egt1.EQUIPE) ");
		sql.append("INNER JOIN EQUIPE e1 ON (egt1.EQUIPE=e1.ID) ");
		sql.append("INNER JOIN EQUIPE_GROUPE_TOURNOI egt2 ON (m.TOURNOI=egt2.TOURNOI and m.EQUIPE_VISITEUR=egt2.EQUIPE) ");
		sql.append("INNER JOIN EQUIPE e2 ON (egt2.EQUIPE=e2.ID) ");
		sql.append("WHERE m.TOURNOI=" + dbi.toDB(codeTournoi) + " ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche les matchs du tournoi");
			logger.debug(sql.toString());
		}
		
		Statement st = null;
		//PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Match> listeMatchs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());

			listeMatchs = new Vector<Match>();
			while (rs.next()) {
				Match match = new Match();
				
				match.setId(dbi.fromDB(rs.getInt("ID")));
				match.setCodeTournoi(codeTournoi);
				match.setGroupe(dbi.fromDB(rs.getString("GROUPE")));
				match.setEquipeLoc(dbi.fromDB(rs.getInt("EQUIPE_LOCAL")));
				match.setEquipeVis(dbi.fromDB(rs.getInt("EQUIPE_VISITEUR")));
				match.setScoreLocal(dbi.fromDB(rs.getInt("SCORE_LOCAL")));
				match.setScoreVisiteur(dbi.fromDB(rs.getInt("SCORE_VISITEUR")));
				match.setTour(dbi.fromDB(rs.getInt("TOUR")));
				match.setJour(dbi.fromDB(rs.getDate("JOUR")));
				match.setHoraire(dbi.fromDB(rs.getString("HORAIRE")));
				match.setLieu(dbi.fromDB(rs.getString("LIEU")));
				match.setStatut(dbi.fromDB(rs.getInt("STATUT")));
				
				Equipe equipeLocale = new Equipe();
				equipeLocale.setId(dbi.fromDB(rs.getInt("EQUIPE_LOCAL")));
				equipeLocale.setLibelle(dbi.fromDB(rs.getString("LIBELLE_EQUIPE_LOCALE")));
				match.setEquipeLocale(equipeLocale);
				
				Equipe equipeVisiteur = new Equipe();
				equipeVisiteur.setId(dbi.fromDB(rs.getInt("EQUIPE_VISITEUR")));
				equipeVisiteur.setLibelle(dbi.fromDB(rs.getString("LIBELLE_EQUIPE_VISITEUR")));
				match.setEquipeVisiteur(equipeVisiteur);
				
				listeMatchs.add(match);
			}
				
			return listeMatchs;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		}
	}
	
	public Vector<Match> trouverMatchsGroupe(int codeTournoi, String groupe) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT m.ID, m.TOURNOI, m.GROUPE, m.DATE_MATCH as JOUR, m.TOUR, m.HORAIRE, m.LIEU, m.STATUT, ");
		sql.append("m.EQUIPE_LOCAL, e1.LIBELLE as LIBELLE_EQUIPE_LOCALE, ");
		sql.append("m.EQUIPE_VISITEUR, e2.LIBELLE as LIBELLE_EQUIPE_VISITEUR, ");
		sql.append("m.SCORE_LOCAL, m.SCORE_VISITEUR ");
		sql.append("FROM `MATCH` m ");
		sql.append("INNER JOIN EQUIPE_GROUPE_TOURNOI egt1 ON (m.TOURNOI=egt1.TOURNOI and m.EQUIPE_LOCAL=egt1.EQUIPE) ");
		sql.append("INNER JOIN EQUIPE e1 ON (egt1.EQUIPE=e1.ID) ");
		sql.append("INNER JOIN EQUIPE_GROUPE_TOURNOI egt2 ON (m.TOURNOI=egt2.TOURNOI and m.EQUIPE_VISITEUR=egt2.EQUIPE) ");
		sql.append("INNER JOIN EQUIPE e2 ON (egt2.EQUIPE=e2.ID) ");
		sql.append("WHERE m.TOURNOI=" + dbi.toDB(codeTournoi) + " ");
		sql.append("AND m.GROUPE=" + dbi.toDB(groupe) + " ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche les matchs du groupe");
			logger.debug(sql.toString());
		}
		
		Statement st = null;
		//PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Match> listeMatchs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());

			listeMatchs = new Vector<Match>();
			while (rs.next()) {
				Match match = new Match();
				
				match.setId(dbi.fromDB(rs.getInt("ID")));
				match.setCodeTournoi(codeTournoi);
				match.setGroupe(dbi.fromDB(rs.getString("GROUPE")));
				match.setEquipeLoc(dbi.fromDB(rs.getInt("EQUIPE_LOCAL")));
				match.setEquipeVis(dbi.fromDB(rs.getInt("EQUIPE_VISITEUR")));
				match.setScoreLocal(dbi.fromDB(rs.getInt("SCORE_LOCAL")));
				match.setScoreVisiteur(dbi.fromDB(rs.getInt("SCORE_VISITEUR")));
				match.setTour(dbi.fromDB(rs.getInt("TOUR")));
				match.setJour(dbi.fromDB(rs.getDate("JOUR")));
				match.setHoraire(dbi.fromDB(rs.getString("HORAIRE")));
				match.setLieu(dbi.fromDB(rs.getString("LIEU")));
				match.setStatut(dbi.fromDB(rs.getInt("STATUT")));
				
				Equipe equipeLocale = new Equipe();
				equipeLocale.setId(dbi.fromDB(rs.getInt("EQUIPE_LOCAL")));
				equipeLocale.setLibelle(dbi.fromDB(rs.getString("LIBELLE_EQUIPE_LOCALE")));
				match.setEquipeLocale(equipeLocale);
				
				Equipe equipeVisiteur = new Equipe();
				equipeVisiteur.setId(dbi.fromDB(rs.getInt("EQUIPE_VISITEUR")));
				equipeVisiteur.setLibelle(dbi.fromDB(rs.getString("LIBELLE_EQUIPE_VISITEUR")));
				match.setEquipeVisiteur(equipeVisiteur);
				
				listeMatchs.add(match);
			}
				
			return listeMatchs;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
		}
	}
	
	public Vector<Match> trouverMatchsEquipe(int codeTournoi, String groupe, int equipe) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT m.ID, m.TOURNOI, m.GROUPE, m.DATE_MATCH as JOUR, m.TOUR, m.HORAIRE, m.LIEU, m.STATUT, ");
		sql.append("m.EQUIPE_LOCAL, e1.LIBELLE as LIBELLE_EQUIPE_LOCALE, ");
		sql.append("m.EQUIPE_VISITEUR, e2.LIBELLE as LIBELLE_EQUIPE_VISITEUR, ");
		sql.append("m.SCORE_LOCAL, m.SCORE_VISITEUR ");
		sql.append("FROM `MATCH` m ");
		sql.append("INNER JOIN EQUIPE_GROUPE_TOURNOI egt1 ON (m.TOURNOI=egt1.TOURNOI and m.EQUIPE_LOCAL=egt1.EQUIPE) ");
		sql.append("INNER JOIN EQUIPE e1 ON (egt1.EQUIPE=e1.ID) ");
		sql.append("INNER JOIN EQUIPE_GROUPE_TOURNOI egt2 ON (m.TOURNOI=egt2.TOURNOI and m.EQUIPE_VISITEUR=egt2.EQUIPE) ");
		sql.append("INNER JOIN EQUIPE e2 ON (egt2.EQUIPE=e2.ID) ");
		sql.append("WHERE m.TOURNOI=" + dbi.toDB(codeTournoi) + " ");
		sql.append("AND m.GROUPE=" + dbi.toDB(groupe) + " ");
		sql.append("AND ( e1.ID=" + dbi.toDB(equipe) + " OR e2.ID=" + dbi.toDB(equipe) + " ) ");
		
//		sql.append("SELECT matgrptou.ID, matgrptou.GROUPE, matgrptou.EQUIPE1 as CODE_EQUIPE1, matgrptou.EQUIPE2 as CODE_EQUIPE2, ");
//		sql.append("equipe1.LIBELLE as EQUIPE1, equipe2.LIBELLE as EQUIPE2, ");
//		sql.append("matgrptou.TOUR, matgrptou.JOUR, matgrptou.HEURE, matgrptou.LIEU, ");
//		sql.append("matgrptou.SCORE1, matgrptou.SCORE2 ");
//		sql.append("FROM MATCH_GROUPE_TOURNOI matgrptou ");
//		sql.append(", EQUIPE_GROUPE_TOURNOI equipeGroupe1 ");
//		sql.append(", EQUIPE_TOURNOI equipe1 ");
//		sql.append(", EQUIPE_GROUPE_TOURNOI equipeGroupe2 ");
//		sql.append(", EQUIPE_TOURNOI equipe2 ");
//		sql.append("WHERE equipeGroupe1.EQUIPE=matgrptou.EQUIPE1 AND equipeGroupe1.GROUPE=matgrptou.GROUPE AND equipeGroupe1.TOURNOI=matgrptou.TOURNOI ");
//		sql.append("AND equipeGroupe2.EQUIPE=matgrptou.EQUIPE2 AND equipeGroupe2.GROUPE=matgrptou.GROUPE AND equipeGroupe2.TOURNOI=matgrptou.TOURNOI ");
//		sql.append("AND equipe1.ID=equipeGroupe1.EQUIPE AND equipe1.TOURNOI=equipeGroupe1.TOURNOI ");
//		sql.append("AND equipe2.ID=equipeGroupe2.EQUIPE AND equipe2.TOURNOI=equipeGroupe2.TOURNOI ");
//		sql.append("AND matgrptou.TOURNOI = " + dbi.toDB(codeTournoi));
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche les matchs de l'équipe " + equipe);
			logger.debug(sql.toString());
		}
		
		Statement st = null;
		//PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Match> listeMatchs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());

			listeMatchs = new Vector<Match>();
			while (rs.next()) {
				Match match = new Match();
				
				match.setId(dbi.fromDB(rs.getInt("ID")));
				match.setCodeTournoi(codeTournoi);
				match.setGroupe(dbi.fromDB(rs.getString("GROUPE")));
				match.setEquipeLoc(dbi.fromDB(rs.getInt("EQUIPE_LOCAL")));
				match.setEquipeVis(dbi.fromDB(rs.getInt("EQUIPE_VISITEUR")));
				match.setScoreLocal(dbi.fromDB(rs.getInt("SCORE_LOCAL")));
				match.setScoreVisiteur(dbi.fromDB(rs.getInt("SCORE_VISITEUR")));
				match.setTour(dbi.fromDB(rs.getInt("TOUR")));
				match.setJour(dbi.fromDB(rs.getDate("JOUR")));
				match.setHoraire(dbi.fromDB(rs.getString("HORAIRE")));
				match.setLieu(dbi.fromDB(rs.getString("LIEU")));
				match.setStatut(dbi.fromDB(rs.getInt("STATUT")));
				
				Equipe equipeLocale = new Equipe();
				equipeLocale.setId(dbi.fromDB(rs.getInt("EQUIPE_LOCAL")));
				equipeLocale.setLibelle(dbi.fromDB(rs.getString("LIBELLE_EQUIPE_LOCALE")));
				match.setEquipeLocale(equipeLocale);
				
				Equipe equipeVisiteur = new Equipe();
				equipeVisiteur.setId(dbi.fromDB(rs.getInt("EQUIPE_VISITEUR")));
				equipeVisiteur.setLibelle(dbi.fromDB(rs.getString("LIBELLE_EQUIPE_VISITEUR")));
				match.setEquipeVisiteur(equipeVisiteur);
				
				listeMatchs.add(match);
			}
				
			return listeMatchs;
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
	public int supprimerMatchsTournoi(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("DELETE FROM `match` ");
		sql.append("WHERE TOURNOI = " + dbi.toDB(codeTournoi));
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Suppression des matchs du tournoi.");
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
	
	public int enregistrerResultat(Match match) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("UPDATE `MATCH` ");
		sql.append("SET SCORE_LOCAL = "+dbi.toDB(match.getScoreLocal())+", ");
		sql.append("SCORE_VISITEUR = "+dbi.toDB(match.getScoreVisiteur())+", ");
		sql.append("STATUT = 3, ");
		sql.append("USER_MAJ = 'tsutter', ");
		sql.append("DERNIERE_MAJ = now()");
		sql.append("WHERE ID = " + dbi.toDB(match.getId()));
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Enregistrement du résultat du match " + match.getId() + ".");
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
	 * @param idMatch
	 * @param scoreLocal
	 * @param scoreVisiteur
	 * @return
	 * @throws SQLException
	 */
	public int enregistrerResultat(int idMatch, int scoreLocal, int scoreVisiteur) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("UPDATE `MATCH` ");
		sql.append("SET SCORE_LOCAL = "+dbi.toDB(scoreLocal)+", ");
		sql.append("SCORE_VISITEUR = "+dbi.toDB(scoreVisiteur)+", ");
		sql.append("STATUT = 3, ");
		sql.append("USER_MAJ = 'tsutter', ");
		sql.append("DERNIERE_MAJ = now()");
		sql.append("WHERE ID = " + dbi.toDB(idMatch));
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Enregistrement du résultat du match " +idMatch + ".");
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
	 * @param idMatch
	 * @param horaire
	 * @return
	 * @throws SQLException
	 */
	public int enregistrerHoraire(int idMatch, String horaire) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("UPDATE `MATCH` ");
		sql.append("SET HORAIRE = "+dbi.toDB(horaire)+", ");
		sql.append("USER_MAJ = 'tsutter', ");
		sql.append("DERNIERE_MAJ = now()");
		sql.append("WHERE ID = " + dbi.toDB(idMatch));
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Enregistrement de l'horaire du match " +idMatch + ".");
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
}

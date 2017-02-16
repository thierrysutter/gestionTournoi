package fr.assj.gestiontournoi.tournoi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.commun.DBInterface;
import fr.assj.gestiontournoi.commun.MySQL;

public class DaoTournoi {
	protected Connection connection;
	protected Logger logger;
    protected DBInterface dbi;
    
    /**
     * Constructeur
     * 
     * @param connection
     */
	public DaoTournoi(Connection connection) {
		this.connection = connection;
		this.dbi = new MySQL();
		this.logger = Logger.getLogger(DaoTournoi.class);
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Vector<Tournoi> trouverTournois(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT t.ID, t.LIBELLE, t.STATUT, st.LIBELLE as LIBELLE_STATUT, ");
		sql.append("pt.DATE_TOURNOI, pt.TYPE_TOURNOI, tt.LIBELLE as LIBELLE_TYPE_TOURNOI, pt.CATEGORIE, ");
		sql.append("c.LIBELLE as LIBELLE_CATEGORIE, pt.HEURE_DEBUT, pt.DESCRIPTION, pt.LIEU, pt.REGLEMENT, pt.PLAQUETTE, ");
		sql.append("pt.NB_EQUIPES, pt.NB_TERRAINS, pt.DUREE_RENCONTRE, pt.BAREME_VICTOIRE, pt.BAREME_NUL, ");
		sql.append("pt.BAREME_DEFAITE, pt.NB_GROUPES, pt.NB_EQUIPES_QUALIFIEES_GROUPE, pt.NB_EQUIPES_FINALE, pt.CONSOLANTE ");
		sql.append("FROM TOURNOI t ");
		sql.append("INNER JOIN STATUT_TOURNOI st ON t.STATUT=st.ID ");
		sql.append("INNER JOIN PARAMETRES_TOURNOI pt ON t.ID=pt.TOURNOI ");
		sql.append("INNER JOIN CATEGORIE c ON pt.CATEGORIE=c.ID ");
		sql.append("INNER JOIN TYPE_TOURNOI tt ON pt.TYPE_TOURNOI=tt.ID ");
		if(codeTournoi > 0 ) {
			sql.append("WHERE t.id = " + dbi.toDB(codeTournoi));
		}
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche des tournois existants.");
			logger.debug(sql);
		}
		
		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Vector<Tournoi> resultat = new Vector<Tournoi>();
			
			while (rs.next()) {
				Tournoi tournoi = new Tournoi();
				tournoi.setId(dbi.fromDB(rs.getInt("ID")));
				tournoi.setLibelle(dbi.fromDB(rs.getString("LIBELLE")));
				tournoi.setStatut(dbi.fromDB(rs.getInt("STATUT")));
				tournoi.setLibelleStatut(dbi.fromDB(rs.getString("LIBELLE_STATUT")));
				tournoi.setDateTournoi(dbi.fromDB(rs.getDate("DATE_TOURNOI")));
				tournoi.setTypeTournoi(dbi.fromDB(rs.getInt("TYPE_TOURNOI")));
				tournoi.setLibelleTypeTournoi(dbi.fromDB(rs.getString("LIBELLE_TYPE_TOURNOI")));
				tournoi.setCategorie(dbi.fromDB(rs.getInt("CATEGORIE")));
				tournoi.setHeureDebut(dbi.fromDB(rs.getString("HEURE_DEBUT")));
				tournoi.setLibelleCategorie(dbi.fromDB(rs.getString("LIBELLE_CATEGORIE")));
				tournoi.setDescription(dbi.fromDB(rs.getString("DESCRIPTION")));
				tournoi.setLieu(dbi.fromDB(rs.getString("LIEU")));
				tournoi.setReglement(dbi.fromDB(rs.getInt("REGLEMENT")));
				tournoi.setPlaquette(dbi.fromDB(rs.getInt("PLAQUETTE")));
				tournoi.setNbEquipes(dbi.fromDB(rs.getInt("NB_EQUIPES")));
				tournoi.setNbTerrains(dbi.fromDB(rs.getInt("NB_TERRAINS")));
				tournoi.setDureeRencontre(dbi.fromDB(rs.getInt("DUREE_RENCONTRE")));
				tournoi.setBaremeVictoire(dbi.fromDB(rs.getInt("BAREME_VICTOIRE")));
				tournoi.setBaremeNul(dbi.fromDB(rs.getInt("BAREME_NUL")));
				tournoi.setBaremeDefaite(dbi.fromDB(rs.getInt("BAREME_DEFAITE")));
				tournoi.setNbGroupes(dbi.fromDB(rs.getInt("NB_GROUPES")));
				tournoi.setNbEquipesQualifieesParGroupe(dbi.fromDB(rs.getInt("NB_EQUIPES_QUALIFIEES_GROUPE")));
				tournoi.setNbEquipesQualifiees(dbi.fromDB(rs.getInt("NB_EQUIPES_FINALE")));
				tournoi.setConsolante(dbi.fromDB(rs.getBoolean("CONSOLANTE")));
				
				resultat.add(tournoi);
			}
			resultat.trimToSize();
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
	public Tournoi trouverTournoiParId(int codeTournoi) throws SQLException {
		return trouverTournois(codeTournoi).firstElement();
	}
	
	/**
	 * 
	 * @param tournoi
	 * @return
	 * @throws SQLException
	 */
	public int ajouterTournoi(Tournoi tournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("INSERT INTO TOURNOI ");
		sql.append("(LIBELLE, STATUT, DATE_CREATION, USER_MAJ, DERNIERE_MAJ) ");
		sql.append("VALUES (");
		sql.append(""+dbi.toDB(tournoi.getLibelle())+",");
		sql.append("0,");
		sql.append("curdate(),");
		sql.append("'ADMIN',");
		sql.append("now()");
		sql.append(")");
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Insertion du tournoi.");
			logger.debug(sql);
		}
		
		Statement st = null;
		ResultSet rs = null;
		int idTournoi = -1;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			st.executeUpdate(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			rs = st.getGeneratedKeys();
			if (rs.next()) {
				idTournoi = rs.getInt(1);
		    } else {
		    	return -1;
		    }
			
			// insertion du paramétrage du tournoi
			sql = new StringBuffer(200);
			sql.append("INSERT INTO PARAMETRES_TOURNOI ");
			sql.append("(TOURNOI, DATE_TOURNOI, TYPE_TOURNOI, CATEGORIE, HEURE_DEBUT, DESCRIPTION, LIEU, REGLEMENT, PLAQUETTE, NB_EQUIPES, NB_TERRAINS, DUREE_RENCONTRE, BAREME_VICTOIRE, BAREME_NUL, BAREME_DEFAITE, NB_GROUPES, NB_EQUIPES_QUALIFIEES_GROUPE, NB_EQUIPES_FINALE, CONSOLANTE, DATE_CREATION, USER_MAJ, DERNIERE_MAJ) ");
			sql.append("VALUES (");
			sql.append(""+ dbi.toDB(idTournoi)+",");
			sql.append(""+ dbi.toDB(tournoi.getDateTournoi())+",");
			sql.append(""+ dbi.toDB(tournoi.getTypeTournoi())+",");
			sql.append(""+ dbi.toDB(tournoi.getCategorie())+",");
			sql.append(""+ dbi.toDB(tournoi.getHeureDebut())+",");
			sql.append(""+ dbi.toDB(tournoi.getDescription())+",");
			sql.append("'',"); // lieu
			sql.append("-1,"); // réglement
			sql.append("-1,"); // plaquette
			sql.append(""+ dbi.toDB(tournoi.getNbEquipes())+",");
			sql.append(""+ dbi.toDB(tournoi.getNbTerrains())+",");
			sql.append(""+ dbi.toDB(tournoi.getDureeRencontre())+",");
			sql.append(""+ dbi.toDB(tournoi.getBaremeVictoire())+",");
			sql.append(""+ dbi.toDB(tournoi.getBaremeNul())+",");
			sql.append(""+ dbi.toDB(tournoi.getBaremeDefaite())+",");
			sql.append(""+ dbi.toDB(tournoi.getNbGroupes())+",");
			sql.append(""+ dbi.toDB(tournoi.getNbEquipesQualifieesParGroupe())+",");
			sql.append(""+ dbi.toDB(tournoi.getNbEquipesQualifiees())+",");
			sql.append(""+ dbi.toDB(tournoi.isConsolante()?"1":"0")+",");
			sql.append("curdate(),");
			sql.append("'ADMIN',");
			sql.append("now()");
			sql.append(")");
			
			// logger
			if (logger.isDebugEnabled()) {
				logger.debug("Insertion des paramètres du tournoi.");
				logger.debug(sql);
			}
			
			if (st.executeUpdate(sql.toString())<0) {
				return -1;
			}
			
			return idTournoi;
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
	 * @param tournoi
	 * @return
	 * @throws SQLException
	 */
	public int modifierTournoi(Tournoi tournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("UPDATE TOURNOI ");
		sql.append("SET LIBELLE="+dbi.toDB(tournoi.getLibelle())+",");
		sql.append("USER_MAJ='ADMIN',");
		sql.append("DERNIERE_MAJ=now()");
		sql.append("WHERE ID="+dbi.toDB(tournoi.getId())+"");
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Modification du tournoi.");
			logger.debug(sql);
		}
		
		Statement st = null;
		ResultSet rs = null;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			st.executeUpdate(sql.toString());
			
			// modification du paramétrage du tournoi
			sql = new StringBuffer(200);
			sql.append("UPDATE PARAMETRES_TOURNOI ");
			sql.append("SET ");
			sql.append("DATE_TOURNOI="+ dbi.toDB(tournoi.getDateTournoi())+",");
			sql.append("TYPE_TOURNOI="+ dbi.toDB(tournoi.getTypeTournoi())+",");
			sql.append("CATEGORIE="+ dbi.toDB(tournoi.getCategorie())+",");
			sql.append("HEURE_DEBUT="+ dbi.toDB(tournoi.getHeureDebut())+",");
			sql.append("DESCRIPTION="+ dbi.toDB(tournoi.getDescription())+",");
			sql.append("LIEU='',"); // lieu
			sql.append("REGLEMENT=-1,"); // réglement
			sql.append("PLAQUETTE=-1,"); // plaquette
			sql.append("NB_EQUIPES="+ dbi.toDB(tournoi.getNbEquipes())+",");
			sql.append("NB_TERRAINS="+ dbi.toDB(tournoi.getNbTerrains())+",");
			sql.append("DUREE_RENCONTRE="+ dbi.toDB(tournoi.getDureeRencontre())+",");
			sql.append("BAREME_VICTOIRE="+ dbi.toDB(tournoi.getBaremeVictoire())+",");
			sql.append("BAREME_NUL="+ dbi.toDB(tournoi.getBaremeNul())+",");
			sql.append("BAREME_DEFAITE="+ dbi.toDB(tournoi.getBaremeDefaite())+",");
			sql.append("NB_GROUPES="+ dbi.toDB(tournoi.getNbGroupes())+",");
			sql.append("NB_EQUIPES_QUALIFIEES_GROUPE="+ dbi.toDB(tournoi.getNbEquipesQualifieesParGroupe())+",");
			sql.append("NB_EQUIPES_FINALE="+ dbi.toDB(tournoi.getNbEquipesQualifiees())+",");
			sql.append("CONSOLANTE="+ dbi.toDB(tournoi.isConsolante()?"1":"0")+",");
			sql.append("USER_MAJ='ADMIN',");
			sql.append("DERNIERE_MAJ=now() ");
			sql.append("WHERE TOURNOI="+dbi.toDB(tournoi.getId())+"");
			
			
			// logger
			if (logger.isDebugEnabled()) {
				logger.debug("Modification des paramètres du tournoi.");
				logger.debug(sql);
			}
			
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
	public int supprimerTournoi(int codeTournoi) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("DELETE FROM PARAMETRES_TOURNOI ");
		sql.append("WHERE TOURNOI = " + dbi.toDB(codeTournoi));
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Suppression des paramètres du tournoi.");
			logger.debug(sql);
		}
		
		Statement st = null;
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			st.executeUpdate(sql.toString());
						
			// insertion du paramétrage du tournoi
			sql = new StringBuffer(200);
			sql.append("DELETE FROM TOURNOI ");
			sql.append("WHERE ID = " + dbi.toDB(codeTournoi));
			
			// logger
			if (logger.isDebugEnabled()) {
				logger.debug("Suppression du tournoi.");
				logger.debug(sql);
			}
			
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
	 * @param nouveauStatut
	 * @return
	 * @throws SQLException
	 */
	public int changerStatutTournoi(int codeTournoi, int nouveauStatut) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		sql.append("UPDATE TOURNOI ");
		sql.append("SET STATUT= " + dbi.toDB(nouveauStatut) + ", ");
		sql.append("DERNIERE_MAJ= now(), ");
		sql.append("USER_MAJ= 'TEST' ");
		sql.append("WHERE ID = " + dbi.toDB(codeTournoi));
						
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Changement de statut du tournoi.");
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

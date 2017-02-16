package fr.assj.gestiontournoi.contact;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.commun.DBInterface;
import fr.assj.gestiontournoi.commun.MySQL;

public class DaoContact {
	protected Connection connection;
	protected Logger logger;
    protected DBInterface dbi;

    /**
     * 
     * @param connection
     */
	public DaoContact(Connection connection) {
		this.connection = connection;
		this.dbi = new MySQL();
		this.logger = Logger.getLogger(DaoContact.class);
	}
	
	/**
	 * Recherche toutes les équipes enregistrées dans le système
	 * @return un vecteur d'Club
	 * @throws SQLException
	 */
	public Vector<Contact> trouverContactsPourClub(int idClub) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("SELECT contact.fonction as id_fonction, fonction.libelle as libelle_fonction, contact.nom, contact.prenom, contact.adresse1, contact.adresse2, contact.adresse3, contact.code_postal, contact.ville, contact.pays, contact.tel, contact.email ");
		
		sql.append("FROM CONTACT contact LEFT OUTER JOIN FONCTION fonction ON (contact.fonction=fonction.id) ");
		sql.append("WHERE 1=1 ");
		sql.append("AND contact.club = "+dbi.toDB(idClub) + " ");
		sql.append("ORDER BY club.id, club.nom, equipe.categorie, equipe.libelle ");
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Recherche la liste des clubs en base");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		
		try {
			//exécution de la requête
			st = this.connection.createStatement();
			rs = st.executeQuery(sql.toString());
			
			Vector<Contact> resultat = new Vector<Contact>();
			
			Contact contact = null;
			while (rs.next()) {
				contact = new Contact();
				contact.setFonction(dbi.fromDB(rs.getInt("ID_FONCTION")));
				contact.setLibelleFonction(dbi.fromDB(rs.getString("LIBELLE_FONCTION")));
				contact.setNom(dbi.fromDB(rs.getString("NOM")));
				contact.setPrenom(dbi.fromDB(rs.getString("PRENOM")));
				contact.setAdresse1(dbi.fromDB(rs.getString("ADRESSE1")));
				contact.setAdresse2(dbi.fromDB(rs.getString("ADRESSE2")));
				contact.setAdresse3(dbi.fromDB(rs.getString("ADRESSE3")));
				contact.setCodePostal(dbi.fromDB(rs.getString("CODE_POSTAL")));
				contact.setVille(dbi.fromDB(rs.getString("VILLE")));
				contact.setPays(dbi.fromDB(rs.getString("PAYS")));
				contact.setTel(dbi.fromDB(rs.getString("TEL")));
				contact.setEmail(dbi.fromDB(rs.getString("EMAIL")));
				resultat.add(contact);
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
	 * @param nom
	 * @return
	 * @throws SQLException
	 */
	public int supprimerContact(int idClub, String nom) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("DELETE FROM CONTACT ");
		sql.append("WHERE CLUB = " + dbi.toDB(idClub) + " ");
		sql.append("AND NOM = " + dbi.toDB(nom) + " ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Suppression d'un contact.");
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
	 * @param fonction
	 * @param idClub
	 * @param nom
	 * @param prenom
	 * @param adresse1
	 * @param adresse2
	 * @param adresse3
	 * @param codePostal
	 * @param ville
	 * @param pays
	 * @param tel
	 * @param email
	 * @throws SQLException
	 */
	public void ajouterContact(int fonction, int idClub, String nom, String prenom, String adresse1, String adresse2, String adresse3, String codePostal, String ville, String pays, String tel, String email) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("INSERT INTO CONTACT (FONCTION, CLUB, NOM, PRENOM, ADRESSE1, ADRESSE2, ADRESSE3, CODE_POSTAL, VILLE, PAYS, TEL, EMAIL, DATE_CREATION, USER_MAJ, DERNIERE_MAJ) VALUES ( ");
		sql.append("" + dbi.toDB(fonction) + ", ");
		sql.append("" + dbi.toDB(idClub) + ", ");
		sql.append("" + dbi.toDB(nom) + ", ");
		sql.append("" + dbi.toDB(prenom) + ", ");
		sql.append("" + dbi.toDB(adresse1) + ", ");
		sql.append("" + dbi.toDB(adresse2) + ", ");
		sql.append("" + dbi.toDB(adresse3) + ", ");
		sql.append("" + dbi.toDB(codePostal) + ", ");
		sql.append("" + dbi.toDB(ville) + ", ");
		sql.append("" + dbi.toDB(pays) + ", ");
		sql.append("" + dbi.toDB(tel) + ", ");
		sql.append("" + dbi.toDB(email) + ", ");
		sql.append("curdate(), ");
		sql.append("'TEST', ");
		sql.append("now()) ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Ajout d'un contact dans le système.");
			logger.debug(sql.toString());
		}

		Statement st = null;
		
		try {
			//exécution de la requête
			//exécution de la requête
			st = this.connection.createStatement();
			st.executeUpdate(sql.toString());
		} finally {
			if (st != null) {
				st.close();
			}
		}
	}
}

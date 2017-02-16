package fr.assj.gestiontournoi.club;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;

import fr.assj.gestiontournoi.categorie.Categorie;
import fr.assj.gestiontournoi.commun.DBInterface;
import fr.assj.gestiontournoi.commun.MySQL;
import fr.assj.gestiontournoi.contact.Contact;
import fr.assj.gestiontournoi.equipe.Equipe;

public class DaoClub {
	protected Connection connection;
	protected Logger logger;
    protected DBInterface dbi;

    /**
     * 
     * @param connection
     */
	public DaoClub(Connection connection) {
		this.connection = connection;
		this.dbi = new MySQL();
		this.logger = Logger.getLogger(DaoClub.class);
	}
	
	/**
	 * Recherche toutes les équipes enregistrées dans le système
	 * @return un vecteur d'Club
	 * @throws SQLException
	 */
	public Vector<Club> trouverClubs(String libelleClub) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		
		
		sql.append("SELECT club.ID, club.NOM, club.PAYS, coalesce(club.LIGUE) as LIGUE, coalesce(club.DISTRICT) as DISTRICT, ");
		sql.append("equipe.id as id_equipe, equipe.categorie as id_categorie, categorie.libelle as libelle_categorie, equipe.libelle as libelle_equipe, ");
		sql.append("contact.fonction as id_fonction, fonction.libelle as libelle_fonction, contact.nom as nom_contact, contact.prenom as prenom_contact ");
		sql.append("FROM CLUB club LEFT OUTER JOIN EQUIPE equipe ON (club.id = equipe.club) LEFT OUTER JOIN CATEGORIE categorie ON (equipe.categorie=categorie.id) ");
		sql.append("LEFT OUTER JOIN CONTACT contact ON (club.id = contact.club) LEFT OUTER JOIN FONCTION fonction ON (contact.fonction=fonction.id) ");
		sql.append("WHERE 1=1 ");
		if (libelleClub!=null && !libelleClub.equals(""))
			sql.append("AND club.NOM like "+dbi.toDB("%"+libelleClub.toUpperCase()+"%") + " ");
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
			
			Vector<Club> resultat = new Vector<Club>();
			
			int idClub = 0;
			Club club = null;
			while (rs.next()) {
				
				if (idClub == 0 || idClub != rs.getInt("ID")) {
					club = new Club();
					club.setId(dbi.fromDB(rs.getInt("ID")));
					club.setNom(dbi.fromDB(rs.getString("NOM")));
					club.setPays(dbi.fromDB(rs.getString("PAYS")));
					club.setLigue(dbi.fromDB(rs.getString("LIGUE")));
					club.setDistrict(dbi.fromDB(rs.getString("DISTRICT")));
					resultat.add(club);
					idClub = club.getId();
				}
				
				if (dbi.fromDB(rs.getInt("ID_EQUIPE")) > 0) {
					Equipe equipe = new Equipe();
					equipe.setId(dbi.fromDB(rs.getInt("ID_EQUIPE")));
					equipe.setLibelle(dbi.fromDB(rs.getString("LIBELLE_EQUIPE")));
					club.ajouterEquipe(equipe);
					
					Categorie categorie = new Categorie();
					categorie.setId(dbi.fromDB(rs.getInt("ID_CATEGORIE")));
					categorie.setLibelle(dbi.fromDB(rs.getString("LIBELLE_CATEGORIE")));
					equipe.setCategorie(categorie);
				}
				
				if (dbi.fromDB(rs.getInt("ID_FONCTION")) > 0) {
					Contact contact = new Contact();
					contact.setFonction(dbi.fromDB(rs.getInt("ID_FONCTION")));
					contact.setLibelleFonction(dbi.fromDB(rs.getString("LIBELLE_FONCTION")));
					contact.setNom(dbi.fromDB(rs.getString("NOM_CONTACT")));
					contact.setPrenom(dbi.fromDB(rs.getString("PRENOM_CONTACT")));
					club.ajouterContact(contact);
				}
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
	 * @return un vecteur d'Club
	 * @throws SQLException
	 */
	public Club trouverClubParId(int idClub) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		
		
		sql.append("SELECT club.ID, club.NOM, club.PAYS, coalesce(club.LIGUE) as LIGUE, coalesce(club.DISTRICT) as DISTRICT, ");
		sql.append("club.NUM_AFFILIATION, club.ADRESSE1, club.ADRESSE2, club.ADRESSE3, club.CODE_POSTAL, club.VILLE, ");
		sql.append("club.SITE_WEB, club.TEL1, club.TEL2, club.FAX1, club.FAX2, club.EMAIL1, club.EMAIL2, ");
		sql.append("club.COULEUR1, club.COULEUR2, club.STADE, club.LOGO, ");
		sql.append("equipe.id as id_equipe, equipe.categorie as id_categorie, categorie.libelle as libelle_categorie, equipe.libelle as libelle_equipe ");
//		sql.append("contact.fonction as id_fonction, fonction.libelle as libelle_fonction, contact.nom as nom_contact, contact.prenom as prenom_contact ");
		sql.append("FROM CLUB club LEFT OUTER JOIN EQUIPE equipe ON (club.id = equipe.club) LEFT OUTER JOIN CATEGORIE categorie ON (equipe.categorie=categorie.id) ");
//		sql.append("LEFT OUTER JOIN CONTACT contact ON (club.id = contact.club) LEFT OUTER JOIN FONCTION fonction ON (contact.fonction=fonction.id) ");
		sql.append("WHERE 1=1 ");
		sql.append("AND club.ID = "+dbi.toDB(idClub) + " ");
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
			
			Club club = null;
			while (rs.next()) {
				
				if (club == null) {
					club = new Club();
					club.setId(dbi.fromDB(rs.getInt("ID")));
					club.setNom(dbi.fromDB(rs.getString("NOM")));
					club.setPays(dbi.fromDB(rs.getString("PAYS")));
					club.setLigue(dbi.fromDB(rs.getString("LIGUE")));
					club.setDistrict(dbi.fromDB(rs.getString("DISTRICT")));
					
					club.setNumeroAffiliation(dbi.fromDB(rs.getInt("NUM_AFFILIATION")));
					club.setAdresse1(dbi.fromDB(rs.getString("ADRESSE1")));
					club.setAdresse2(dbi.fromDB(rs.getString("ADRESSE2")));
					club.setAdresse3(dbi.fromDB(rs.getString("ADRESSE3")));
					club.setCodePostal(dbi.fromDB(rs.getString("CODE_POSTAL")));
					club.setVille(dbi.fromDB(rs.getString("VILLE")));
					club.setSiteWeb(dbi.fromDB(rs.getString("SITE_WEB")));
					club.setTel1(dbi.fromDB(rs.getString("TEL1")));
					club.setTel2(dbi.fromDB(rs.getString("TEL2")));
					club.setFax1(dbi.fromDB(rs.getString("FAX1")));
					club.setFax2(dbi.fromDB(rs.getString("FAX2")));
					club.setEmail1(dbi.fromDB(rs.getString("EMAIL1")));
					club.setEmail2(dbi.fromDB(rs.getString("EMAIL2")));
					club.setCouleur1(dbi.fromDB(rs.getString("COULEUR1")));
					club.setCouleur2(dbi.fromDB(rs.getString("COULEUR2")));
					club.setStade(dbi.fromDB(rs.getString("STADE")));
					club.setLogo(dbi.fromDB(rs.getString("LOGO")));
					
				}
				if (dbi.fromDB(rs.getInt("ID_EQUIPE")) > 0) {
					Equipe equipe = new Equipe();
					equipe.setId(dbi.fromDB(rs.getInt("ID_EQUIPE")));
					equipe.setLibelle(dbi.fromDB(rs.getString("LIBELLE_EQUIPE")));
					club.ajouterEquipe(equipe);
					
					Categorie categorie = new Categorie();
					categorie.setId(dbi.fromDB(rs.getInt("ID_CATEGORIE")));
					categorie.setLibelle(dbi.fromDB(rs.getString("LIBELLE_CATEGORIE")));
					equipe.setCategorie(categorie);
				}
				
				
//				if (dbi.fromDB(rs.getInt("ID_FONCTION")) > 0) {
//					Contact contact = new Contact();
//					contact.setFonction(dbi.fromDB(rs.getInt("ID_FONCTION")));
//					contact.setLibelleFonction(dbi.fromDB(rs.getString("LIBELLE_FONCTION")));
//					contact.setNom(dbi.fromDB(rs.getString("NOM_CONTACT")));
//					contact.setPrenom(dbi.fromDB(rs.getString("PRENOM_CONTACT")));
//					club.ajouterContact(contact);
//				}
			}
			
			return club;
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
	public int supprimerClub(int codeClub) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("DELETE FROM CLUB ");
		sql.append("WHERE ID = " + dbi.toDB(codeClub) + " ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Suppression d'un club.");
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
	 * @throws SQLException
	 */
	public int ajouterClub(String nomClub, String ligue, String district, String numAffiliation, String adresse, String codePostal, String ville, String pays, String tel, String fax, String email, String siteWeb) throws SQLException {
		StringBuffer sql = new StringBuffer(200);
		
		sql.append("INSERT INTO CLUB (NOM, NUM_AFFILIATION, LIGUE, DISTRICT, SITE_WEB, ADRESSE1, CODE_POSTAL, VILLE, PAYS, TEL1, EMAIL1, FAX1, DATE_CREATION, USER_MAJ, DERNIERE_MAJ) VALUES ( ");
		sql.append("" + dbi.toDB(nomClub) + ", ");
		sql.append("" + dbi.toDB(numAffiliation) + ", ");
		sql.append("" + dbi.toDB(ligue) + ", ");
		sql.append("" + dbi.toDB(district) + ", ");
		sql.append("" + dbi.toDB(siteWeb) + ", ");
		sql.append("" + dbi.toDB(adresse) + ", ");
		sql.append("" + dbi.toDB(codePostal) + ", ");
		sql.append("" + dbi.toDB(ville) + ", ");
		sql.append("" + dbi.toDB(pays) + ", ");
		sql.append("" + dbi.toDB(tel) + ", ");
		sql.append("" + dbi.toDB(email) + ", ");
		sql.append("" + dbi.toDB(fax) + ", ");
		sql.append("curdate(), ");
		sql.append("'TEST', ");
		sql.append("now()) ");
		
		// logger
		if (logger.isDebugEnabled()) {
			logger.debug("Ajout d'un club dans le système.");
			logger.debug(sql.toString());
		}

		Statement st = null;
		ResultSet rs = null;
		int idClub = -1;
		
		try {
			//exécution de la requête
			//exécution de la requête
			st = this.connection.createStatement();
			st.executeUpdate(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			rs = st.getGeneratedKeys();
			if (rs.next()) {
				idClub = rs.getInt(1);
		    }
			return idClub;
		} finally {
			if (st != null) {
				st.close();
			}
		}
	}
}

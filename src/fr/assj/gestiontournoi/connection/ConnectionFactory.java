package fr.assj.gestiontournoi.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * 
 * @author tsutter
 *
 */
public class ConnectionFactory {
	
	/**
	 * Logger spécifique à cette classe.
	 */
	protected static Logger logger = Logger.getLogger(ConnectionFactory.class);
	
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			logger.debug("ouverture d'une connexion à la base de données");
			
//			Properties prop = new Properties();
//			InputStream in = ConnectionFactory.class.getResourceAsStream("datasources.properties");
//			prop.load(in);
//			String bdd = prop.getProperty("datasource");
//			in.close();
			
//			Properties properties = new Properties();
//			InputStream input = null;
//			input = new FileInputStream("datasources.properties");
//
//			// load a properties file
//			properties.load(input);
//			
//			String bdd = properties.getProperty("datasource");
			
			
			/* Récupération de la source de données à partir du contexte DataSource JNDI*/
			 
			Context context = new InitialContext();
			DataSource datasource = (DataSource) context.lookup("java:comp/env/jdbc/gestifoot2");
			
			/**
			 * Obtention d'une connexion du pool
			 */
			connection = datasource.getConnection();
		} catch (NamingException e) {
			logger.error("Erreur lors de l'ouverture d'une connexion à la base de données", e);
			e.printStackTrace();
		} catch (SQLException e) {
			connection = null;
			logger.error("Erreur lors de l'ouverture d'une connexion à la base de données", e);
			e.printStackTrace();
//		} catch (IOException ioe) {
//			logger.error("Fichier de confiuration introuvable", ioe);
//			ioe.printStackTrace();
		}
		
		return connection;
	}

}

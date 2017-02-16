package fr.assj.gestiontournoi.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;

import fr.assj.gestiontournoi.ManagerBase;
import fr.assj.gestiontournoi.connection.ConnectionFactory;

/**
 * Servlet implementation class ServletBase
 */
@WebServlet("/ServletBase")
public class ServletBase extends ActionServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1798886238608757130L;
	
	protected static Logger logger = Logger.getLogger("LOG");
       
    /**
     * @see ActionServlet#ActionServlet()
     */
    public ServletBase() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
	}
	
	public void init() throws ServletException
	{
		super.init();
		logger.info("**************************************************");
		logger.info("version tomcat : " + this.getServletContext().getServerInfo());
		logger.info("version appli  : 1.00.00");
		
		
//		BasicDataSource dataBase = new BasicDataSource();
//		try {
//			dataBase.setDriverClassName("com.mysql.jdbc.Driver");
//			dataBase.setUrl("jdbc:mysql://localhost:3306/test?autoReconnect=true");
//			dataBase.setDefaultAutoCommit(true);
//			dataBase.setUsername("root");
//			dataBase.setPassword("root");
//			dataBase.setMaxIdle(30);
//			dataBase.setMaxActive(100);
//			dataBase.setMaxWait(10000);
//			
//			this.da
//			
//		} catch (Exception e) {
//			logger.error("Erreur lors de la configuration de la base de données", e);
//		}
		Connection connexion = null;
		try {
			connexion = ConnectionFactory.getConnection();
			logger.debug("Ouverture d'une connexion à la base de données.");
			
			// mise en session de la liste des catégories
			ManagerBase.construireListeReference(connexion);
			
		} catch (Exception e) {
			logger.error("Erreur lors de la mise en cache des données de session", e);
		} finally {
			try {
				if (connexion != null && !connexion.isClosed()) {
					connexion.close();
					logger.debug("fermeture de la connexion a la base de données");
				}
			} catch (SQLException sqle) {
				logger.error("erreur : " + sqle);
			}
		}
	}

}

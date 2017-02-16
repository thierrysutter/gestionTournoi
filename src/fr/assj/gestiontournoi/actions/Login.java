package fr.assj.gestiontournoi.actions;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Login
 * 
 * @author Thierry SUTTER

 * @struts.action path="/login"
 * @struts.action-forward name="login" path="/login/login.jsp"
 * @struts.action-forward name="changementMotDePasse" path="/login/changementMotDePasse.jsp"
 * @struts.action-forward name="success" path="/accueil.do" 
 * @struts.action-forward name="close" path="/login/loginClose.jsp"
 * 
 */
public class Login extends ActionBase {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		String login = request.getParameter("login");
	    String password = request.getParameter("password");
	    boolean close = stringToBoolean(request.getParameter("close"), false);
		
		try {
			if (close) {
	        	return mapping.findForward("close");
	        }
	    	
	    	if (null == login) {
	    		return mapping.findForward("login");
	    	}
	    	
	    	return mapping.findForward("success");
	    	
			/*
			connection = this.openDataBaseconnection(connection, request);
			Utilisateur user = ManagerUtilisateur.trouverCompteUtilisateurParLogin(connection, login);
			// si login inconnu
	    	if (user == null) {
				String messageErreur = "Utilisateur inconnu";
				request.setAttribute("messageErreur", messageErreur);
				return mapping.findForward("login");
			}
			
			// vérification des identifiants saisis
			String messageErreurLogin = ManagerUtilisateur.verifierUtilisateur(connection, user, password);
						
			if (messageErreurLogin != null) {
				request.setAttribute("messageErreur", messageErreurLogin);
				return mapping.findForward("login");
			} else {
				// La connexion a réussi
				request.getSession().setAttribute("global_user", user);
				
				return mapping.findForward("success");
			}
			*/
			
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("erreur", e);
			return mapping.findForward("erreur");
		} finally {
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
					logger.debug("fermeture de la connexion a la base de données");
				}
			} catch (SQLException sqle) {
				logger.error("erreur : " + sqle);
			}
		}
	}
}

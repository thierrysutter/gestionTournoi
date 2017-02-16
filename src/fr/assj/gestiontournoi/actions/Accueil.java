package fr.assj.gestiontournoi.actions;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Accueil
 * 
 * @author Thierry SUTTER
 * 
 * @struts.action path="/accueil"
 * @struts.action-forward name="success" path="/accueil/accueil.jsp"
 * 
 */
public class Accueil extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		try {
//			connection = this.openDataBaseconnection(connection, request);
			
			// recherche des tournois
//			Vector<Tournoi> listeTournois = ManagerTournoi.trouverTournoi(connection);
//			
//			request.setAttribute("listeTournois", listeTournois);
			
			return mapping.findForward("success");
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

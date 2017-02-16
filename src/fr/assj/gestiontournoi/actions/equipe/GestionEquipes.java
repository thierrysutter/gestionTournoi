package fr.assj.gestiontournoi.actions.equipe;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.assj.gestiontournoi.actions.ActionBase;
import fr.assj.gestiontournoi.equipe.Equipe;
import fr.assj.gestiontournoi.equipe.ManagerEquipe;

/**
 * 
 * @author tsutter
 *
 * @struts.action path="/equipes"
 * @struts.action-forward name="success" path="/equipe/gestionEquipes.jsp"
 */
public class GestionEquipes extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		try {
			connection = this.openDataBaseconnection(connection, request);
			
			String libelleClub = stringToString(request.getParameter("libelleClub"), "");
			String libelleEquipe = stringToString(request.getParameter("libelleEquipe"), "");
			
			Vector<Equipe> listeEquipes = ManagerEquipe.trouverEquipes(connection, libelleClub, libelleEquipe);
			request.setAttribute("listeEquipes", listeEquipes);
			
			request.setAttribute("libelleClub", libelleClub);
			request.setAttribute("libelleEquipe", libelleEquipe);
			
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

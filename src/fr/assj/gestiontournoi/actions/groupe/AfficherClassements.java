package fr.assj.gestiontournoi.actions.groupe;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.assj.gestiontournoi.actions.ActionBase;
import fr.assj.gestiontournoi.groupe.Groupe;
import fr.assj.gestiontournoi.groupe.ManagerGroupe;
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;
import fr.assj.gestiontournoi.tournoi.Tournoi;

/**
 * 
 * @author Thierry SUTTER
 * 
 * @struts.action path="/groupe.afficherClassements"
 * @struts.action-forward name="success" path="/groupe.afficherClassements.jsp"
 *
 */
public class AfficherClassements extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		int codeTournoi = stringToInt(request.getParameter("codeTournoi"), -1);
		
		try {
			connection = this.openDataBaseconnection(connection, request);
			
			// recherche du tournoi
			Tournoi tournoi = ManagerTournoi.trouverTournoiParId(connection, codeTournoi);
			
			// mise à jour du statut du touroi
			// la première phase est terminée, on passe à la phase d'élimination directe (tournoi + consolante)
			
			
			// recherche les groupes du tournois
			Vector<Groupe> groupesTournoi = ManagerGroupe.trouverGroupesEquipesTournoi(connection, codeTournoi);
			tournoi.setListeGroupes(groupesTournoi);
			
			request.setAttribute("tournoi", tournoi);
						
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

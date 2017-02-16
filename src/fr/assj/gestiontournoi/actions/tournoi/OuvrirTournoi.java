package fr.assj.gestiontournoi.actions.tournoi;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import fr.assj.gestiontournoi.actions.ActionBase;
import fr.assj.gestiontournoi.groupe.ManagerGroupe;
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;

/**
 * 
 * @author tsutter
 * 
 * @struts.action path="/tournoi.ouvrir"
 * @struts.action-forward name="success" path="/tournoi.afficher.do"
 *
 */
public class OuvrirTournoi extends ActionBase {
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
			// changement du statut du tournoi
			ManagerTournoi.changerStatutTournoi(connection, codeTournoi, 3);
			
			
			// initialisation des données de la table EQUIPE_GROUPE_TOURNOI
			ManagerGroupe.initialiserDonneesEquipes(connection, codeTournoi);
			// initialisation du classement des groupes ????
			
			ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
			redirect.addParameter("codeTournoi", codeTournoi);
			
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

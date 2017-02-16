package fr.assj.gestiontournoi.actions.tournoi;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.assj.gestiontournoi.actions.ActionBase;
import fr.assj.gestiontournoi.groupe.ManagerGroupe;
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;
import fr.assj.gestiontournoi.tournoi.Tournoi;

/**
 * 
 * @author tsutter
 * 
 * @struts.action path="/tournoi.duppliquer"
 * @struts.action-forward name="success" path="/tournoi.lister.do"
 */
public class DuppliquerTournoi extends ActionBase {
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
			
			Tournoi tournoiADuppliquer = ManagerTournoi.trouverTournoiParId(connection, codeTournoi);
			
			int codeNouveauTournoi = ManagerTournoi.ajouterTournoi(connection, tournoiADuppliquer);
			
			tournoiADuppliquer.setId(codeNouveauTournoi);
			
			// création à "vide" des groupes du tournoi
			for (int i=0; i<tournoiADuppliquer.getNbGroupes(); i++) {
				ManagerGroupe.ajouterGroupeTournoi(connection, codeNouveauTournoi, "Groupe " + String.valueOf(i+1));
			}
			
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

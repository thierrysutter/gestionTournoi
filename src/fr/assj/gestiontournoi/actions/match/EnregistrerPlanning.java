package fr.assj.gestiontournoi.actions.match;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.assj.gestiontournoi.actions.ActionBase;
import fr.assj.gestiontournoi.match.ManagerMatch;
import fr.assj.gestiontournoi.match.Match;

/**
 * 
 * @author tsutter
 * 
 * @struts.action path="/plannning.enregistrer"
 * @struts.action-forward name="success" path="/tournoi.afficher.do"
 *
 */
public class EnregistrerPlanning extends ActionBase {
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
			
			// recup de la liste des matchs du tournoi
			Vector<Match> listeMatchs = ManagerMatch.trouverMatchsTournoi(connection, codeTournoi);
			if (!listeMatchs.isEmpty()) {
				int nbMatchsEnregistres = 0;
				for (Match match : listeMatchs) {
					String horaireMatch = "";
					if (!"".equals(request.getParameter("horaire_"+match.getId()))) {
						horaireMatch = stringToString(request.getParameter("horaire_"+match.getId()), "");
					}
					
					// enregistrement des résultats
					if (!horaireMatch.equals("") && !horaireMatch.equals(match.getHoraire())) {
						match.setHoraire(horaireMatch);
						ManagerMatch.enregistrerHoraire(connection, match.getId(), horaireMatch);
						nbMatchsEnregistres++;
					}
				}
				
				if (nbMatchsEnregistres>0) {
					request.setAttribute("messageOK", "Sauvegarde effectuée");
				} else {
					request.setAttribute("messageKO", "Aucune donnée à sauvegarder");
				}
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

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
 * @struts.action path="/match.enregistrer"
 * @struts.action-forward name="success" path="/tournoi.afficher.do"
 *
 */
public class EnregistrerResultats extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		int codeTournoi = stringToInt(request.getParameter("codeTournoi"), -1);
		boolean saveAll = stringToBoolean(request.getParameter("saveAll"), false);
		
		try {
			connection = this.openDataBaseconnection(connection, request);
			
			if (saveAll) {
				// recup de la liste des matchs du tournoi
				Vector<Match> listeMatchs = ManagerMatch.trouverMatchsTournoi(connection, codeTournoi);
				if (!listeMatchs.isEmpty()) {
					int nbMatchsEnregistres = 0;
					for (Match match : listeMatchs) {
						int scoreLocal = -1, scoreVisiteur = -1;
						if (!"".equals(request.getParameter("match_"+match.getId()+"_loc"))) {
							scoreLocal = stringToInt(request.getParameter("match_"+match.getId()+"_loc"), -1);
						}
						if (!"".equals(request.getParameter("match_"+match.getId()+"_vis"))) {
							scoreVisiteur = stringToInt(request.getParameter("match_"+match.getId()+"_vis"), -1);
						}
						
						// enregistrement des résultats
						if (scoreLocal != -1 && scoreVisiteur != -1) {
							match.setScoreLocal(scoreLocal);
							match.setScoreVisiteur(scoreVisiteur);
							ManagerMatch.enregistrerResultat(connection, match);
							nbMatchsEnregistres++;
						}
					}
					
					if (nbMatchsEnregistres>0) {
						request.setAttribute("messageOK", "Sauvegarde de tous les résultats effectuée");
					} else {
						request.setAttribute("messageKO", "Aucune donnée à sauvegarder");
					}
				}
			} else {
				int idMatch = stringToInt(request.getParameter("idMatch"), -1);
				if (idMatch > 0) {
					int scoreLocal = -1, scoreVisiteur = -1;
					if (!"".equals(request.getParameter("match_"+idMatch+"_loc"))) {
						scoreLocal = stringToInt(request.getParameter("match_"+idMatch+"_loc"), -1);
					}
					if (!"".equals(request.getParameter("match_"+idMatch+"_vis"))) {
						scoreVisiteur = stringToInt(request.getParameter("match_"+idMatch+"_vis"), -1);
					}
					if (scoreLocal != -1 && scoreVisiteur != -1) {
						ManagerMatch.enregistrerResultat(connection, idMatch, scoreLocal, scoreVisiteur);
						request.setAttribute("messageOK", "Sauvegarde du résultat effectuée");
					} else {
						request.setAttribute("messageKO", "Aucune donnée à sauvegarder");
					}
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

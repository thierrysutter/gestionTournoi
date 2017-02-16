package fr.assj.gestiontournoi.actions.match;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import fr.assj.gestiontournoi.actions.ActionBase;
import fr.assj.gestiontournoi.equipe.Equipe;
import fr.assj.gestiontournoi.groupe.Groupe;
import fr.assj.gestiontournoi.groupe.ManagerGroupe;
import fr.assj.gestiontournoi.match.ManagerMatch;
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;
import fr.assj.gestiontournoi.tournoi.Tournoi;

/**
 * 
 * @author Thierry SUTTER
 * 
 * @struts.action path="/match.generer"
 * @struts.action-forward name="success" path="/tournoi.afficher.do"
 *
 */
public class GenererCalendrier extends ActionBase {
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
			Tournoi tournoi = ManagerTournoi.trouverTournoiParId(connection, codeTournoi);
			if (tournoi != null) {
				//supprimer tous les matchs
				ManagerMatch.supprimerMatchsTournoi(connection, codeTournoi);
				
				// heure de début du tournoi ie heure du premier match
				String heureDebutTournoi = tournoi.getHeureDebut();
				
				Vector<Groupe> groupesTournoi = ManagerGroupe.trouverGroupesEquipesTournoi(connection, codeTournoi);
				for (Groupe groupe : groupesTournoi) {
					// pour chaque groupe, on génère le calendrier
					// cas du match unique ie pas d'aller retour
					// chaque équipe du groupe doit joué un nombre de match égal au nombre d'équipe du groupe - 1
					// et un seul match contre chaque équipe
					
					Vector<Equipe> listeEquipes = groupe.getListeEquipes();
					if (listeEquipes!= null && !listeEquipes.isEmpty()) {
						if (listeEquipes.size()%2==1) {
							// nombre d'équipe impair donc on ajoute une équipe Exempt pour compléter
							Equipe exempt = new Equipe();
							exempt.setId(-1);
							exempt.setLibelle("exempt");
							listeEquipes.add(exempt);
						}
						
						for (int i=1; i<listeEquipes.size();i++) {
							// creation du tour i
							ManagerMatch.creerTour(connection, i, listeEquipes, tournoi.getDateTournoi(), heureDebutTournoi, tournoi.getId(), groupe.getLibelle());
							listeEquipes.add(1, listeEquipes.lastElement());
							listeEquipes.remove(listeEquipes.size()-1);
						}
					}
				}
			}
			
			// changement de statut du tournoi
			ManagerTournoi.changerStatutTournoi(connection, codeTournoi, 2);	
			
			ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
			redirect.addParameter("codeTournoi", codeTournoi);
			redirect.addParameter("statut", 4);
			
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

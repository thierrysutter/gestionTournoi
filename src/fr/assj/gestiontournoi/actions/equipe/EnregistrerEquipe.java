package fr.assj.gestiontournoi.actions.equipe;

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
import fr.assj.gestiontournoi.equipe.ManagerEquipe;
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;
import fr.assj.gestiontournoi.tournoi.Tournoi;

/**
 * 
 * @author tsutter
 *
 * @struts.action path="/equipe.enregistrer"
 * @struts.action-forward name="saisieEquipes" path="/equipe/saisieEquipes.jsp"
 * @struts.action-forward name="clotureInscription" path="/groupe.repartir.do"
 */
public class EnregistrerEquipe extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		int codeTournoi = stringToInt(request.getParameter("codeTournoi"), -1);
		String[] equipesSelectionnes = request.getParameterValues("equipesSelectionnes");
		boolean clotureInscription = stringToBoolean(request.getParameter("clotureInscription"), false);
		
		try {
			connection = this.openDataBaseconnection(connection, request);
			Tournoi tournoi = ManagerTournoi.trouverTournoiParId(connection, codeTournoi);
			Vector<Equipe> listeEquipesTournoi = null;
			if (equipesSelectionnes.length > 0) {
				listeEquipesTournoi = new Vector<Equipe>();
				// on supprime les equipes du tournoi en base (annule et remplace)
				ManagerEquipe.supprimerEquipesTournoi(connection, codeTournoi);
		        // puis on reinsere les équipes
				for (String equipe : equipesSelectionnes) {
					Equipe equipeInscrite = new Equipe();
					equipeInscrite.setId(Integer.parseInt(equipe.split("_")[0]));
					equipeInscrite.setLibelle(equipe.split("_")[1]);
					
		        	if (equipeInscrite.getId() == -1) {
	        		// on doit d'abord ajouter l'équipe dans la base pour obtenir un nouvel id
		        		equipeInscrite.setId(ManagerEquipe.ajouterEquipe(connection, equipeInscrite.getLibelle()));
		        	}

		        	ManagerEquipe.inscrireEquipeTournoi(connection, equipeInscrite.getId(), codeTournoi);
		        	
		        	listeEquipesTournoi.add(equipeInscrite);
				}

				request.setAttribute("message", "Sauvegarde effectuée");
			}
			
			request.setAttribute("tournoi", tournoi);
			
			if (clotureInscription && !listeEquipesTournoi.isEmpty() && tournoi.getNbEquipes() == listeEquipesTournoi.size()) {
				// le tournoi est maintenant complet
				// le statut change et passe à 2
				ManagerTournoi.changerStatutTournoi(connection, codeTournoi, 2);
				
				request.setAttribute("codeTournoi", codeTournoi);
				request.setAttribute("statut", 2);
				
				ActionRedirect redirect = new ActionRedirect(mapping.findForward("clotureInscription"));
				redirect.addParameter("codeTournoi", codeTournoi);
				redirect.addParameter("statut", 2);
				
				return mapping.findForward("clotureInscription");
			} else {
				// le tournoi n'est pas encore complet mais les inscriptions ont débuté
				// le statut du tournoi passe à 1
				ManagerTournoi.changerStatutTournoi(connection, codeTournoi, 1);
				request.setAttribute("equipesTournoi", listeEquipesTournoi);
				return mapping.findForward("saisieEquipes");
			}
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

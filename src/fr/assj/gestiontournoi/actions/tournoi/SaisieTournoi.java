package fr.assj.gestiontournoi.actions.tournoi;

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
import fr.assj.gestiontournoi.groupe.Groupe;
import fr.assj.gestiontournoi.groupe.ManagerGroupe;
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;
import fr.assj.gestiontournoi.tournoi.Tournoi;

/**
 * 
 * @author tsutter
 * 
 * @struts.action path="/tournoi.saisie"
 * @struts.action-forward name="saisieEquipes" path="/equipe/saisieEquipes.jsp"
 * @struts.action-forward name="repartitionGroupes" path="/tournoi/tournoiComplet.jsp"
 * @struts.action-forward name="generationCalendrier" path="/tournoi/tournoiComplet.jsp"
 * @struts.action-forward name="validationTournoi" path="/tournoi/tournoiComplet.jsp"
 * @struts.action-forward name="success" path="/tournoi.liste.do"
 */
public class SaisieTournoi extends ActionBase {
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
			
			// recherche des tournois existants
			Tournoi tournoi = ManagerTournoi.trouverTournoiParId(connection, codeTournoi);
			request.setAttribute("tournoi", tournoi);
			
			// recherche des équipes inscrites au tournoi
			Vector<Equipe> listeEquipesTournoi = ManagerEquipe.trouverEquipesTournoi(connection, codeTournoi);
			request.setAttribute("equipesTournoi", listeEquipesTournoi);
			// en fonction du statut du tournoi, on affiche la page adéquate
			//
			// statut = 0 ==> seul le paramétrage du tournoi a été renseigné, on affiche la page de saisie des équipes participantes
			// statut = 1 ==> la saisie des équipes a commencé mais n'est pas terminé
			// statut = 2 ==> la saisie des équipes a été finalisé, la répartition des équipes dans les groupes n'a pas été lancée
			// statut = 3 ==> les groupes sont finalisés mais le calendrier du tournoi n'a pas été généré
			// statut = 4 ==> le tournoi est prêt, il ne reste plus qu'à le valider
			switch(tournoi.getStatut()) {
				case 0: {
					Vector<Equipe> listeEquipesDispo = ManagerEquipe.trouverEquipesDispo(connection, codeTournoi, tournoi.getCategorie());
					request.setAttribute("listeEquipesDispo", listeEquipesDispo);
					return mapping.findForward("saisieEquipes");
//					break;
				}
				case 1: {
					Vector<Equipe> listeEquipesDispo = ManagerEquipe.trouverEquipesDispo(connection, codeTournoi, tournoi.getCategorie());
					request.setAttribute("listeEquipesDispo", listeEquipesDispo);
					return mapping.findForward("saisieEquipes");
//					break;
				}
				case 2: {
					// recherche de la liste des groupes du tournoi
					Vector<Groupe> groupesTournoi = ManagerGroupe.trouverGroupesTournoi(connection, codeTournoi);
					request.setAttribute("groupesTournoi", groupesTournoi);
					return mapping.findForward("repartitionGroupes");
//					break;
				}
				case 3: {
					// recherche de la liste des groupes du tournoi avec pour chacun la liste des équipes qui le compose
					Vector<Groupe> groupesTournoi = ManagerGroupe.trouverGroupesEquipesTournoi(connection, codeTournoi);
					request.setAttribute("groupesTournoi", groupesTournoi);
					return mapping.findForward("generationCalendrier");
//					break;
				}
				case 4: {
					// recherche de la liste des groupes du tournoi avec pour chacun la liste des équipes qui le compose
					Vector<Groupe> groupesTournoi = ManagerGroupe.trouverGroupesEquipesTournoi(connection, codeTournoi);
					request.setAttribute("groupesTournoi", groupesTournoi);
					return mapping.findForward("validationTournoi");
//					break;
				}
				default: {
					return mapping.findForward("success");
//					break;
				}
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
	
	public Vector<Equipe> retirerEquipesInscrites(Vector<Equipe> listeEquipes, Vector<Equipe> listeEquipesTournoi) {
		Vector<Equipe> resultat = new Vector<Equipe>();
		boolean trouve = false;
		for (Equipe equipe : listeEquipes) {
			for(Equipe equipeTournoi : listeEquipesTournoi) {
				if (equipe.getId()==equipeTournoi.getId()) {
					trouve = true;
					break;
				}
			}
			
			if (!trouve) {
				resultat.add(equipe);
			}
			trouve = false;
		}
		
		resultat.trimToSize();
		return resultat;
	}
}

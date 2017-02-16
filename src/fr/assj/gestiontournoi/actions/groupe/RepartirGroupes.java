package fr.assj.gestiontournoi.actions.groupe;

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
import fr.assj.gestiontournoi.groupe.Groupe;
import fr.assj.gestiontournoi.groupe.ManagerGroupe;
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;
import fr.assj.gestiontournoi.tournoi.Tournoi;

/**
 * 
 * @author Thierry SUTTER
 * 
 * @struts.action path="/groupe.repartir"
 * @struts.action-forward name="success" path="/match.generer.do"
 *
 */
public class RepartirGroupes extends ActionBase {
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
				Vector<Groupe> groupesTournoi = ManagerGroupe.trouverGroupesTournoi(connection, codeTournoi);
				if (groupesTournoi != null && !groupesTournoi.isEmpty()) {
					// on purge un éventuel précédent tirage au sort
					ManagerGroupe.supprimerEquipesGroupesTournoi(connection, tournoi.getId());
					
					// on récupère la liste des équipes 
					Vector<Equipe> equipesTournoi = ManagerEquipe.trouverEquipesTournoi(connection, codeTournoi);
					
					int nbEquipesParGroupe = equipesTournoi.size() / groupesTournoi.size();
					if (groupesTournoi.size() == 1) {
						for(Equipe equipe : equipesTournoi) {
							groupesTournoi.firstElement().ajouterEquipe(equipe);
						}
						ManagerGroupe.ajouterEquipeGroupeTournoi(connection, tournoi.getId(), groupesTournoi.firstElement());
					} else {
						// pour chaque groupe, on tire au hasard une équipe et on lui ajoute
						for(Groupe groupe : groupesTournoi) {
							boolean groupeComplet = false;
							while(!groupeComplet) {
								int random = (int)(Math.random() * equipesTournoi.size());
								Equipe equipe = equipesTournoi.elementAt(random);
			
								// il faut vérifier que 2 équipes du même club ne puissent pas se retrouver dans le même groupe
								//TODO
								
								groupe.ajouterEquipe(equipe);
								equipesTournoi.remove(random);
								
								if (groupe.getListeEquipes().size() == nbEquipesParGroupe) {
									groupeComplet = true;
									//tournoi.ajouterGroupe(groupe);
									// insertion en base equipe_groupe_tournoi
									ManagerGroupe.ajouterEquipeGroupeTournoi(connection, tournoi.getId(), groupe);
									
									// génération automatique du calendrier du groupe
									// cas du match unique ie pas d'aller retour
									// chaque équipe du groupe doit joué un nombre de match égal au nombre d'équipe du groupe - 1
									// et un seul match contre chaque équipe
									//ManagerGroupe.generationCalendrierGroupe(connection, tournoi.getDateTournoi(), groupe);
								}
							}
						}
					}
					// changement de statut du tournoi
					ManagerTournoi.changerStatutTournoi(connection, codeTournoi, 2);
					
					ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
					redirect.addParameter("codeTournoi", codeTournoi);
					redirect.addParameter("statut", 3);
					
					// envoi dans request
					request.setAttribute("tournoi", tournoi);
					request.setAttribute("equipesTournoi", equipesTournoi);
					request.setAttribute("groupesTournoi", groupesTournoi);
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

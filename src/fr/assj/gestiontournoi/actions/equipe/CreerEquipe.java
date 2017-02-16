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
import fr.assj.gestiontournoi.categorie.Categorie;
import fr.assj.gestiontournoi.categorie.ListeCategories;
import fr.assj.gestiontournoi.club.Club;
import fr.assj.gestiontournoi.club.ManagerClub;
import fr.assj.gestiontournoi.equipe.ManagerEquipe;

/**
 * 
 * @author tsutter
 *
 * @struts.action path="/equipes.ajouter"
 * @struts.action-forward name="success" path="/equipe/creationEquipe.jsp"
 * @struts.action-forward name="liste" path="/equipes.do"
 */
public class CreerEquipe extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		try {
			connection = this.openDataBaseconnection(connection, request);
			
			boolean submit = stringToBoolean(request.getParameter("sub"), false);
			if (submit) {
				String nomEquipe = stringToString(request.getParameter("nom"), "");
				int club = stringToInteger(request.getParameter("club"), 0);
				int categorie = stringToInteger(request.getParameter("categorie"), 0);
				
				ManagerEquipe.ajouterEquipe(connection, nomEquipe, club, categorie);
				
				return mapping.findForward("liste");
			}
			
			//Vector<Club> listeClubs = ListeClubs.getInstance().trouverListe();
			Vector<Club> listeClubs = ManagerClub.trouverClubs(connection, "");
			request.setAttribute("listeClubs", listeClubs);
			
			Vector<Categorie> listeCategories = ListeCategories.getInstance().trouverListe();
			request.setAttribute("listeCategories", listeCategories);
			
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

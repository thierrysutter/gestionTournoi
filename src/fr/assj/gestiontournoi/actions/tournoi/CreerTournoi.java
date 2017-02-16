package fr.assj.gestiontournoi.actions.tournoi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.assj.gestiontournoi.actions.ActionBase;
import fr.assj.gestiontournoi.categorie.Categorie;
import fr.assj.gestiontournoi.categorie.ListeCategories;
import fr.assj.gestiontournoi.groupe.ManagerGroupe;
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;
import fr.assj.gestiontournoi.tournoi.Tournoi;

/**
 * 
 * @author tsutter
 * 
 * @struts.action path="/tournoi.creer"
 * @struts.action-forward name="success" path="/tournoi/creerTournoi.jsp"
 * @struts.action-forward name="creation" path="/tournoi.lister.do"
 *
 */
public class CreerTournoi extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		boolean submit = stringToBoolean(request.getParameter("sub"), false);
		
		try {
			connection = this.openDataBaseconnection(connection, request);
			Vector<Categorie> listeCategories = ListeCategories.getInstance().trouverListe();
			request.setAttribute("listeCategories", listeCategories);
			
			if (submit) {
				// recup des données du formulaire
				String libelle = stringToString(request.getParameter("libelle"), "");
				Date dateTournoi = stringToDate(request.getParameter("dateTournoi"), null);
				String heureDebut = stringToString(request.getParameter("heureDebut"), null);
				int categorie = stringToInt(request.getParameter("categorie"), -1);
				int typeTournoi = stringToInt(request.getParameter("typeTournoi"), -1);
				int nbEquipes = stringToInt(request.getParameter("nbEquipes"), -1);
				int nbTerrains = stringToInt(request.getParameter("nbTerrains"), -1);
				int dureeRencontre = stringToInt(request.getParameter("dureeRencontre"), -1);
				int baremeVictoire = stringToInt(request.getParameter("baremeVictoire"), -1);
				int baremeNul = stringToInt(request.getParameter("baremeNul"), -1);
				int baremeDefaite = stringToInt(request.getParameter("baremeDefaite"), -1);
				int nbGroupes = stringToInt(request.getParameter("nbGroupes"), -1);
				int nbEquipesQualifieesGroupe = stringToInt(request.getParameter("nbEquipesQualifieesGroupe"), -1);
				boolean consolante = stringToBoolean(request.getParameter("consolante"), false);
				String description = stringToString(request.getParameter("description"), "");
				
				// création du tournoi + paramétrage
				// groupes, etc ...
				Tournoi tournoiACreer = new Tournoi();
				tournoiACreer.setLibelle(libelle);
				tournoiACreer.setDateTournoi(dateTournoi);
				tournoiACreer.setHeureDebut(heureDebut);
				tournoiACreer.setStatut(1);
				tournoiACreer.setCategorie(categorie);
				tournoiACreer.setTypeTournoi(typeTournoi);
				tournoiACreer.setNbEquipes(nbEquipes);
				tournoiACreer.setNbTerrains(nbTerrains);
				tournoiACreer.setDureeRencontre(dureeRencontre);
				tournoiACreer.setBaremeVictoire(baremeVictoire);
				tournoiACreer.setBaremeNul(baremeNul);
				tournoiACreer.setBaremeDefaite(baremeDefaite);
				tournoiACreer.setNbGroupes(nbGroupes);
				tournoiACreer.setNbEquipesQualifieesParGroupe(nbEquipesQualifieesGroupe);
				tournoiACreer.setNbEquipesQualifiees((nbGroupes*nbEquipesQualifieesGroupe));
				tournoiACreer.setConsolante(consolante);
				tournoiACreer.setDescription(description);
								
				int codeTournoi = ManagerTournoi.ajouterTournoi(connection, tournoiACreer);
				
				// création à "vide" des groupes du tournoi
				for (int i=0; i<nbGroupes; i++) {
					ManagerGroupe.ajouterGroupeTournoi(connection, codeTournoi, "Groupe " + String.valueOf(i+1));
				}
				
				return mapping.findForward("creation");
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

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
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;
import fr.assj.gestiontournoi.tournoi.Tournoi;

/**
 * 
 * @author tsutter
 * 
 * @struts.action path="/tournoi.modifier"
 * @struts.action-forward name="success" path="/tournoi.lister.do"
 *
 */
public class ModifierTournoi extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		try {
			connection = this.openDataBaseconnection(connection, request);
			Vector<Categorie> listeCategories = ListeCategories.getInstance().trouverListe();
			request.setAttribute("listeCategories", listeCategories);
			
			// recup des données du formulaire
			int codeTournoi = stringToInt(request.getParameter("codeTournoi"), -1);
			String libelle = stringToString(request.getParameter("libelle"), "");
			Date dateTournoi = stringToDate(request.getParameter("dateTournoi"), null);
			int categorie = stringToInt(request.getParameter("categorie"), -1);
			int typeTournoi = stringToInt(request.getParameter("typeTournoi"), -1);
			int nbEquipes = stringToInt(request.getParameter("nbEquipes"), -1);
			int nbTerrains = stringToInt(request.getParameter("nbTerrains"), -1);
			int dureeRencontre = stringToInt(request.getParameter("dureeRencontre"), -1);
			int baremeVictoire = stringToInt(request.getParameter("baremeVictoire"), -1);
			int baremeNul = stringToInt(request.getParameter("baremeNul"), -1);
			int baremeDefaite = stringToInt(request.getParameter("baremeDefaite"), -1);
			int nbGroupes = stringToInt(request.getParameter("nbGroupes"), -1);
			int nbEquipesQualifieesParGroupe = stringToInt(request.getParameter("nbEquipesQualifieesGroupe"), -1);
			int nbEquipesQualifiees = stringToInt(request.getParameter("nbEquipesQualifiees"), -1);
			boolean consolante = stringToBoolean(request.getParameter("consolante"), false);
			String description = stringToString(request.getParameter("description"), "");
			
			// création du tournoi + paramétrage
			// groupes, etc ...
			Tournoi tournoiAModifier = new Tournoi();
			tournoiAModifier.setId(codeTournoi);
			tournoiAModifier.setLibelle(libelle);
			tournoiAModifier.setDateTournoi(dateTournoi);
			tournoiAModifier.setStatut(1);
			tournoiAModifier.setCategorie(categorie);
			tournoiAModifier.setTypeTournoi(typeTournoi);
			tournoiAModifier.setNbEquipes(nbEquipes);
			tournoiAModifier.setNbTerrains(nbTerrains);
			tournoiAModifier.setDureeRencontre(dureeRencontre);
			tournoiAModifier.setBaremeVictoire(baremeVictoire);
			tournoiAModifier.setBaremeNul(baremeNul);
			tournoiAModifier.setBaremeDefaite(baremeDefaite);
			tournoiAModifier.setNbGroupes(nbGroupes);
			tournoiAModifier.setNbEquipesQualifieesParGroupe(nbEquipesQualifieesParGroupe);
			tournoiAModifier.setNbEquipesQualifiees(nbEquipesQualifiees);
			tournoiAModifier.setConsolante(consolante);
			tournoiAModifier.setDescription(description);
							
			ManagerTournoi.modifierTournoi(connection, tournoiAModifier);
			
//			// création à "vide" des groupes du tournoi
//			for (int i=0; i<nbGroupes; i++) {
//				ManagerGroupe.ajouterGroupeTournoi(connection, codeTournoi, "Groupe " + String.valueOf(i+1));
//			}
			
			request.setAttribute("tournoi", tournoiAModifier);
			request.setAttribute("mode", "visu");
				
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

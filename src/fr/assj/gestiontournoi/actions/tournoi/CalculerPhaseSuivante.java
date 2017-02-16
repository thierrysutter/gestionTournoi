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
import fr.assj.gestiontournoi.groupe.Groupe;
import fr.assj.gestiontournoi.groupe.ManagerGroupe;
import fr.assj.gestiontournoi.match.ManagerMatch;
import fr.assj.gestiontournoi.match.Match;
import fr.assj.gestiontournoi.tournoi.ManagerTournoi;
import fr.assj.gestiontournoi.tournoi.Tournoi;

/**
 * 
 * @author Thierry SUTTER
 * 
 * @struts.action path="/tournoi.suivant"
 * @struts.action-forward name="success" path="/tournoi.afficher2.do"
 *
 */
public class CalculerPhaseSuivante extends ActionBase {
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
			
			// recherche du tournoi
			Tournoi tournoi = ManagerTournoi.trouverTournoiParId(connection, codeTournoi);
			
			// recherche les groupes du tournois
			Vector<Groupe> groupesTournoi = ManagerGroupe.trouverGroupesEquipesTournoi(connection, codeTournoi);
			tournoi.setListeGroupes(groupesTournoi);
			
			
			int nbEquipesQualifiees = tournoi.getNbEquipesQualifiees();
			int nbEquipesConsolante = tournoi.getNbEquipes() - tournoi.getNbEquipesQualifiees();
			
			Vector<Equipe> listeEquipesQualifiees = new Vector<Equipe>(nbEquipesQualifiees);
			Vector<Equipe> listeEquipesConsolante = new Vector<Equipe>(nbEquipesConsolante);
			for (Groupe groupe : tournoi.getListeGroupes()) {
				for (Equipe equipe : groupe.getListeEquipes()) {
					if (equipe.getClassement() <= tournoi.getNbEquipesQualifieesParGroupe()) {
						listeEquipesQualifiees.add(equipe);
					} else {
						listeEquipesConsolante.add(equipe);
					}
				}
			}
			
			// phase "tournoi"
			int nbMatchs = nbEquipesQualifiees / 2;
			Vector<Match> listeMatchsTournoi = new Vector<Match>(nbMatchs);
			int cptMatch = 0;
			while (cptMatch < nbMatchs) {
				int random = (int)(Math.random() * listeEquipesQualifiees.size());
				Equipe equipe = listeEquipesQualifiees.elementAt(random);
				listeEquipesQualifiees.remove(random);
				boolean ok = false;
				while(!ok) {
					int random2 = (int)(Math.random() * listeEquipesQualifiees.size());
					Equipe equipe2 = listeEquipesQualifiees.elementAt(random2);
					
					if(equipe.getId() != equipe2.getId() && equipe.getClassement() != equipe2.getClassement() && !equipe.getGroupePhase1().getLibelle().equals(equipe2.getGroupePhase1().getLibelle())) {
						Match match = new Match();
						match.setCodeTournoi(codeTournoi);
						match.setEquipeLoc(equipe.getId());
						match.setEquipeLocale(equipe);
						match.setEquipeVis(equipe2.getId());
						match.setEquipeVisiteur(equipe2);
						match.setStatut(0);
						match.setGroupe("tournoi");
						match.setTour(1);
						
						int idMatch = ManagerMatch.ajouterMatchGroupe(connection, codeTournoi, "tournoi", equipe.getId(), equipe2.getId(), 1, tournoi.getDateTournoi(), "14h00", "");
						match.setId(idMatch);
						listeMatchsTournoi.add(match);
						cptMatch++;
						ok = true;
						listeEquipesQualifiees.remove(random2);
					} 
				}
			}
			
			// phase "consolante"
			int nbMatchsConsolante = nbEquipesConsolante / 2;
			Vector<Match> listeMatchsConsolante = new Vector<Match>(nbMatchsConsolante);
			int cptMatchConsolante = 0;
			while (cptMatchConsolante < nbMatchsConsolante) {
				int random = (int)(Math.random() * listeEquipesConsolante.size());
				Equipe equipe = listeEquipesConsolante.elementAt(random);
				listeEquipesConsolante.remove(random);
				
				boolean ok = false;
				while(!ok) {
					int random2 = (int)(Math.random() * listeEquipesConsolante.size());
					Equipe equipe2 = listeEquipesConsolante.elementAt(random2);
					
					if(equipe.getId() != equipe2.getId() && equipe.getClassement() != equipe2.getClassement() && !equipe.getGroupePhase1().getLibelle().equals(equipe2.getGroupePhase1().getLibelle())) {
						Match match = new Match();
						match.setCodeTournoi(codeTournoi);
						match.setEquipeLoc(equipe.getId());
						match.setEquipeLocale(equipe);
						match.setEquipeVis(equipe2.getId());
						match.setEquipeVisiteur(equipe2);
						match.setStatut(0);
						match.setGroupe("consolante");
						match.setTour(1);
						
						int idMatch = ManagerMatch.ajouterMatchGroupe(connection, codeTournoi, "consolante", equipe.getId(), equipe2.getId(), 1, tournoi.getDateTournoi(), "14h00", "");
						match.setId(idMatch);
						listeMatchsConsolante.add(match);
						cptMatchConsolante++;
						ok = true;
						listeEquipesConsolante.remove(random2);
					} 
				}
			}
			// changement de statut du tournoi
			ManagerTournoi.changerStatutTournoi(connection, codeTournoi, 4);
			
			request.setAttribute("tournoi", tournoi);
			request.setAttribute("rencontresFinales", listeMatchsTournoi);
			request.setAttribute("rencontresConsolante", listeMatchsConsolante);
			
//			// répartir les équipes dans les vecteurs en fonction de leur classements
//			// pour le tirage au sort de la phase elimination directe
//			// Il faut x vecteurs où x correspond au nombre d'equipes par groupe
//			Hashtable<Integer, Vector<Equipe>> chapeaux = new Hashtable<Integer, Vector<Equipe>>();
//			int nbEquipesParGroupe = tournoi.getNbEquipes()/tournoi.getNbGroupes();
//			for (int i=1; i<=nbEquipesParGroupe; i++) {
//				Vector<Equipe> chapeau = new Vector<Equipe>();
//				chapeaux.put(i, chapeau);
//			}
//			
//			// remplissage des vecteurs
//			// on boucle sur groupes du tournois puis sur la liste des équipes de chaque groupe
//			for (Groupe groupe : groupesTournoi) {
//				for (Equipe equipe : groupe.getListeEquipes()) {
//					chapeaux.get(equipe.getClassement()).add(equipe);
//				}
//			}
//			
//			// tirage au sort des matchs
//			// nombre d'equipes qualifiées par groupes
//			Vector<Match> rencontresFinales = new Vector<Match>();
////			for (int j=1; j<=tournoi.getNbEquipesQualifieesParGroupe(); j++) {
////			}
//			int nbRencontres = tournoi.getNbEquipesQualifiees()/2;
//			int compteur = 0;
//			while(compteur<nbRencontres) {
//				int random1 = (int)(Math.random() * chapeaux.get(1).size());
//				int random2 = (int)(Math.random() * chapeaux.get(2).size());
//				Equipe eq1 = chapeaux.get(1).elementAt(random1);
//				Equipe eq2 = chapeaux.get(2).elementAt(random2);
//				Match m = new Match();
//				m.setCodeTournoi(codeTournoi);
//				//m.setGroupe(groupe);
//				//m.setHoraire();
//				m.setJour(tournoi.getDateTournoi());
//				m.setLieu(tournoi.getLieu());
//				//m.setTour(1);
//				m.setEquipeLoc(eq1.getId());
//				m.setEquipeLocale(eq1);
//				m.setEquipeVis(eq2.getId());
//				m.setEquipeVisiteur(eq2);
//				m.setStatut(0);
//				rencontresFinales.add(m);
//				
//				chapeaux.get(1).remove(random1);
//				chapeaux.get(2).remove(random2);
//				compteur++;
//			}
//			
//			Vector<Match> rencontresConsolante = new Vector<Match>();
//			if (tournoi.isConsolante()) {
//				nbRencontres = tournoi.getNbEquipesQualifiees()/2;
//				compteur = 0;
//				while(compteur<nbRencontres) {
//					int random1 = (int)(Math.random() * chapeaux.get(3).size());
//					int random2 = (int)(Math.random() * chapeaux.get(4).size());
//					Equipe eq1 = chapeaux.get(3).elementAt(random1);
//					Equipe eq2 = chapeaux.get(4).elementAt(random2);
//					Match m = new Match();
//					m.setCodeTournoi(codeTournoi);
//					//m.setGroupe(groupe);
//					//m.setHoraire(tournoi.get);
//					m.setJour(tournoi.getDateTournoi());
//					m.setLieu(tournoi.getLieu());
//					//m.setTour(1);
//					m.setEquipeLoc(eq1.getId());
//					m.setEquipeLocale(eq1);
//					m.setEquipeVis(eq2.getId());
//					m.setEquipeVisiteur(eq2);
//					m.setStatut(0);
//					rencontresConsolante.add(m);
//					chapeaux.get(3).remove(random1);
//					chapeaux.get(4).remove(random2);
//					compteur++;
//				}
//			}
//			
//			// enregistrement des matchs en base
////			ManagerMatch.ajouterMatchs(connection, rencontresFinales);
////			ManagerMatch.ajouterMatchs(connection, rencontresConsolante);
//			
//			request.setAttribute("tournoi", tournoi);
//			request.setAttribute("rencontresFinales", rencontresFinales);
//			request.setAttribute("rencontresConsolante", rencontresConsolante);
						
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


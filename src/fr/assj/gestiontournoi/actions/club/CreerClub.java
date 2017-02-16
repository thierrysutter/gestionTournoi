package fr.assj.gestiontournoi.actions.club;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.assj.gestiontournoi.actions.ActionBase;
import fr.assj.gestiontournoi.club.ManagerClub;

/**
 * 
 * @author tsutter
 *
 * @struts.action path="/clubs.creer"
 * @struts.action-forward name="liste" path="/clubs.do"
 * @struts.action-forward name="success" path="/club/creationClub.jsp"
 */
public class CreerClub extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		try {
			boolean submit = stringToBoolean(request.getParameter("sub"), false);
			
			if (submit) {
				connection = this.openDataBaseconnection(connection, request);
				
				String nomClub = stringToString(request.getParameter("nom"), "");
				String ligue = stringToString(request.getParameter("ligue"), "");
				String district = stringToString(request.getParameter("district"), "");
				String numAffiliation = stringToString(request.getParameter("numAffiliation"), "");
				String adresse = stringToString(request.getParameter("adresse"), "");
				String codePostal = stringToString(request.getParameter("codePostal"), "");
				String ville = stringToString(request.getParameter("ville"), "");
				String pays = stringToString(request.getParameter("pays"), "");
				String tel = stringToString(request.getParameter("tel1"), "");
				String fax = stringToString(request.getParameter("fax1"), "");
				String email = stringToString(request.getParameter("email1"), "");
				String siteWeb = stringToString(request.getParameter("siteWeb"), "");
				
				ManagerClub.ajouterClub(connection, nomClub, ligue, district, numAffiliation, adresse, codePostal, ville, pays, tel, fax, email, siteWeb);
				
				return mapping.findForward("liste");
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

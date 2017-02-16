package fr.assj.gestiontournoi.actions.club;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.assj.gestiontournoi.actions.ActionBase;
import fr.assj.gestiontournoi.club.Club;
import fr.assj.gestiontournoi.club.ManagerClub;
import fr.assj.gestiontournoi.contact.Contact;
import fr.assj.gestiontournoi.contact.ManagerContact;

/**
 * 
 * @author tsutter
 *
 * @struts.action path="/clubs.afficher"
 * @struts.action-forward name="success" path="/club/ficheClub.jsp"
 */
public class AfficherClub extends ActionBase {
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		this.logger.debug(this.log(request));
		Connection connection = null;
		
		try {
			connection = this.openDataBaseconnection(connection, request);
			int idClub = stringToInt(request.getParameter("idClub"), -1);
			
			Club club = ManagerClub.trouverClubParId(connection, idClub);
			
			Vector<Contact> listeContacts = ManagerContact.trouverContactsPourClub(connection, idClub);
			club.setListeContacts(listeContacts);
			
			request.setAttribute("club", club);
			
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

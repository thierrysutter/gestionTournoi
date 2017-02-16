package fr.assj.gestiontournoi.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import fr.assj.gestiontournoi.commun.Format;

/**
 * <p>Titre : TronquerChaineTag</p>
 * <p>Description : affiche le contenu de la taglib en "capitales"
 * (le 1er caractère en majuscule pour les suivants en en minuscule)</p>
 * 
 * Tronque une chaine de caractères apres dernier mot ne depassant pas la taille maxi autorisée
 * et accompagnée de "..." avec une infobulle affichant la chaine complete
 * (si le 1er mot fait plus que la taille autorisée alors le mot est tronqué)
 * 
 * Param obligatoire:
 * - max : le nb maxi de caractères à afficher
 * 
 * Param optionnel:
 * - acronyme : indique s'il faut affichier les "..."
 * 
 * @author Thierry SUTTER
 * @version 1.0
 */
public class TronquerChaineTag extends BodyTagSupport {


	/**
	 * Attribut nécessaire car les BoCI sont Serializable
	 */
	private static final long serialVersionUID = -4691632729819161233L;

	/**
	 * Le nombre de décimales après la virgule à afficher
	 */
	private int max = 0;

	/**
	 * indique si la chaine tronquée est suivi de ... + chaine complete en acronyme
	 */
	private boolean acronyme = false;

	/**
	 * 
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		//return EVAL_BODY_TAG;
		return EVAL_BODY_BUFFERED;
	}

	/**
	 * 
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {

		try {
			if ( this.getBodyContent()!=null && this.getBodyContent().getString().length() > 0) {
				String bodyString = this.getBodyContent().getString();

				String chaineTronquee = Format.formateChaine(bodyString, this.max, false, this.acronyme);

				this.getBodyContent().getEnclosingWriter().println( chaineTronquee );
			}
		} catch (IOException e) {
			throw new JspException (e);
		}

		return EVAL_PAGE;
	}

	/**
	 * Affecte la nouvelle valeur à l'attribut max.
	 *
	 * @param max la nouvelle valeur de l'attribut max.
	 * @see TronquerChaineTag#max
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * Affecte la nouvelle valeur à l'attribut acronyme.
	 *
	 * @param acronyme la nouvelle valeur de l'attribut acronyme.
	 * @see TronquerChaineTag#acronyme
	 */
	public void setAcronyme(boolean acronyme) {
		this.acronyme = acronyme;
	}
}

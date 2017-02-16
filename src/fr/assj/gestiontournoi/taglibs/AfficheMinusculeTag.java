package fr.assj.gestiontournoi.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <p>Titre : AfficheMinusculeTag</p>
 * <p>Description : affiche le contenu de la taglib en "capitales"
 * (le 1er caractère en majuscule pour les suivants en en minuscule)</p>
 * 
 * - param obligatoire: aucun
 * - params optionnels: aucun
 * 
 * @author Thierry SUTTER
 * @version 1.0
 */
public class AfficheMinusculeTag extends BodyTagSupport {


	/**
	 * Attribut nécessaire car les BoCI sont Serializable
	 */
	private static final long serialVersionUID = -3588800726425196189L;

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
				bodyString = bodyString.substring(0,1).toUpperCase() + bodyString.substring(1).toLowerCase();
				this.getBodyContent().getEnclosingWriter().println( bodyString );
			}
		} catch (IOException e) {
			throw new JspException (e);
		}

		return EVAL_PAGE;
	}
}

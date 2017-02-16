package fr.assj.gestiontournoi.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * FormateNombreTag :
 * Arrondis le nombre présent dans la taglib suivant un nombre de décimale souhaitée.
 * Si le nombre fourni est entiter alors il est possible de n'afficher aucune décimale avec le paramètre fixe=true.
 * La virgule (séparateur entre la partie entière et décimale) est remplacée par un point (pour que le résultat
 * soit interprété comme un nombre en Javascript)
 * 
 * paramètres obligatoires: aucun
 * paramètres facultatifs :
 * > decimale : le nombre de décimales après la virgule à afficher
 * > fixe : indique si le nb de décimales à afficher est fixe ou si aucune décimale n'est affichée si le nombre fourni est entier
 * 
 * ex0 : le nombre 1234,517 sans attribut "decimale" renseigné retournera 1235
 * ex1 : le nombre 1234,5173 avec decimale=2 retournera 1234.52
 * ex2 : le nombre 1234,000 avec decimale=2 et fixe=true retournera 1234
 * ex3 : le nombre 1234,5173 avec decimale=2 et separateur=true retournera 1 234.52 (non compatible pour être interprété comme un nombre en javascript)
 * 
 * @author Thierry SUTTER
 * @version 1.0
 */
public class ArrondirNombreTag extends BodyTagSupport {


	/**
	 * Attribut nécessaire car les BoCI sont Serializable
	 */
	private static final long serialVersionUID = -3588800726425196189L;

	/**
	 * Le nombre de décimales après la virgule à afficher
	 */
	private int decimale = 0;

	/**
	 * Indique si le nb de décimales à afficher est fixe ou si aucune décimale n'est affichée si le nombre fourni
	 * est entier
	 */
	private boolean fixe = true;

	/**
	 * Indique s'il faut ajouter un espace comme séparateur des milliers
	 */
	private boolean separateur = false;

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

				// conversion du nombre contenu de la taglib de String en decimal
				double nombre = (new Double(bodyString)).doubleValue();

				// le nombre est entier si son arrondi à l'entier inférieur = arrondi à l'entier supérieur
				boolean isEntier = (Math.ceil(nombre) == Math.floor(nombre));

				// arrondit le nombre à "nbDecimale" chiffres après la virgule
				// sauf si le nombre est entier et que le nb de décimales demandées n'est pas fixe
				if (!isEntier && this.decimale > 0) {
					double arrondi = 1.0;
					for (int i = 0; i < this.decimale; i++) {
						arrondi *= 10;
					}
					nombre = (java.lang.Math.round(nombre * arrondi)) / arrondi;
				}

				// definition de la classe de formattage à partir du nombre de décimales à afficher
				String pattern = "0"; // affichage sans chiffre après la virgule si le nombre est entier
				// determination du nb de chiffres après la virgule à partir du pattern (ex : 0#000.00 => 2)
				if ((!isEntier || this.fixe) && this.decimale > 0) {
					pattern = "0.";
					for (int i=0; i<this.decimale; i++) {
						pattern += "0"; // on ajoute au formattage un chiffre après la virgule pour chaque décimale demandée
					}
				}

				// si les séparateurs de milliers sont demandés on adapte le modele de formattage
				// (max géré pour les séparateurs : 999 999 999)
				if (this.separateur) {
					pattern = "###,###,##" + pattern;
				}

				// conversion du nombre (double) en String suivant le modèle constitué ci dessus
				// et remplacement de la "," par un "." (pour que le résultat soit interprété comme un nombre en Javascript)
				java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);
				this.getBodyContent().getEnclosingWriter().print(df.format(nombre).replace(',', '.'));
			}
		} catch (IOException e) {
			throw new JspException (e);
		}

		return EVAL_PAGE;
	}

	/**
	 * Affecte la nouvelle valeur à l'attribut nbDecimale.
	 *
	 * @param decimale la nouvelle valeur de l'attribut nbDecimale.
	 * @see ArrondirNombreTag#decimale
	 */
	public void setDecimale(int decimale) {
		this.decimale = decimale;
	}

	/**
	 * Affecte la nouvelle valeur à l'attribut fixe.
	 *
	 * @param fixe la nouvelle valeur de l'attribut fixe.
	 * @see ArrondirNombreTag#fixe
	 */
	public void setFixe(boolean fixe) {
		this.fixe = fixe;
	}

	/**
	 * Affecte la nouvelle valeur à l'attribut separateur.
	 *
	 * @param separateur la nouvelle valeur de l'attribut fixe.
	 * @see ArrondirNombreTag#separateur
	 */
	public void setSeparateur(boolean separateur) {
		this.separateur = separateur;
	}
}

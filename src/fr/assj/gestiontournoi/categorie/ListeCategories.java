package fr.assj.gestiontournoi.categorie;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class ListeCategories {
	/**
	  * L'instance statique.
	 */
	private static ListeCategories instance = new ListeCategories();
	  
	/**
	 * Récupère l'instance unique de la class ListeCategories.
	 * 
	 * @return L'instance de la classe ListeCategories.
	 */

	public static ListeCategories getInstance() {
		return ListeCategories.instance;
	}
	
	/**
	 * La liste des catégories.
	 */
	private final Collection<Categorie> listeCategories;
	
	/**
	 * Constructeur redéfini comme étant privé pour interdire son appel et forcer
	 * à passer par la méthode getInstance.
	 */
	private ListeCategories() {
	    this.listeCategories = new Vector<Categorie>();
	  }

	  /**
	   * Ajoute une catégorie à la liste des catégories.
	   * 
	   * @param categorie La catégorie à ajouter.
	   */
	  public void ajouterCategorie(final Categorie categorie) {
	    this.listeCategories.add(categorie);
	  }

	  /**
	   * Réinitialise la liste des Categories.
	   */
	  public void reinitialiser() {
	    this.listeCategories.clear();
	  }

	  /**
	   * Recherche une catégorie par son code.
	   * 
	   * @param code Le code recherché.
	   * @return La catégorie.
	   */
	  public Categorie trouverParCode(int code) {
	    // Code null == pas de rayon
	    if (0 == code) {
	      return null;
	    }

	    //code = code.trim();

	    // Recherche de rayon dans la liste
	    final Iterator<Categorie> itrCategorie = this.listeCategories.iterator();
	    while (itrCategorie.hasNext()) {
	      final Categorie categorie = itrCategorie.next();

	      if (code == categorie.getId()) {
	        return categorie;
	      }
	    }

	    // Rayon non trouvé
	    return null;
	  }
	  
	  public Vector<Categorie> trouverListe() {
		  Vector<Categorie> result = new Vector<Categorie>();
		  result.addAll(this.listeCategories);
		  return result;
	  }
}

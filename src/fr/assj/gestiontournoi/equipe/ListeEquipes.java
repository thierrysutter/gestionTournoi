package fr.assj.gestiontournoi.equipe;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


public class ListeEquipes {
	/**
	  * L'instance statique.
	 */
	private static ListeEquipes instance = new ListeEquipes();
	  
	/**
	 * Récupère l'instance unique de la class ListeEquipes.
	 * 
	 * @return L'instance de la classe ListeEquipes.
	 */

	public static ListeEquipes getInstance() {
		return ListeEquipes.instance;
	}
	
	/**
	 * La liste des équipes.
	 */
	private final Collection<Equipe> listeEquipes;
	
	/**
	 * Constructeur redéfini comme étant privé pour interdire son appel et forcer
	 * à passer par la méthode getInstance.
	 */
	private ListeEquipes() {
		this.listeEquipes = new Vector<Equipe>();
	}
	
	/**
	 * Ajoute une équipe à la liste des équipes.
	 * 
	 * @param equipe L'équipe à ajouter.
	 */
	public void ajouterEquipe(final Equipe equipe) {
		this.listeEquipes.add(equipe);
	}

	/**
	 * Réinitialise la liste des Equipes.
	 */
	public void reinitialiser() {
		this.listeEquipes.clear();
	}
	
	/**
	   * Recherche une catégorie par son code.
	   * 
	   * @param code Le code recherché.
	   * @return La catégorie.
	   */
	  public Equipe trouverParCode(int code) {
	    // Code null == pas de rayon
	    if (0 == code) {
	      return null;
	    }

	    //code = code.trim();

	    // Recherche d'une equipe dans la liste
	    final Iterator<Equipe> itrEquipe = this.listeEquipes.iterator();
	    while (itrEquipe.hasNext()) {
	      final Equipe equipe = itrEquipe.next();

	      if (code == equipe.getId()) {
	        return equipe;
	      }
	    }

	    // Equipe non trouvé
	    return null;
	  }
	  
	  public Vector<Equipe> trouverListe() {
		  Vector<Equipe> result = new Vector<Equipe>();
		  result.addAll(this.listeEquipes);
		  return result;
	  }
}

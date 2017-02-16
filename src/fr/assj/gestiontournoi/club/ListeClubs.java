package fr.assj.gestiontournoi.club;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


public class ListeClubs {
	/**
	  * L'instance statique.
	 */
	private static ListeClubs instance = new ListeClubs();
	  
	/**
	 * Récupère l'instance unique de la class ListeClubs.
	 * 
	 * @return L'instance de la classe ListeClubs.
	 */

	public static ListeClubs getInstance() {
		return ListeClubs.instance;
	}
	
	/**
	 * La liste des équipes.
	 */
	private final Collection<Club> listeClubs;
	
	/**
	 * Constructeur redéfini comme étant privé pour interdire son appel et forcer
	 * à passer par la méthode getInstance.
	 */
	private ListeClubs() {
		this.listeClubs = new Vector<Club>();
	}
	
	/**
	 * Ajoute un club à la liste des clubs.
	 * 
	 * @param club Le club à ajouter.
	 */
	public void ajouterClub(final Club club) {
		this.listeClubs.add(club);
	}

	/**
	 * Réinitialise la liste des Clubs.
	 */
	public void reinitialiser() {
		this.listeClubs.clear();
	}
	
	/**
	   * Recherche une catégorie par son code.
	   * 
	   * @param code Le code recherché.
	   * @return La catégorie.
	   */
	  public Club trouverParCode(int code) {
	    // Code null == pas de rayon
	    if (0 == code) {
	      return null;
	    }

	    //code = code.trim();

	    // Recherche d'une Club dans la liste
	    final Iterator<Club> itrClub = this.listeClubs.iterator();
	    while (itrClub.hasNext()) {
	      final Club club = itrClub.next();

	      if (code == club.getId()) {
	        return club;
	      }
	    }

	    // Club non trouvé
	    return null;
	  }
	  
	  public Vector<Club> trouverListe() {
		  Vector<Club> result = new Vector<Club>();
		  result.addAll(this.listeClubs);
		  return result;
	  }
}

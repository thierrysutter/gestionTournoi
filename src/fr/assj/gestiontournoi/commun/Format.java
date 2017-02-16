package fr.assj.gestiontournoi.commun;

import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Format {
	/** *************************** FORMATS *************************************** */

	  /**
	   * convertit d'une date au format Date en un String de la forme jj/mm/aa
	   * 
	   * @param dDate
	   * @return date convertie au format jj/mm/aa
	   */
	  public static String toDate(Date dDate)
	  {
	    if (dDate == null)
	      return "";
	    java.text.SimpleDateFormat sdf;
	    sdf = new java.text.SimpleDateFormat("dd/MM/yy");
	    return sdf.format(dDate);
	  }

	  /**
	   * convertit d'une date au format Date en un String de la forme jj/mm/aaaa
	   * 
	   * @param dDate
	   * @return date convertie au format jj/mm/aaaa
	   */
	  public static String toLongDate(Date dDate)
	  {
	    if (dDate == null)
	      return "";
	    java.text.SimpleDateFormat sdf;
	    sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	    return sdf.format(dDate);
	  }

	  /**
	   * convertit d'une date au format Date en un String de la forme jj/mm/aaaa hh:mm
	   * 
	   * @param dDate
	   * @return date convertie au format jj/mm/aaaa hh:mm
	   */
	  public static String toDateTime(Date dDate)
	  {
	    if (dDate == null)
	      return "";
	    java.text.SimpleDateFormat sdf;
	    sdf = new java.text.SimpleDateFormat("dd/MM/yyyy - kk:mm");
	    return sdf.format(dDate);
	  }

	  /**
	   *  convertit d'une date au format Date en un String de la forme demandée
	   * @param dDate
	   * @param pattern
	   * @return date convertie au format demandé
	   */
	  public static String toDate(Date dDate, String pattern)
	  {
	    if (dDate == null || pattern == null)
	      return "";
	    java.text.SimpleDateFormat sdf;
	    sdf = new java.text.SimpleDateFormat(pattern);
	    return sdf.format(dDate);
	  }

	  /**
	   * convertit d'une date au format Date en un String de la forme mm/aaaa
	   * @param dDate
	   * @return date convertie au format mm/aaaa
	   */
	  public static String toMoisAnnee(Date dDate)
	  {
	    return toDate(dDate, "MM/yyyy");
	  }

	  /**
	   * convertit d'une date au format Date en un String de la forme mm/aa
	   * @param dDate
	   * @return date convertie au format mm/aa
	   */
	  public static String toSpecDateSurAnnee(Date dDate)
	  {
	    return toDate(dDate, "MM/yy");
	  }

	  /**
	   * convertit une chaine de caracteres de la forme aaaa-mm-jj en format Date
	   * @param sqlDate
	   * @return date au format "java"
	   */
	  public static Date fromSQLtoDate(String sqlDate)
	  {
	    if (sqlDate == null || sqlDate.length() != 10)
	      return null;
	    Calendar c = Calendar.getInstance();
	    int year = Integer.parseInt(sqlDate.substring(0, 4));
	    int month = Integer.parseInt(sqlDate.substring(5, 7)) - 1; // janvier = mois n°0 dans l'objet Calendar
	    int day = Integer.parseInt(sqlDate.substring(8, 10));
	    c.set(year, month, day, 0, 0, 0);
	    return c.getTime();
	  }

	  /**
	   * convertit une valeur de type double en un nombre a 2 chiffres apres la virgule
	   * @param nombre
	   * @return nombre formaté avec 2 chiffres apres la virgule
	   */
	  public static String formatPrix(double nombre)
	  {
	  	return formatPrix(arrondir(nombre, 2), "0.00");
	  }
	  
	  /**
	   * convertit une valeur de type double suivant un certain format 
	   * @param nombre
	   * @param pattern
	   * @return nombre formaté 
	   */
	  public static String formatPrix(double nombre, String pattern)
	  {
	    java.text.DecimalFormat df;
	    df = new java.text.DecimalFormat(pattern);
	    
	    // determination du nb de chiffres après la virgule (ex : 0#000.00 => 2)
	    int nbDecimales = 0;
	    if (pattern.indexOf(".") != -1) {
	    	nbDecimales = pattern.length() - pattern.indexOf(".") - 1;
	    }
	    
	    return df.format(arrondir(nombre, nbDecimales)).replace(',', '.');
	  }

	  /**
	   * convertit une valeur de type double en un nombre a 3 chiffres apres la virgule
	   * @param nombre
	   * @return nombre formaté avec 3 chiffres apres la virgule
	   */
	  public static String formatLongPrix(double nombre)
	  {
	    java.text.DecimalFormat df;
	    df = new java.text.DecimalFormat("0.000");
	    return df.format(arrondir(nombre, 3)).replace(',', '.');
	  }
	  
	  /**
	   * convertit un chiffre decimal en entier
	   * @param nombre
	   * @return entier
	   */
	  public static String formatInt(double nombre)
	  {
	    java.text.DecimalFormat df;
	    df = new java.text.DecimalFormat("0");
	    return df.format(arrondir(nombre,0));
	  }

	  /**
	   * convertit un chiffre decimal en entier
	   * @param nombre
	   * @return entier
	   */
	  public static String formatNoDecimal(double nombre)
	  {
	    return formatInt(nombre);
	  }

	  /**
	   * Format un nombre decimal avec un séparateur de millier
	   * @param nombre
	   * @param sep
	   * @return nombre formatté avec un séparateur de millier
	   */
	  public static String formatDblSepMilliers(double nombre, char sep)
	  {
	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	    dfs.setGroupingSeparator(sep);
	    java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0", dfs);

	    return df.format(arrondir(nombre,0));
	  }

	  /**
	   * Format un nombre entier avec un séparateur de millier
	   * @param nombre
	   * @param sep
	   * @return nombre formatté avec un séparateur de millier
	   */
	  public static String formatIntSepMilliers(int nombre, char sep)
	  {
	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	    dfs.setGroupingSeparator(sep);
	    java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0", dfs);

	    return df.format(arrondir(nombre,0));
	  }
	  
	  /**
	   * convertit une valeur de type float en un nombre a 2 chiffres apres la virgule
	   * @param nombre
	   * @return nombre formaté avec 2 chiffres apres la virgule
	   */
	  public static String formatPrix(float nombre)
	  {
	  	return formatPrix(arrondir(nombre, 2), "0.00");
	  }
	  
	  /**
	   * convertit une valeur de type float suivant un certain format 
	   * @param nombre
	   * @param pattern
	   * @return nombre formaté 
	   */
	  public static String formatPrix(float nombre, String pattern)
	  {
	    java.text.DecimalFormat df;
	    df = new java.text.DecimalFormat(pattern);
	    
	    // determination du nb de chiffres après la virgule (ex : 0#000.00 => 2)
	    int nbDecimales = 0;
	    if (pattern.indexOf(".") != -1) {
	    	nbDecimales = pattern.length() - pattern.indexOf(".") - 1;
	    }
	    
	    return df.format(arrondir(nombre, nbDecimales)).replace(',', '.');
	  }
	  
	  /**
	   * convertit une valeur de type float en un nombre a 3 chiffres apres la virgule
	   * @param nombre
	   * @return nombre formaté avec 3 chiffres apres la virgule
	   */
	  public static String formatLongPrix(float nombre)
	  {
	    java.text.DecimalFormat df;
	    df = new java.text.DecimalFormat("0.000");
	    return df.format(arrondir(nombre, 3)).replace(',', '.');
	  }

	  /**
	   * convertit un float a l'entier (arrondis au plus proche) 
	   * @param nombre
	   * @return entier
	   */
	  public static String formatInt(float nombre)
	  {
	    java.text.DecimalFormat df;
	    df = new java.text.DecimalFormat("0");
	    return df.format(arrondir(nombre,0));
	  }

	  /**
	   * convertit un float a l'entier (arrondis au plus proche) 
	   * @param nombre
	   * @return entier
	   */
	  public static String formatNoDecimal(float nombre)
	  {
	    return formatInt(nombre);
	  }

	  /**
	   * Format un nombre float avec un séparateur de millier
	   * @param nombre
	   * @param sep
	   * @return nombre formatté avec un séparateur de millier
	   */
	  public static String formatDblSepMilliers(float nombre, char sep)
	  {
	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	    dfs.setGroupingSeparator(sep);
	    java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0", dfs);

	    return df.format(arrondir(nombre,0));
	  }

	  

	  /** ************************ AFFICHAGE MULTI TYPE ***************************** */

	  /**
	   * @param valeur
	   * @return "" a la place de null si la valeur est null
	   */
	  public static String display(String valeur)
	  {
	    if (valeur == null)
	      return "";
	    else
	      return valeur;
	  }

	  /**
	   * @param valeur
	   * @return "oui" si vrai, sinon "non"
	   */
	  public static String display(boolean valeur)
	  {
	    if (valeur)
	      return "oui";
	    else
	      return "non";
	  }

	  /**
	   * @param valeur
	   * @return entier converti en String
	   */
	  public static String display(int valeur)
	  {
	    return String.valueOf(valeur);
	  }

	  /**
	   * remplace le caractere ',' d'un nombre de type double par '.'
	   * @param field
	   * @return chaine convertie
	   */
	  public static String display(double field)
	  {
	    return formatPrix(field).replace(',', '.');
	  }
	  
	  /**
	   * remplace le caractere ',' d'un nombre de type float par '.'
	   * @param field
	   * @return chaine convertie
	   */
	  public static String display(float field)
	  {
	    return formatPrix(field).replace(',', '.');
	  }

	  /**
	   * affiche uniquement le libelle en retirant le code s'il est present en debut de chaine
	   * ex: displayLibOnly("code - libelle") => "libelle"
	   * @param code
	   * @param libel
	   * @return code + libelle "nettoyé"
	   */
	  public static String displayLibOnly(String code, String libel)
	  {
	    if (code == null || libel == null)
	      return "";
	    if (libel.length() < code.length()
	        || !libel.substring(0, code.length()).equals(code))
	      return libel;

	    libel = libel.substring(code.length());
	    int count = 0;
	    for (int i = 0; i < libel.length(); i++)
	    {
	      if (libel.charAt(i) == ' ' || libel.charAt(i) == '-')
	        count++;
	      else
	        break;
	    }
	    return libel.substring(count);
	  }

	  /**
	   * retourne une chaine de caractere de la forme "code - libelle"
	   * (n'affiche pas 2 fois le code si le code est inclus dans le libelle)
	   * @param code
	   * @param libel
	   * @return code + libelle "nettoyé"
	   */
	  public static String displayCodeLib(String code, String libel)
	  {
	      if (code==null || libel==null)
	          return "";
	      if (libel.length()>code.length() && libel.substring(0,code.length()).equals(code))
	          return libel;
	      return code + " - " + libel;
	  }

	  
	  /**
	   * convertit une chaine de caractere en minuscule (seul le 1er caractre est forcé en majuscule
	   * @param chaine
	   * @return chaine en capital
	   */
	  public static String enMinuscule(String chaine)
	  { 
	  	if (chaine == null || chaine.length() == 0) 
	  	{
	  		return "";
	  	}
	    return chaine.substring(0,1).toUpperCase() + chaine.substring(1).toLowerCase();
	  }
	  
	  /**
	   * Retourne une chaine de caractere pouvant etre : 
	   * - formatée en minuscule (le 1er caractère reste en majuscule)
	   * - tronquée apres dernier mot ne depassant pas la taille maxi autorisée
	   *   et accompagnée de "..." avec une infobulle affichant la chaine complete
	   *   (si le 1er mot fait plus que la taille autorisée alors le mot est tronqué)
	   * 
	   * Attention!! utiliser l'option "filter=false" avec les taglib bean:write si avecAcronyme
	   * 
	   * @param chaine : chaine de caractere a traiter
	   * @param max : nombre maximal de caracteres autorisés
	   * @param enMinuscule : indique si la chaine doit etre forcée en minuscule
	   * @param avecAcronyme : indique si la chaine tronquée est suivi de ... + chaine complete en acronyme
	   * @return chaine formatée
	   */
	  public static String formateChaine(String chaine, int max, boolean enMinuscule, boolean avecAcronyme) {
	    if (null == chaine) {
	    	return "";
	    } else if (chaine.length() <= max || max == 0) {
	    	return enMinuscule ? Format.enMinuscule(chaine) : chaine;
	    } else {
	      StringBuffer debutChaine = new StringBuffer(chaine.length());
	      String[] mot = chaine.split("\\s");

	      for (int i = 0; i < mot.length; i++)
	      {
	        if (debutChaine.length() + mot[i].length() + 1 <= max)
	        {
	        	debutChaine.append(mot[i]).append(" ");
	        }
	        else 
	        	break;
	      }

	      // Au cas où le premier mot fait plus que le nb max de caracteres
	      if (debutChaine.length() == 0)
	      {
	      	debutChaine.append(chaine.substring(0, max));
	      }

	      String acronym = !avecAcronyme ? "" : "<acronym style=\"cursor: help; text-decoration: underline;\" title=\"" + (enMinuscule ? Format.enMinuscule(chaine.replace('"', ' ')) : chaine.replace('"', ' ')) + "\">...</acronym>"; 
	      	
	      return (enMinuscule ? Format.enMinuscule(debutChaine.toString()) : debutChaine.toString()) +  acronym;
	    }
	  }

	  /** ******************************* DIVERS ************************************ */

	  /**
	   * convertit une chaine de caractere de la forme d'un chemin d'acces au format html
	   * @param text
	   * @return chaine transcodée
	   */
	  public static String html(String text)
	  {
	    if (text == null)
	      return "";
	    StringBuffer sb = new StringBuffer(text.length() * 2);
	    char c;
	    for (int i = 0; i < text.length(); i++)
	    {
	      c = text.charAt(i);
	      if (c == '\'')
	        sb.append("&acute;");
	      else if (c == '\"')
	        sb.append("&quot;");
	      else if (c == '\n')
	      {
	        // on ne fait rien
	      }
	      else if (c == '\r' && text.charAt(i + 1) == '\n')
	        sb.append("<br />");
	      else
	        sb.append(c);
	    }
	    return sb.toString();
	  }

	  /**
	   * retourne le 1er chiffre d'un nombre
	   * @param number
	   * @return le 1er chiffre d'un nombre
	   */
	  public static int getFirstNumber(int number)
	  {
	    return Integer.parseInt(String.valueOf(number).substring(0, 1));
	  }	  
	  
	  /**
	   * Decoupe une chaine de caractère a chaque espace mais sans séparer les mots
	   * contenue entre quote.
	   * 
	   * @param chaineDeRecherche La chaine à decouper.
	   * @return Un vecter contenant la liste des mots.
	   */
	  public static Vector<String> decoupeParMot(String chaineDeRecherche)
	  {
	    Vector<String> tableauResultat = new Vector<String>();
	    if (chaineDeRecherche == null || chaineDeRecherche.length() == 0) {
	      return tableauResultat;
	    }

	    boolean entreQuote = false;
	    StringBuffer motCourrant = new StringBuffer(chaineDeRecherche.length());

	    for (int i = 0; i < chaineDeRecherche.length(); i++)
	    {
	      if (String.valueOf(chaineDeRecherche.charAt(i)).matches("\""))
	      {
	        entreQuote = !entreQuote;
	      }
	      else if (String.valueOf(chaineDeRecherche.charAt(i)).matches("\\s")
	          && !entreQuote)
	      {
	        if (motCourrant.length() > 0)
	        {
	          tableauResultat.add(motCourrant.toString().trim());
	        }
	        motCourrant = new StringBuffer(chaineDeRecherche.length());
	      }
	      else
	      {
	        motCourrant.append(chaineDeRecherche.charAt(i));
	      }

	    }

	    if (motCourrant.toString().trim().length() > 0)
	    {
	      tableauResultat.add(motCourrant.toString().trim());
	    }

	    return tableauResultat;
	  }
	  
	  /**
	   * @param nbCar
	   * @param chaine
	   * @return la chaine tronquée au nb de caractères
	   */
	  public static String tronqueChaine(int nbCar, String chaine)
	  {
	    if (chaine == null) {
	        return "";
	    }
	    if (chaine.length() > nbCar) {
	      chaine = chaine.substring(0, nbCar - 1);
	    }
	    return chaine;
	  }

	  /**
	   * Decoupe une chaine de caracteres en un vecteur 'nbLigne' de chaine de
	   * 'tailleLigne' caracteres.
	   * 
	   * @param nbLigne int Nombre maximum de ligne.
	   * @param tailleLigne int Taille maximum des lignes.
	   * @param phrase
	   * @return Vector Les lignes decoupées.
	   */
	  public static Vector<String> decoupeChaine(int nbLigne, int tailleLigne, String phrase)
	  {
	    // chaque mot de la phrase dans une cellule du tableau
	    String[] mot = phrase.trim().split(" ");
	    Vector<String> result = new Vector<String>();
	    if (mot[0].length() > tailleLigne)
	    {
	      // Le premier mot de la phrase est plus long que la taille maxin de la
	      // ligne
	      result.add(mot[0].substring(0, tailleLigne));
	    }
	    else
	    {
	      result.add(mot[0]);
	    }

	    // Pour tout les autres mots de la phrase
	    for (int i = 1; i < mot.length; i++)
	    {
	      // Si la ligne en cour n'est pas trop longue
	      if (result.lastElement().toString().length() + mot[i].length() < tailleLigne)
	      {
	        // Ajout à la ligne en court
	        result.setElementAt(result.lastElement().toString() + " " + mot[i],
	            result.size() - 1);
	      }
	      else
	      {
	        // Nouvelle ligne
	        result.add(mot[i]);
	      }
	    }

	    // Limitation du nombre de ligne du vecteur
	    result.setSize(nbLigne);

	    return result;
	  }

	  /**
	   * Arrondis un double au nb de décimales souhaitées 
	   * @param nombre
	   * @param nbchiffres
	   * @return nombre arrondi au nb de chiffres voulu après la virgule
	   */
	  public static double arrondir(double nombre, int nbchiffres)
	  {
	    // arrondit nombre à nbchiffres après la virgule
	    double arrondi = 1.0;
	    for (int i = 0; i < nbchiffres; i++)
	    {
	      arrondi *= 10;
	    }
	    return (java.lang.Math.round(nombre * arrondi)) / arrondi;
	  }
	  
	  /**
	   * Arrondis un float au nb de décimales souhaitées 
	   * @param nombre
	   * @param nbchiffres
	   * @return nombre arrondi au nb de chiffres voulu après la virgule
	   */
	  public static float arrondir(float nombre, int nbchiffres)
	  {
	    // arrondit nombre à nbchiffres après la virgule
	  	float arrondi = (new Float(1.0)).floatValue();
	    for (int i = 0; i < nbchiffres; i++)
	    {
	      arrondi *= 10;
	    }
	    return (java.lang.Math.round(nombre * arrondi)) / arrondi;
	  }

	  /**
	   * @return date du jour à 00:00:00
	   */
	  public static Date getDateDuJour()
	  {
	  	Calendar c = Calendar.getInstance();
	  	c.set(Calendar.MILLISECOND, 0);
	  	c.set(Calendar.MINUTE, 0);
	  	c.set(Calendar.HOUR_OF_DAY,0);
	  	c.set(Calendar.SECOND, 0);
	  	return c.getTime();
	  }

	  /**
	   * Retourne une chaine de caratere en majuscule sans accents.
	   * @param chaine La chaine.
	   * @return Le chaine de caratere en majuscule sans accents.
	   */
	  public static final String toUpperCaseSansAccent(String chaine) {
	    
	    if (null == chaine || chaine.length() == 0 ) {
	      return null;
	    }
	    
	    return StringOperation.sansAccent(chaine.toUpperCase());
	  }

	  /**
	   * Echape les caratère spéciaux dans une chaine pour l'affichage en HTML.
	   * attention: a utiliser uniquement pour l'appel simple a une page
	   * et non pour les passage de paramètres de formulaire 
	   * @param chaineBrute Le chaine à échapper.
	   * @return La chaine échapée.
	   */
	  public static final String escapeHTMLChar(String chaineBrute)
	  {
	    StringBuffer chaineEncodee = new StringBuffer();
	    for (int i = 0; i < chaineBrute.length(); i++)
	    {
	      char c = chaineBrute.charAt(i);
	      
	      switch (c)
	      { 
	        case '\n':
	          chaineEncodee.append("<br />");
	          break;
	        case '"':
	          chaineEncodee.append("&#34;");
	          break;
	        case '&':
	          chaineEncodee.append("&#38;");
	          break;
	        case '\'':
	          chaineEncodee.append("\\\'");
	          break;
	        case '€':
	          chaineEncodee.append("&#128;");
	          break;
	        case '‚':
	          chaineEncodee.append("&#130;");
	          break;
	        case 'ƒ':
	          chaineEncodee.append("&#131;");
	          break;
	        case '„':
	          chaineEncodee.append("&#132;");
	          break;
	        case '…':
	          chaineEncodee.append("&#133;");
	          break;
	        case '†':
	          chaineEncodee.append("&#134;");
	          break;
	        case '‡':
	          chaineEncodee.append("&#135;");
	          break;
	        case 'ˆ':
	          chaineEncodee.append("&#136;");
	          break;
	        case '‰':
	          chaineEncodee.append("&#137;");
	          break;
	        case 'Š':
	          chaineEncodee.append("&#138;");
	          break;
	        case '‹':
	          chaineEncodee.append("&#139;");
	          break;
	        case 'Œ':
	          chaineEncodee.append("&#140;");
	          break;
	        case '‘':
	          chaineEncodee.append("&#145;");
	          break;
	        case '’':
	          chaineEncodee.append("&#146;");
	          break;
	        case '“':
	          chaineEncodee.append("&#147;");
	          break;
	        case '”':
	          chaineEncodee.append("&#148;");
	          break;
	        case '•':
	          chaineEncodee.append("&#149;");
	          break;
	        case '–':
	          chaineEncodee.append("&#150;");
	          break;
	        case '—':
	          chaineEncodee.append("&#151;");
	          break;
	        case '˜':
	          chaineEncodee.append("&#152;");
	          break;
	        case '™':
	          chaineEncodee.append("&#153;");
	          break;
	        case 'š':
	          chaineEncodee.append("&#154;");
	          break;
	        case '›':
	          chaineEncodee.append("&#155;");
	          break;
	        case 'œ':
	          chaineEncodee.append("&#156;");
	          break;
	        case 'Ÿ':
	          chaineEncodee.append("&#159;");
	          break;
	        case ' ':
	          chaineEncodee.append("&#160;");
	          break;
	        case '¡':
	          chaineEncodee.append("&#161;");
	          break;
	        case '¢':
	          chaineEncodee.append("&#162;");
	          break;
	        case '£':
	          chaineEncodee.append("&#163;");
	          break;
	        case '¤':
	          chaineEncodee.append("&#164;");
	          break;
	        case '¥':
	          chaineEncodee.append("&#165;");
	          break;
	        case '¦':
	          chaineEncodee.append("&#166;");
	          break;
	        case '§':
	          chaineEncodee.append("&#167;");
	          break;
	        case '¨':
	          chaineEncodee.append("&#168;");
	          break;
	        case '©':
	          chaineEncodee.append("&#169;");
	          break;
	        case 'ª':
	          chaineEncodee.append("&#170;");
	          break;
	        case '«':
	          chaineEncodee.append("&#171;");
	          break;
	        case '¬':
	          chaineEncodee.append("&#172;");
	          break;
	        case '­':
	          chaineEncodee.append("&#173;");
	          break;
	        case '®':
	          chaineEncodee.append("&#174;");
	          break;
	        case '¯':
	          chaineEncodee.append("&#175;");
	          break;
	        case '°':
	          chaineEncodee.append("&#176;");
	          break;
	        case '±':
	          chaineEncodee.append("&#177;");
	          break;
	        case '²':
	          chaineEncodee.append("&#178;");
	          break;
	        case '³':
	          chaineEncodee.append("&#179;");
	          break;
	        case '´':
	          chaineEncodee.append("&#180;");
	          break;
	        case 'µ':
	          chaineEncodee.append("&#181;");
	          break;
	        case '¶':
	          chaineEncodee.append("&#182;");
	          break;
	        case '·':
	          chaineEncodee.append("&#183;");
	          break;
	        case '¸':
	          chaineEncodee.append("&#184;");
	          break;
	        case '¹':
	          chaineEncodee.append("&#185;");
	          break;
	        case 'º':
	          chaineEncodee.append("&#186;");
	          break;
	        case '»':
	          chaineEncodee.append("&#187;");
	          break;
	        case '¼':
	          chaineEncodee.append("&#188;");
	          break;
	        case '½':
	          chaineEncodee.append("&#189;");
	          break;
	        case '¾':
	          chaineEncodee.append("&#190;");
	          break;
	        case '¿':
	          chaineEncodee.append("&#191;");
	          break;
	        case 'À':
	          chaineEncodee.append("&#192;");
	          break;
	        case 'Á':
	          chaineEncodee.append("&#193;");
	          break;
	        case 'Â':
	          chaineEncodee.append("&#194;");
	          break;
	        case 'Ã':
	          chaineEncodee.append("&#195;");
	          break;
	        case 'Ä':
	          chaineEncodee.append("&#196;");
	          break;
	        case 'Å':
	          chaineEncodee.append("&#197;");
	          break;
	        case 'Æ':
	          chaineEncodee.append("&#198;");
	          break;
	        case 'Ç':
	          chaineEncodee.append("&#199;");
	          break;
	        case 'È':
	          chaineEncodee.append("&#200;");
	          break;
	        case 'É':
	          chaineEncodee.append("&#201;");
	          break;
	        case 'Ê':
	          chaineEncodee.append("&#202;");
	          break;
	        case 'Ë':
	          chaineEncodee.append("&#203;");
	          break;
	        case 'Ì':
	          chaineEncodee.append("&#204;");
	          break;
	        case 'Í':
	          chaineEncodee.append("&#205;");
	          break;
	        case 'Î':
	          chaineEncodee.append("&#206;");
	          break;
	        case 'Ï':
	          chaineEncodee.append("&#207;");
	          break;
	        case 'Ð':
	          chaineEncodee.append("&#208;");
	          break;
	        case 'Ñ':
	          chaineEncodee.append("&#209;");
	          break;
	        case 'Ò':
	          chaineEncodee.append("&#210;");
	          break;
	        case 'Ó':
	          chaineEncodee.append("&#211;");
	          break;
	        case 'Ô':
	          chaineEncodee.append("&#212;");
	          break;
	        case 'Õ':
	          chaineEncodee.append("&#213;");
	          break;
	        case 'Ö':
	          chaineEncodee.append("&#214;");
	          break;
	        case '×':
	          chaineEncodee.append("&#215;");
	          break;
	        case 'Ø':
	          chaineEncodee.append("&#216;");
	          break;
	        case 'Ù':
	          chaineEncodee.append("&#217;");
	          break;
	        case 'Ú':
	          chaineEncodee.append("&#218;");
	          break;
	        case 'Û':
	          chaineEncodee.append("&#219;");
	          break;
	        case 'Ü':
	          chaineEncodee.append("&#220;");
	          break;
	        case 'Ý':
	          chaineEncodee.append("&#221;");
	          break;
	        case 'Þ':
	          chaineEncodee.append("&#222;");
	          break;
	        case 'ß':
	          chaineEncodee.append("&#223;");
	          break;
	        case 'à':
	          chaineEncodee.append("&#224;");
	          break;
	        case 'á':
	          chaineEncodee.append("&#225;");
	          break;
	        case 'â':
	          chaineEncodee.append("&#226;");
	          break;
	        case 'ã':
	          chaineEncodee.append("&#227;");
	          break;
	        case 'ä':
	          chaineEncodee.append("&#228;");
	          break;
	        case 'å':
	          chaineEncodee.append("&#229;");
	          break;
	        case 'æ':
	          chaineEncodee.append("&#230;");
	          break;
	        case 'ç':
	          chaineEncodee.append("&#231;");
	          break;
	        case 'è':
	          chaineEncodee.append("&#232;");
	          break;
	        case 'é':
	          chaineEncodee.append("&#233;");
	          break;
	        case 'ê':
	          chaineEncodee.append("&#234;");
	          break;
	        case 'ë':
	          chaineEncodee.append("&#235;");
	          break;
	        case 'ì':
	          chaineEncodee.append("&#236;");
	          break;
	        case 'í':
	          chaineEncodee.append("&#237;");
	          break;
	        case 'î':
	          chaineEncodee.append("&#238;");
	          break;
	        case 'ï':
	          chaineEncodee.append("&#239;");
	          break;
	        case 'ð':
	          chaineEncodee.append("&#240;");
	          break;
	        case 'ñ':
	          chaineEncodee.append("&#241;");
	          break;
	        case 'ò':
	          chaineEncodee.append("&#242;");
	          break;
	        case 'ó':
	          chaineEncodee.append("&#243;");
	          break;
	        case 'ô':
	          chaineEncodee.append("&#244;");
	          break;
	        case 'õ':
	          chaineEncodee.append("&#245;");
	          break;
	        case 'ö':
	          chaineEncodee.append("&#246;");
	          break;
	        case '÷':
	          chaineEncodee.append("&#247;");
	          break;
	        case 'ø':
	          chaineEncodee.append("&#248;");
	          break;
	        case 'ù':
	          chaineEncodee.append("&#249;");
	          break;
	        case 'ú':
	          chaineEncodee.append("&#250;");
	          break;
	        case 'û':
	          chaineEncodee.append("&#251;");
	          break;
	        case 'ü':
	          chaineEncodee.append("&#252;");
	          break;
	        case 'ý':
	          chaineEncodee.append("&#253;");
	          break;
	        case 'þ':
	          chaineEncodee.append("&#254;");
	          break;

	        default:
	          chaineEncodee.append(c);
	          break;
	      }
	    }
	    return chaineEncodee.toString();
	  }
	  
	  /**
	   * @param chaineBrute
	   * @return chaine transcodée
	   */
	  public static final String escapeJavaScriptChar(String chaineBrute)
	  {
	    StringBuffer chaineEncodee = new StringBuffer();
	    for (int i = 0; i < chaineBrute.length(); i++)
	    {
	      char c = chaineBrute.charAt(i);
	      switch (c)
	      { 
	        case '"':
	          chaineEncodee.append("\\\"");
	          break;
	        case '\'':
	          chaineEncodee.append("\\\'");
	          break;

	        default:
	          chaineEncodee.append(c);
	          break;
	      }
	    }
	    return chaineEncodee.toString();
	  }
	  
	  /**
	   * @param chaineBrute
	   * @return chaine transcodée
	   */
	  public static final String escapeURLChar(String chaineBrute)
	  {
	    StringBuffer chaineEncodee = new StringBuffer();
	    for (int i = 0; i < chaineBrute.length(); i++)
	    {
	      char c = chaineBrute.charAt(i);
	      switch (c)
	      { 
	        case ' ':
	          chaineEncodee.append("%20");
	          break;
	        case '?':
	          chaineEncodee.append("%3f");
	          break;
	        case '=':
	          chaineEncodee.append("%3d");
	          break;
	        case '+':
	          chaineEncodee.append("%2b");
	          break;
	        case '/':
	          chaineEncodee.append("%2f");
	          break;
	        case '%':
	          chaineEncodee.append("%25");
	          break;
	        case ';':
	          chaineEncodee.append("%3b");
	          break;
	        case ':':
	          chaineEncodee.append("%3a");
	          break;
	        // à compléter ...
	          
	        default:
	          chaineEncodee.append(c);
	          break;
	      }
	    }
	    return chaineEncodee.toString();
	  }
	}


	/**
	 * 
	 * StringOperation : retire les accents d'une chaine de caractère
	 *
	 * @author Sébastien JANCZAK - Cora Informatique
	 * @version 1.0.0
	 */
	class StringOperation
	{

	    /** Index du 1er caractere accentué **/
	    private static final int MIN = 192;
	    /** Index du dernier caractere accentué **/
	    private static final int MAX = 255;
	    /** Vecteur de correspondance entre accent / sans accent **/
	    private static final Vector<String> map = initMap();
	    
	    /** 
	     * Initialisation du tableau de correspondance entre les caractéres accentués et leur homologues non accentués
	     * @return la liste des caractères "non accentués" de substitution 
	     */
	    private static Vector<String> initMap()
	    {  Vector<String> result = new Vector<String>();
	       String car  = null;
	       
	       car = new String("A");
	       result.add( car );            /* '\u00C0'   À   alt-0192  */  
	       result.add( car );            /* '\u00C1'   Á   alt-0193  */
	       result.add( car );            /* '\u00C2'   Â   alt-0194  */
	       result.add( car );            /* '\u00C3'   Ã   alt-0195  */
	       result.add( car );            /* '\u00C4'   Ä   alt-0196  */
	       result.add( car );            /* '\u00C5'   Å   alt-0197  */
	       car = new String("AE");
	       result.add( car );            /* '\u00C6'   Æ   alt-0198  */
	       car = new String("C");
	       result.add( car );            /* '\u00C7'   Ç   alt-0199  */
	       car = new String("E");
	       result.add( car );            /* '\u00C8'   È   alt-0200  */
	       result.add( car );            /* '\u00C9'   É   alt-0201  */
	       result.add( car );            /* '\u00CA'   Ê   alt-0202  */
	       result.add( car );            /* '\u00CB'   Ë   alt-0203  */
	       car = new String("I");
	       result.add( car );            /* '\u00CC'   Ì   alt-0204  */
	       result.add( car );            /* '\u00CD'   Í   alt-0205  */
	       result.add( car );            /* '\u00CE'   Î   alt-0206  */
	       result.add( car );            /* '\u00CF'   Ï   alt-0207  */
	       car = new String("D");
	       result.add( car );            /* '\u00D0'   Ð   alt-0208  */
	       car = new String("N");
	       result.add( car );            /* '\u00D1'   Ñ   alt-0209  */
	       car = new String("O");
	       result.add( car );            /* '\u00D2'   Ò   alt-0210  */
	       result.add( car );            /* '\u00D3'   Ó   alt-0211  */
	       result.add( car );            /* '\u00D4'   Ô   alt-0212  */
	       result.add( car );            /* '\u00D5'   Õ   alt-0213  */
	       result.add( car );            /* '\u00D6'   Ö   alt-0214  */
	       car = new String("*");
	       result.add( car );            /* '\u00D7'   ×   alt-0215  */
	       car = new String("0");
	       result.add( car );            /* '\u00D8'   Ø   alt-0216  */
	       car = new String("U");
	       result.add( car );            /* '\u00D9'   Ù   alt-0217  */
	       result.add( car );            /* '\u00DA'   Ú   alt-0218  */
	       result.add( car );            /* '\u00DB'   Û   alt-0219  */
	       result.add( car );            /* '\u00DC'   Ü   alt-0220  */
	       car = new String("Y");
	       result.add( car );            /* '\u00DD'   Ý   alt-0221  */
	       car = new String("Þ");
	       result.add( car );            /* '\u00DE'   Þ   alt-0222  */
	       car = new String("B");
	       result.add( car );            /* '\u00DF'   ß   alt-0223  */
	       car = new String("a");
	       result.add( car );            /* '\u00E0'   à   alt-0224  */
	       result.add( car );            /* '\u00E1'   á   alt-0225  */
	       result.add( car );            /* '\u00E2'   â   alt-0226  */
	       result.add( car );            /* '\u00E3'   ã   alt-0227  */
	       result.add( car );            /* '\u00E4'   ä   alt-0228  */
	       result.add( car );            /* '\u00E5'   å   alt-0229  */
	       car = new String("ae");
	       result.add( car );            /* '\u00E6'   æ   alt-0230  */
	       car = new String("c");
	       result.add( car );            /* '\u00E7'   ç   alt-0231  */
	       car = new String("e");
	       result.add( car );            /* '\u00E8'   è   alt-0232  */
	       result.add( car );            /* '\u00E9'   é   alt-0233  */
	       result.add( car );            /* '\u00EA'   ê   alt-0234  */
	       result.add( car );            /* '\u00EB'   ë   alt-0235  */
	       car = new String("i");
	       result.add( car );            /* '\u00EC'   ì   alt-0236  */
	       result.add( car );            /* '\u00ED'   í   alt-0237  */
	       result.add( car );            /* '\u00EE'   î   alt-0238  */
	       result.add( car );            /* '\u00EF'   ï   alt-0239  */
	       car = new String("d");
	       result.add( car );            /* '\u00F0'   ð   alt-0240  */
	       car = new String("n");
	       result.add( car );            /* '\u00F1'   ñ   alt-0241  */
	       car = new String("o");
	       result.add( car );            /* '\u00F2'   ò   alt-0242  */
	       result.add( car );            /* '\u00F3'   ó   alt-0243  */
	       result.add( car );            /* '\u00F4'   ô   alt-0244  */
	       result.add( car );            /* '\u00F5'   õ   alt-0245  */
	       result.add( car );            /* '\u00F6'   ö   alt-0246  */
	       car = new String("/");
	       result.add( car );            /* '\u00F7'   ÷   alt-0247  */
	       car = new String("0");
	       result.add( car );            /* '\u00F8'   ø   alt-0248  */
	       car = new String("u");
	       result.add( car );            /* '\u00F9'   ù   alt-0249  */
	       result.add( car );            /* '\u00FA'   ú   alt-0250  */
	       result.add( car );            /* '\u00FB'   û   alt-0251  */
	       result.add( car );            /* '\u00FC'   ü   alt-0252  */
	       car = new String("y");
	       result.add( car );            /* '\u00FD'   ý   alt-0253  */
	       car = new String("þ");
	       result.add( car );            /* '\u00FE'   þ   alt-0254  */
	       car = new String("y");
	       result.add( car );            /* '\u00FF'   ÿ   alt-0255  */
	       result.add( car );            /* '\u00FF'       alt-0255  */
	       
	       return result;
	    }

	    /** Transforme une chaine pouvant contenir des accents dans une version sans accent 
	     *  @param chaine Chaine a convertir sans accent
	     *  @return Chaine dont les accents ont été supprimé
	     **/
	    public static String sansAccent(String chaine)
	    {  StringBuffer Result  = new StringBuffer(chaine);
	       
	       for(int bcl = 0 ; bcl < Result.length() ; bcl++)
	       {   int carVal = chaine.charAt(bcl);
	           if( carVal >= MIN && carVal <= MAX )
	           {  // Remplacement
	              String newVal = map.get( carVal - MIN );
	              Result.replace(bcl, bcl+1,newVal);
	           }   
	       }
	       return Result.toString();
	   }
}

package fr.assj.gestiontournoi.commun;

public class MySQL extends DB implements DBInterface {
	public MySQL()
    {
    }

    public String fromDB(String valeur)
    {
        if(valeur == null)
            return null;
        else
            return valeur.trim();
    }

    public String fromDB(String valeur, String defaut)
    {
        if(valeur == null)
            return defaut;
        else
            return valeur.trim();
    }
}

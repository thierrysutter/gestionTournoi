package fr.assj.gestiontournoi.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;

import fr.assj.gestiontournoi.connection.ConnectionFactory;

public class ActionBase extends Action {
	protected Logger logger = Logger.getLogger("LOG");
	
	protected synchronized Connection openDataBaseconnection(Connection connection, HttpServletRequest request) throws SQLException {
		if (connection == null) {
			connection = ConnectionFactory.getConnection();
			logger.debug("Ouverture d'une connexion à la base de données.");
		}
		return connection;
	}
	
	/**
	 * generation d'un message pour les logs
	 *
	 * @param request
	 * @return un message à afficher dans les logs.
	 */
	protected String log(HttpServletRequest request)
	{
		String id   = request.getSession().getId();

		StringBuffer message = new StringBuffer(1000);
		message.append("[");
		
		message.append(":");
		if (id != null) {
			message.append(id);
		}
		
		message.append("] Entrée dans ");
		message.append(this.getClass().getName());

		return message.toString();
	}
	
	public static int stringToInt(String valeur, int defaut)
    {
        try
        {
            if(valeur == null)
                return defaut;
            else
                return Integer.parseInt(valeur);
        }
        catch(NumberFormatException nfe)
        {
            return defaut;
        }
    }

    public static double stringToDbl(String valeur, double defaut)
    {
        try
        {
            if(valeur == null)
                return defaut;
            else
                return Double.parseDouble(valeur);
        }
        catch(NumberFormatException nfe)
        {
            return defaut;
        }
    }

    public static Integer stringToInteger(String valeur, Integer defaut)
    {
        try
        {
            if(valeur == null)
                return defaut;
            else
                return new Integer(valeur);
        }
        catch(NumberFormatException nfe)
        {
            return defaut;
        }
    }

    public static Double stringToDouble(String valeur, Double defaut)
    {
        try
        {
            if(valeur == null)
                return defaut;
            else
                return new Double(valeur.replace(',', '.'));
        }
        catch(NumberFormatException nfe)
        {
            return defaut;
        }
    }

    public static double stringToDouble(String valeur, double defaut)
    {
        try
        {
            if(valeur == null)
                return defaut;
            else
                return (new Double(valeur.replace(',', '.'))).doubleValue();
        }
        catch(NumberFormatException nfe)
        {
            return defaut;
        }
    }

    public static Date stringToDate(String valeur, String pattern, Date defaut)
    {
        if(valeur == null)
            return defaut;
        if(pattern == null)
            pattern = "dd/MM/yyyy";
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            return sdf.parse(valeur);
        }
        catch(ParseException pe)
        {
            return defaut;
        }
    }

    public static Date stringToDate(String valeur, Date defaut)
    {
        return stringToDate(valeur, "dd/MM/yyyy", defaut);
    }
    
    public static String stringToString(String valeur, String defaut)
    {
        if(valeur == null)
            return defaut;
        else
            return valeur;
    }

    public static boolean stringToBoolean(String valeur, boolean defaut)
    {
        if(valeur == null)
            return defaut;
        if(valeur.equals("0") || valeur.equals("1"))
            return valeur.equals("1");
        if(valeur.equals("on") || valeur.equals("off"))
            return valeur.equals("on");
        else
            return defaut;
    }

    public static int integerToInt(Integer valeur, int defaut)
    {
        if(valeur == null)
            return defaut;
        else
            return valeur.intValue();
    }
}
